package org.mentha.utils.archimate.state

import com.typesafe.config.Config
import org.mentha.utils.akka.persistence.jgit.snapshot.JgitJsonSnapshotStore
import play.api.libs.json.JsValue

class ModelSnapshotStore(config: Config) extends JgitJsonSnapshotStore(config) {

  private val modelPath = "[^/]+".r

  override protected def getNodeType(path: String, name: String, value: JsValue): NodeType = {
    path match {
      case OBJECT_JSON => throw new IllegalStateException()
      case "" => NodeTypeFolder
      case modelPath() => name match {
        case "nodes" | "edges" | "views" => NodeTypeFolder
        case _ => NodeTypeProperty
      }
      case _ => NodeTypeFile
    }
  }

}
