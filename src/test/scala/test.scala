import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

object test {

  import org.mentha.utils.archimate.model.edges._
  import org.mentha.utils.archimate.model.nodes.dsl.Application._
  import org.mentha.utils.archimate.model.nodes.dsl.Business._
  import org.mentha.utils.archimate.model.nodes.dsl.Implementation._
  import org.mentha.utils.archimate.model.nodes.dsl.Motivation._
  import org.mentha.utils.archimate.model.nodes.dsl._
  import org.mentha.utils.archimate.model.view.View._


  def in[T <: Concept](view: View)(unit: => T): T = unit match {
    case r: Relationship => { r.attach(view) }.concept.asInstanceOf[T]
    case n: NodeConcept => { n.attach(view) }.concept.asInstanceOf[T]
  }

  implicit val model = new Model

  val view = (model \ "views" \\ "general")(LayeredViewPoint)

  val overallGoal = in(view) { goal withName "conquest the World" }

  val goal1 = in(view) { goal withName "eat more" }
  in(view) { overallGoal `composes` goal1 }

  val wp = in(view) { workPackage withName "the project" }

  val wp1 = in(view) { workPackage withName "stage 1" }
  in(view) { wp `composes` wp1 }

  val wp2 = in(view) { workPackage withName "stage 2" }
  in(view) { wp `composes` wp2 }

  val wp3 = in(view) { workPackage withName "stage 3" }
  in(view) { wp `composes` wp3 }

  val resource1 = resource withName "resource-1"
  val resource2 = resource withName "resource-2"

  val bs = businessService withName "bs"
  var bo = businessObject withName "bo"

  val ae = applicationEvent withName "ae"
  val ac = applicationCollaboration

  ae `flows` "qwe" `to` ac

  bs `reads` bo
  bs `writes` bo

  bs `flows` "qwe" `to` bs

  resource1 `associated with` resource2

  case class Resident(name: String, age: Int, role: Option[String])

  def main(args: Array[String]): Unit = {

    model.nodes.foreach { e => println(s"Node: ${e.id} -> ${e}") }
    model.edges.foreach { e => println(s"Edge: ${e.id} -> ${e}") }

    import org.mentha.utils.archimate.model.json._
    import play.api.libs.json._

    val json = Json.toJson(model).toString()

    println(json)

    val model2 = Json.parse(json).as[Model]

    model2.nodes.foreach { e => println(s"Node: ${e.id} -> ${e}") }
    model2.edges.foreach { e => println(s"Edge: ${e.id} -> ${e}") }

    val t0 = System.currentTimeMillis()
    val json2 = Json.toJson(model2).toString()
    val t1 = System.currentTimeMillis()

    println(json2)

    val t2 = System.currentTimeMillis()
    val model3 = Json.parse(json2).as[Model]
    val t3 = System.currentTimeMillis()

    println(s"toJson: ${t1-t0} ms")
    println(s"fromJson: ${t3-t2} ms")

    val json4 = """{"_tp":"model","nodes":{},"edges":{},"root":{"_tp":"folder","children":{"2n9rc4sqi000:61d10ncs52000":{"_tp":"folder","name":"qwe","children":{},"views":{}}},"views":{}}}"""
    val json5 = Json.toJson(Json.parse(json4).as[Model]).toString()
    println(json5)


  }

}
