import java.io.File

import org.apache.commons.io.FileUtils
import org.mentha.tools.archimate.model.{Concept, EdgeConcept}

object traverser2 {

  import org.mentha.tools.archimate.model._

  val model = json.fromJsonString(
    FileUtils.readFileToString(
      new File(s"src/test/elements2.json"),
      "UTF-8"
    )
  )

  import org.mentha.archimate.model.traverse._
  val traverser = new ModelTraversing(ModelTraversing.ChangesInfluence)(model)
  traverser(model.nodes.toSeq.take(10):_*)( new ModelVisitor {
    override def visitVertex(vertex: Concept): Boolean = {
      println(s"visit-node: ${vertex.meta.name}@${vertex.id}")
      true
    }
    override def visitEdge(from: Concept, edge: EdgeConcept, to: Concept): Boolean = {
      println(s"visit-edge: ${from.meta.name}@${from.id} --~${edge.meta.name}~--> ${to.meta.name}@${to.id} ")
      true
    }
  })

  def main(args: Array[String]): Unit = {

  }
}