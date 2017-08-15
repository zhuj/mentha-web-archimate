import java.io.File

import org.apache.commons.io.FileUtils

object traverser2 {

  import org.mentha.utils.archimate.model._

  val model = json.fromJsonString(
    FileUtils.readFileToString(
      new File(s"src/test/elements2.json"),
      "UTF-8"
    )
  )

  import org.mentha.utils.archimate.model.traverse._
  val traverser = new ModelTraverser(ModelTraverser.ChangesInfluence)(model)
  traverser(model.nodes.toSeq.take(10):_*)( new ModelVisitor {
    override def visitConcept(c: Concept): Unit = {
      println(s"visit-node: ${c.meta.name}@${c.id}")
    }
    override def visitEdge(from: Concept, edge: EdgeConcept, to: Concept): Unit = {
      println(s"visit-edge: ${from.meta.name}@${from.id} --~${edge.meta.name}~--> ${to.meta.name}@${to.id} ")
    }
  })

  def main(args: Array[String]): Unit = {

  }
}