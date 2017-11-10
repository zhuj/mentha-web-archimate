package org.mentha.utils.akka.persistence

import java.io.InputStream
import java.nio.charset.Charset
import java.util.Collections

import org.apache.commons.io.IOUtils
import org.eclipse.jgit.dircache._
import org.eclipse.jgit.hooks._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.revwalk._
import org.eclipse.jgit.treewalk._
import org.eclipse.jgit.treewalk.filter._

import scala.util._
import scala.util.control.NonFatal

package object jgit {

  @inline private def withObject[Obj, Res](
    obj: Obj,
    finalizer: Obj=>Unit,
    body: Obj=>Try[Res]
  ): Try[Res] = {
    try { body(obj) }
    catch { case NonFatal(e) => Failure(e) }
    finally { finalizer(obj) }
  }

  @inline def withResource[Resource<:AutoCloseable, Res](r: Resource)(body: Resource=>Try[Res]): Try[Res] =
    withObject[Resource, Res](r, _.close(), body)

  /**
    * @see [[https://github.com/centic9/jgit-cookbook]]
    * @see [[https://www.kenneth-truyers.net/2016/10/13/git-nosql-database/]]
    */
  implicit class ImplicitRepository(repo: Repository) {

    def withBranchHistory[T](branchName: String)(body: Stream[RevCommit] => Try[T]): Try[T] = {
      val headId: ObjectId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")
      if (null == headId) {
        body { Stream.Empty }
      } else {
        withResource(new RevWalk(repo)) { revWalk =>
          revWalk.markStart(revWalk.parseCommit(headId))
          revWalk.sort(RevSort.COMMIT_TIME_DESC)
          body { collection.JavaConverters.asScalaIterator(revWalk.iterator()).toStream }
        }
      }
    }

    def withBranchTree[T](branchName: String)(rev: String)(
      construct: => T,
      body: InputStream => Try[T],
      aggregator: (T, String, T) => T
    ): Try[T] = {
      val commitId: ObjectId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")
      if (null == commitId) {
        body {
          IOUtils.toInputStream("", Charset.forName("UTF-8"))
        }
      } else {
        withResource(new RevWalk(repo)) { revWalk =>
          val commit = revWalk.parseCommit(commitId)
          withResource(repo.newObjectReader()) { reader =>

            def withTree(tree: ObjectId, current: T): T = {
              var c = current
              val p = new CanonicalTreeParser()
              p.reset(reader, tree)
              while (!p.eof()) {
                val name = p.getEntryPathString
                println(name)
                val child = if (p.getEntryFileMode == FileMode.TREE) {
                  withTree(p.getEntryObjectId, construct)
                } else {
                  val loader = repo.open(p.getEntryObjectId)
                  withResource(loader.openStream()) { stream => body { stream } } .get
                }
                c = aggregator(c, name, child)
                p.next()
              }
              c
            }

            Try { withTree(commit.getTree, construct) }
          }
        }
      }
    }




    def withFileHistory[T](branchName: String, entryName: String)(body: Stream[RevCommit] => Try[T]): Try[T] = {
      val headId: ObjectId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")
      if (null == headId) {
        body { Stream.Empty }
      } else {
        withResource(new RevWalk(repo)) { revWalk =>
          revWalk.markStart(revWalk.parseCommit(headId))
          revWalk.setTreeFilter(PathFilterGroup.createFromStrings(entryName))
          revWalk.sort(RevSort.COMMIT_TIME_DESC)
          body { collection.JavaConverters.asScalaIterator(revWalk.iterator()).toStream }
        }
      }
    }

    def withFileContent[T](branchName: String, entryName: String)(rev: String)(body: InputStream => Try[T]): Try[T] = {
      val commitId: ObjectId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")
      if (null == commitId) {
        body {
          IOUtils.toInputStream("", Charset.forName("UTF-8"))
        }
      } else {
        withResource(new RevWalk(repo)) { revWalk =>
          val commit = revWalk.parseCommit(commitId)
          val tree = commit.getTree
          withResource(new TreeWalk(repo)) { treeWalk =>
            treeWalk.addTree(tree)
            treeWalk.setRecursive(true)
            treeWalk.setFilter(PathFilter.create(entryName))
            if (!treeWalk.next()) {
              throw new IllegalStateException()
            }
            val objectId = treeWalk.getObjectId(0)
            val loader = repo.open(objectId)
            withResource(loader.openStream()) { stream => body { stream } }
          }
        }
      }
    }


    def commitBranchTree(
      branchName: String,
      commiter: PersonIdent,
      message: String
    )(treeBuilder: ObjectInserter => Try[ObjectId]): Try[RefUpdate.Result] = {

      val headId = repo.resolve(Constants.R_HEADS + branchName + "^{commit}")

      // start
      Hooks.preCommit(repo, System.err).call
      withResource(new RevWalk(repo)) { rw =>
        withResource(repo.newObjectInserter) { obi =>
          treeBuilder(obi).map { indexTreeId =>
            // store commit object
            val commitId = {
              val parentIds = if (null == headId) Collections.emptyList() else Collections.singletonList(headId)
              val cb = new CommitBuilder
              cb.setCommitter(commiter)
              cb.setAuthor(commiter)
              cb.setMessage(message)
              cb.setParentIds(parentIds)
              cb.setTreeId(indexTreeId)
              obi.insert(cb)
            }

            // flush the changes
            obi.flush()

            // read the commit
            val revCommit = rw.parseCommit(commitId)

            // and update the repo
            val refUpdate = repo.updateRef(Constants.R_HEADS + branchName)
            refUpdate.setNewObjectId(commitId)
            refUpdate.setRefLogMessage("commit" + revCommit.getShortMessage, false)
            refUpdate.setExpectedOldObjectId(headId)
            val updateResult = refUpdate.forceUpdate

            // apply post-commit hooks & return the result
            Hooks.postCommit(repo, System.err).call
            updateResult
          }
        }
      }
    }

    def commitBranchEntry(
      branchName: String,
      commiter: PersonIdent,
      message: String
    )(
      entryName: String,
      bytes: Array[Byte]
    ): Try[RefUpdate.Result] = commitBranchTree(
      branchName = branchName,
      commiter = commiter,
      message = message,
    ) {
      (obi) => Try {
        val dc = DirCache.newInCore()

        // store file content as a new blob
        val fileContentBlobId = obi.insert(Constants.OBJ_BLOB, bytes)

        // create an entry
        val entry = new DirCacheEntry(entryName)
        entry.setLength(bytes.length)
        entry.setFileMode(FileMode.REGULAR_FILE)
        entry.setObjectId(fileContentBlobId)
        entry.setLastModified(commiter.getWhen.getTime)

        // create tree builder and store currently available files
        val builder = dc.builder()
        (0 until dc.getEntryCount)
          .map { dc.getEntry }
          .filter { entryName != _.getPathString }
          .foreach { builder.add }

        // add the entry with content, then commit the tree
        builder.add(entry)
        builder.finish()
        dc.writeTree(obi)
      }
    }

  }

}