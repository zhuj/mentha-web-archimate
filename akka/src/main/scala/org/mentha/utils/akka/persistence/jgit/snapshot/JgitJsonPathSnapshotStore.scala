package org.mentha.utils.akka.persistence.jgit.snapshot

import java.io._
import java.nio.charset.Charset
import java.util.Date

import akka.actor.ActorLogging
import akka.persistence._
import akka.persistence.snapshot.SnapshotStore
import com.typesafe.config.Config
import org.apache.commons.codec.net.URLCodec
import org.eclipse.jgit.dircache.{DirCache, DirCacheEntry}
import org.eclipse.jgit.lib._
import org.eclipse.jgit.revwalk._
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import org.eclipse.jgit.util.SystemReader
import org.mentha.utils.akka.persistence.jgit._
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable
import scala.concurrent.Future
import scala.util._

//noinspection DuplicatedCode
class JgitJsonPathSnapshotStore(config: Config) extends SnapshotStore with ActorLogging {

  protected sealed trait NodeType {}
  protected case object NodeTypeFolder extends NodeType {}
  protected case object NodeTypeFile extends NodeType {}
  protected case object NodeTypeProperty extends NodeType {}

  // debug
  @inline private def time[R](name: String)(block: => R): R = {
    if (!log.isInfoEnabled) {
      block
    } else {
      val t0 = System.currentTimeMillis()
      val result = block
      val t1 = System.currentTimeMillis()
      log.info(s"TIME: ${name}: " + (t1 - t0) + "ms")
      result
    }
  }

  private val UTF8 = Charset.forName("UTF-8")

  import akka.util.Helpers._
  private val maxLoadAttempts = config.getInt("max-load-attempts").requiring(_ > 1, "max-load-attempts must be >= 1")

  private val dir = new File(config.getString("dir"))
  require(dir.isDirectory, s"Configured parameter `dir` = ${dir.getPath} should point to a directory")

  if (true) {
    // ensure we have repository initialized
    org.eclipse.jgit.api.Git.init().setDirectory(dir).call()
  }

  private val snapshotPrettyFormat: Boolean = config.getBoolean("pretty")

  private val branchPrefix = new File(config.getString("branch-prefix"))
  private val executionContext = context.system.dispatchers.lookup(config.getString("stream-dispatcher"))

  private val committerName = config.getString("committer.name")
  private val committerMail = config.getString("committer.mail")

  protected val OBJECT_JSON = ".object.json"

  override def loadAsync(persistenceId: String, criteria: SnapshotSelectionCriteria): Future[Option[SelectedSnapshot]] = {
    Future {
      time("loadAsync") {
        selectMetadata(persistenceId, criteria, maxLoadAttempts)
          .flatMap { loadFirstSuccess }
          .get
      }
    }(executionContext)
  }

  override def saveAsync(metadata: SnapshotMetadata, snapshot: Any): Future[Unit] = {
    Future {
      time("saveAsync") {
        save(metadata, snapshot.asInstanceOf[JsObject])
          .recoverWith { case e => log.error(e, e.getMessage); Failure(e) }
          .map { _ => }
          .get
      }
    }(executionContext)
  }

  override def deleteAsync(metadata: SnapshotMetadata): Future[Unit] = {
    // TODO: git rebase?
    Future.successful {} // DO NOTHING
  }

  override def deleteAsync(persistenceId: String, criteria: SnapshotSelectionCriteria): Future[Unit] = {
    // TODO: git rebase?
    Future.successful {}// DO NOTHING
  }

