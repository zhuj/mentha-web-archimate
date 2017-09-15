package org.mentha.utils.akka.persistence.jgit.snapshot

import java.io._
import java.util.Date

import akka.actor.ActorLogging
import akka.persistence._
import akka.persistence.serialization.{Snapshot, streamToBytes}
import akka.persistence.snapshot.SnapshotStore
import akka.serialization._
import com.typesafe.config.Config
import org.eclipse.jgit.lib._
import org.eclipse.jgit.revwalk._
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.util.SystemReader
import org.mentha.utils.akka.persistence.jgit._

import scala.concurrent.Future
import scala.util._

class JgitSnapshotStore(config: Config) extends SnapshotStore with ActorLogging {

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

  import akka.util.Helpers._
  private val maxLoadAttempts = config.getInt("max-load-attempts").requiring(_ > 1, "max-load-attempts must be >= 1")

  private val dir = new File(config.getString("dir"))
  require(dir.isDirectory, s"Configured parameter `dir` = ${dir.getPath} should point to a directory")

  if (true) {
    // ensure we have repository initialized
    org.eclipse.jgit.api.Git.init().setDirectory(dir).call()
  }

  private val branchPrefix = new File(config.getString("branch-prefix"))
  private val executionContext = context.system.dispatchers.lookup(config.getString("stream-dispatcher"))
  private val serializationExtension = SerializationExtension(context.system)

  private val commiterName = config.getString("commiter.name")
  private val commiterMail = config.getString("commiter.mail")

  private val ENTRY_NAME = "SNAPSHOT"

  override def loadAsync(persistenceId: String, criteria: SnapshotSelectionCriteria): Future[Option[SelectedSnapshot]] = {
    Future {
      time("loadAsync") { selectMetadata(persistenceId, criteria, maxLoadAttempts).flatMap { loadFirstSuccess }.get }
    }(executionContext)
  }

  override def saveAsync(metadata: SnapshotMetadata, snapshot: Any): Future[Unit] = {
    Future {
      time("saveAsync") { save(metadata, snapshot).map { _ => }.get }
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
  private def loadFirstSuccess(metadata: Stream[(String, SnapshotMetadata)]): Try[Option[SelectedSnapshot]] = metadata match {
    case Stream.Empty => Success(None) // no snapshots stored
    case (rev, md) #:: remaining ⇒ {
      tryToLoad(md.persistenceId, rev)(deserialize) match {
        case Success(s) => Success(Some(SelectedSnapshot(md, s.data)))
        case Failure(e) => {
          log.error(e, s"Error loading snapshot [{}], remaining attempts: [{}]", md, remaining.size)
          if (remaining.isEmpty) { Failure(e) } // all attempts failed
          else { loadFirstSuccess(remaining) } // try older snapshot
        }
      }
    }
  }

  protected def save(metadata: SnapshotMetadata, snapshot: Any): Try[RefUpdate.Result] = withRepository { repo =>
    repo.commitBranchEntry(
      branchName = branch(metadata.persistenceId),
      entryName = ENTRY_NAME,
      commiter = new PersonIdent(commiterName, commiterMail, metadata.timestamp, SystemReader.getInstance.getTimezone(metadata.timestamp)),
      message = s"[${metadata.sequenceNr}]: Snapshot stored ${new Date(metadata.timestamp).toString}",
    )(bytes = serialize(Snapshot(snapshot)))
  }

  protected def deserialize(inputStream: InputStream): Snapshot =
    serializationExtension.deserialize(streamToBytes(inputStream), classOf[Snapshot]).get

  protected def serialize(snapshot: Snapshot): Array[Byte] =
    serializationExtension.findSerializerFor(snapshot).toBinary(snapshot)

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

  private def selectMetadata(persistenceId: String, criteria: SnapshotSelectionCriteria, maxLoad: Int): Try[Stream[(String, SnapshotMetadata)]] = withRepository { repo =>
    repo.withFileHistory(branchName = branch(persistenceId), entryName = ENTRY_NAME) { commits => Try {
      commits
        // .take(maxLoad)
        .map { revCommit => revCommit.name() -> snapshotMetadata(persistenceId, revCommit) }
        .dropWhile { case (nm, md) => md.timestamp > criteria.maxTimestamp || md.sequenceNr > criteria.maxSequenceNr }
        .takeWhile { case (nm, md) => md.timestamp >= criteria.minTimestamp && md.sequenceNr >= criteria.minSequenceNr }
        .take(maxLoad)
    } }
  }

  private def tryToLoad[T](persistenceId: String, rev: String)(p: (InputStream) ⇒ T): Try[T] = withRepository { repo =>
    repo.withFileContent(branchName = branch(persistenceId), entryName = ENTRY_NAME)(rev = rev) { stream => Try { p(stream) } }
  }

  private def branch(persistenceId: String) = s"${branchPrefix}-${persistenceId}"

  private def withRepository[T](f: Repository => Try[T]): Try[T] = withResource(openRepository())(f)
  private def openRepository(): Repository =   new FileRepositoryBuilder().setGitDir(new File(s"${dir}/.git")).build()

}
