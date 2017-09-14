package org.mentha.utils.akka.persistence.jgit.snapshot

import java.io.InputStream
import java.nio.charset.Charset

import akka.persistence.serialization.Snapshot
import com.typesafe.config.Config
import org.apache.commons.io.IOUtils

class JgitTextSnapshotStore(config: Config) extends JgitSnapshotStore(config) {

  private val charset = Charset.forName("UTF-8")

  override protected def deserialize(inputStream: InputStream): Snapshot = Snapshot {
    IOUtils.toString(inputStream, charset)
  }

  override protected def serialize(snapshot: Snapshot): Array[Byte] = {
    Option(snapshot.data).map { _.asInstanceOf[String] }.getOrElse("").getBytes(charset)
  }

}