  @scala.annotation.tailrec
  private def loadFirstSuccess(metadata: LazyList[(String, SnapshotMetadata)]): Try[Option[SelectedSnapshot]] = metadata match {
    case LazyList() => Success(None) // no snapshots stored
    case (rev, md) #:: remaining => {
      tryToLoad(md.persistenceId, rev) match {
        case Success(s) => Success(Some(SelectedSnapshot(md, s)))
        case Failure(e) => {
          log.error(e, s"Error loading snapshot [{}], remaining attempts: [{}]", md, remaining.size)
          if (remaining.isEmpty) { Failure(e) } // all attempts failed
          else { loadFirstSuccess(remaining) } // try older snapshot
        }
      }
    }
  }

  protected def getNodeType(path: List[String], name: String, value: JsValue): NodeType = {
    if (OBJECT_JSON == name) { throw new IllegalStateException() }
    else { NodeTypeFile }
  }

  protected def save(metadata: SnapshotMetadata, snapshot: JsObject): Try[RefUpdate.Result] = withRepository { repo =>
    val committer = new PersonIdent(committerName, committerMail, metadata.timestamp, SystemReader.getInstance.getTimezone(metadata.timestamp))
    val time = committer.getWhen.toInstant
    repo.commitBranchTree(
      branchName = branch(metadata.persistenceId),
      committer = committer,
      message = s"[${metadata.sequenceNr}]: Snapshot stored ${new Date(metadata.timestamp).toString}"
    ) {
      (obi) => Try {

        val encoder = new URLCodec()
        val dc = DirCache.newInCore()
        val builder = dc.builder()

        def insert(obj: JsValue, path: List[String]) = {
          val text = if (snapshotPrettyFormat) { Json.prettyPrint(obj) } else { Json.stringify(obj) }
          val fileContentBlobId = obi.insert(Constants.OBJ_BLOB, text.getBytes(UTF8))

          val pathString = path.reverse.map { encoder.encode }.mkString("/")
          val entry = new DirCacheEntry(pathString)
          entry.setLength(text.length)
          entry.setFileMode(FileMode.REGULAR_FILE)
          entry.setObjectId(fileContentBlobId)
          entry.setLastModified(time)

          builder.add(entry)
        }

        def withObject(obj: JsObject, path: List[String]): Unit = {
          val c = mutable.LinkedHashMap.empty[String, JsValue]
          for { (name, value) <- obj.fields } {
            getNodeType(path, name, value) match {
              case NodeTypeFolder => withObject(value.asInstanceOf[JsObject], name :: path)
              case NodeTypeFile => insert(value, name :: path)
              case _ => c += (name -> value)
            }
          }
          if (c.nonEmpty) {
            insert(JsObject(c), OBJECT_JSON :: path)
          }
        }

        withObject(snapshot, Nil)

        builder.finish()
        dc.writeTree(obi)
      }
    }
  }


  private val commitMessage = """\[(\d+)\].*""".r
  private def snapshotMetadata(persistenceId: String, revCommit: RevCommit): SnapshotMetadata = {
    val sequenceNr = revCommit.getShortMessage match {
      case commitMessage(seq) => Try { Integer.parseInt(seq) } getOrElse { 0 }
      case _ => 0
    }
    SnapshotMetadata(
      persistenceId = persistenceId,
      sequenceNr = sequenceNr,
      timestamp = 1000L * revCommit.getCommitTime
    )
  }

  private def selectMetadata(persistenceId: String, criteria: SnapshotSelectionCriteria, maxLoad: Int): Try[LazyList[(String, SnapshotMetadata)]] = withRepository { repo =>
    repo.withBranchHistory(branchName = branch(persistenceId)) { commits => Try {
      commits
        // .take(maxLoad)
        .map { revCommit => revCommit.name() -> snapshotMetadata(persistenceId, revCommit) }
        .dropWhile { case (nm, md) => md.timestamp > criteria.maxTimestamp || md.sequenceNr > criteria.maxSequenceNr }
        .takeWhile { case (nm, md) => md.timestamp >= criteria.minTimestamp && md.sequenceNr >= criteria.minSequenceNr }
        .take(maxLoad)
    } }
  }

  private def tryToLoad(persistenceId: String, rev: String): Try[JsObject] = withRepository { repo =>
    val branchName = branch(persistenceId)
    val commitId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")
    if (null == commitId) {
      Success {
        JsObject.empty
      }
    } else {

      withResource(new RevWalk(repo)) { revWalk =>
        val commit = revWalk.parseCommit(commitId)
        withResource(repo.newObjectReader()) { reader =>

          val encoder = new URLCodec()

          def withTree(tree: ObjectId): JsObject = {
            val c = mutable.LinkedHashMap.empty[String, JsValue]

            val p = new CanonicalTreeParser()
            p.reset(reader, tree)
            while (!p.eof()) {
              val name = encoder.decode(p.getEntryPathString)
              val value = if (p.getEntryFileMode == FileMode.TREE) {
                withTree(p.getEntryObjectId)
              } else {
                val loader = repo.open(p.getEntryObjectId)
                withResource(loader.openStream()) { stream => Try { Json.parse(stream).asInstanceOf[JsObject] } } .get
              }

              if (OBJECT_JSON != name) {
                c += (name -> value)
              } else {
                c ++= value.fields
              }

              p.next()
            }

            new JsObject(c)
          }

          Try {
            withTree(commit.getTree)
          }
        }
      }
    }
  }

  private def branch(persistenceId: String) = s"${branchPrefix}-${persistenceId}"

  private def withRepository[T](f: Repository => Try[T]): Try[T] = withResource(openRepository())(f)
  private def openRepository(): Repository =   new FileRepositoryBuilder().setGitDir(new File(s"${dir}/.git")).build()

}
