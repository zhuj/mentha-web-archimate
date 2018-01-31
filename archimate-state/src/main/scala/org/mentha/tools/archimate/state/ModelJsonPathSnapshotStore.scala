package org.mentha.tools.archimate.state

import com.typesafe.config.Config
import org.mentha.utils.akka.persistence.jgit.snapshot.JgitJsonPathSnapshotStore
import play.api.libs.json.JsValue

/**
  * Customization of JgitJsonPathSnapshotStore for Model json.
  * It stores each view as a separate json file in the GIT so that local view changes (i.e. moving) won't affect the whole model.
  * It reduces speed of Store/Load operations, but it could save size of each GIT commit.
  * */
class ModelJsonPathSnapshotStore(config: Config) extends JgitJsonPathSnapshotStore(config) {

  override protected def getNodeType(path: List[String], name: String, value: JsValue): NodeType = {
    path match {
      case Nil => NodeTypeFolder // root is always a folder
      case modelId :: Nil => modelId match {
        case OBJECT_JSON => throw new IllegalStateException(s"There should not be model with id=${OBJECT_JSON}")
        case _ =>   name match {
          // TODO: think about: case "nodes" | "edges" => NodeTypeFile
          case "views" => NodeTypeFolder
          case _ => NodeTypeProperty
        }
      }
      case _ => NodeTypeFile
    }
  }

}
