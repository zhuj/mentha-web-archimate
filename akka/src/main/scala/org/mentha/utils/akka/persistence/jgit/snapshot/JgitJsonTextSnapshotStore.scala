package org.mentha.utils.akka.persistence.jgit.snapshot

import java.io.InputStream

import akka.persistence.serialization.Snapshot
import com.typesafe.config.Config
import org.apache.commons.codec.CharEncoding
import play.api.libs.json._

class JgitJsonTextSnapshotStore(config: Config) extends JgitSnapshotStore(config) {

  private val snapshotPrettyFormat: Boolean = config.getBoolean("pretty")

  override protected def deserialize(inputStream: InputStream): Snapshot = Snapshot {
    // NOTE: Json specification supports only UTF-8, UTF-16 and UTF-32 as valid encodings, so auto-detection implemented only for this charsets.
    Json.parse(inputStream)
  }

  override protected def serialize(snapshot: Snapshot): Array[Byte] = {
    Option(snapshot.data).map { stringify }.getOrElse("").getBytes(CharEncoding.UTF_8)
  }

  @inline private def stringify(data: Any): String = data match {
    case x: String => x
    case x: JsObject => if (snapshotPrettyFormat) { Json.prettyPrint(x) } else { Json.stringify(x) }
    case x => throw new IllegalArgumentException(s"Unexpected snapshot data type: ${org.apache.commons.lang3.ClassUtils.getName(x)}")
  }

}
