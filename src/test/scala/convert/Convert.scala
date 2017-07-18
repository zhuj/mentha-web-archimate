package convert

import java.io.{File, StringReader}

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges.RelationshipMeta
import org.mentha.utils.archimate.model.json
import org.mentha.utils.archimate.model.nodes.{ElementMeta, RelationshipConnectorMeta}
import org.mentha.utils.archimate.model.view._

import scala.util.Try
import scala.xml.XML


object Convert {

  val allMeta: Seq[ConceptMeta[_]] = edges.allRelations ++ nodes.allNodes
  val mapMeta: Map[String, ConceptMeta[_]] = allMeta.map { m => (m.name, m.asInstanceOf[ConceptMeta[Concept]]) }.toMap

  val tpTransformation: PartialFunction[String, String] = {
    case "usedByRelationship" => "associationRelationship"
    case "realisationRelationship" => "realizationRelationship"
    case "specialisationRelationship" => "specializationRelationship"
    case s => s
  }

  def main(args: Array[String]): Unit = {
    val model = new Model

    val xmlFile = new File("src/test/epp-price.archimate")
    val xmlContent = FileUtils.readFileToString(xmlFile, "UTF-8")
    val xml = XML.load(new StringReader(xmlContent))

    val elements = (xml \ "folder").filter { n => "diagrams" != (n \ "@type").text } \ "element"
    for {el <- elements} {
      val tp = tpTransformation(
        StringUtils.uncapitalize(
          StringUtils.substringAfter(
            (el \ "@{http://www.w3.org/2001/XMLSchema-instance}type").text,
            ":"
          )
        )
      )

      val meta = mapMeta(tp)

      meta match {
        case em: ElementMeta[_] => {
          val concept = em.newInstance()
            .withName( el \@ "name" )
          model.add(el \@ "id")(concept)
        }
        case rm: RelationshipMeta[_] => {
          val concept = rm.newInstance(
            model.concept( el \@ "source" ),
            model.concept( el \@ "target" )
          )
          model.add(el \@ "id")(concept)
        }
        case cm: RelationshipConnectorMeta[_] => {
          throw new IllegalStateException(s"unsupported: ${cm.name}")
          val concept = cm.newInstance(null)
          model.add(el \@ "id")(concept)
        }
      }
    }

    val diagrams = (xml \ "folder").filter { n => "diagrams" == (n \ "@type").text } \ "element"
    for {dia <- diagrams if dia \@ "{http://www.w3.org/2001/XMLSchema-instance}type" == "archimate:ArchimateDiagramModel"} {
      val view = model.add( dia \@ "id" ) { new View() withName( dia \@ "name" ) }

      // first apply all nodes
      for {child <- dia \\ "child"} {
        require(child \@ "{http://www.w3.org/2001/XMLSchema-instance}type" == "archimate:DiagramObject")
        val position = (child \ "bounds") map { v => Point(java.lang.Double.parseDouble(v \@ "x"), java.lang.Double.parseDouble(v \@ "y")) } head
        val size = Try {
          (child \ "bounds") map { v => Size(java.lang.Double.parseDouble(v \@ "width"), java.lang.Double.parseDouble(v \@ "height")) } head
        } .getOrElse{ Size(100, 40) }
        val concept = model.concept[NodeConcept](child \@ "archimateElement")
        view.add(child \@ "id") { new ViewNodeConcept[NodeConcept](concept) withPosition(position) withSize(size) }
      }

      // then apply their edges
      for {connection <- dia \\ "sourceConnection"} {
        val relationship = model.concept[Relationship](connection \@ "relationship")
        val source = view.get[ViewObject with ViewConcept](connection \@ "source")
        val target = view.get[ViewObject with ViewConcept](connection \@ "target")
        view.add(connection \@ "id") { new ViewRelationship[Relationship](source, target)(relationship) }
      }

      println(dia)
    }


    val jsonFile = new File("src/test/app-price.json")
    FileUtils.write(jsonFile, json.toJsonString(model), "UTF-8")
  }

}