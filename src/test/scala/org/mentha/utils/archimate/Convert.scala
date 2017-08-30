package org.mentha.utils.archimate

import java.io.{File, StringReader}

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

import scala.util.Try
import scala.xml.{Node, NodeSeq, XML}

object Convert extends MkModel {

  val allMeta: Seq[ConceptMeta[Concept]] = edges.allRelations ++ nodes.allNodes
  val mapMeta: Map[String, ConceptMeta[Concept]] = allMeta.map { m => (m.name, m) }.toMap

  val tpTransformation: PartialFunction[String, String] = {
    case "network" => "communicationNetwork"
    case "infrastructureFunction" => "technologyFunction"
    case "infrastructureService" => "technologyService"
    case "usedByRelationship" => "servingRelationship"
    case "realisationRelationship" => "realizationRelationship"
    case "specialisationRelationship" => "specializationRelationship"
    case "junction" => "andJunction"
    case s => s
  }

  def main(args: Array[String]): Unit = {
    val name = args(0)
    val model = new Model withId s"convert-${name.replace('/','-')}"

    val xmlFile = new File(s"${name}.archimate")
    val xmlContent = FileUtils.readFileToString(xmlFile, "UTF-8")
    val xml = XML.load(new StringReader(xmlContent))

    val elements = (xml \ "folder").filter { n => "diagrams" != n \@ "type" } \\ "element"

    // first - nodes
    for {el <- elements} {
      val tp: String = getTp(el)

      val meta = mapMeta(tp)

      meta match {
        case em: ElementMeta[_] => {
          val concept = em.newInstance()
            .withName( el \@ "name" )
          model.add(el \@ "id")(concept)
        }
        case cm: RelationshipConnectorMeta[_] => {

          val id = (el \@ "id")
          val rels = elements
            .map { case rel => Option(rel) map { getTp } map { mapMeta } map { m => (rel, m) } }
            .collect { case Some((rel, tp: RelationshipMeta[_])) if (rel \@ "source") == id || (rel \@ "target") == id => tp }
            .toSet

          if (rels.size != 1) {
            throw new IllegalStateException(s"${cm.name}: relations={${rels.mkString(",")}}")
          }

          val concept = cm.newInstance(rels.head)
          model.add(id)(concept)
        }
        case _ =>
      }
    }

    // then - edges
    for {el <- elements} {
      val tp: String = getTp(el)

      val meta = mapMeta(tp)

      meta match {
        case rm: RelationshipMeta[_] => {
          val src: Concept = model.concept(el \@ "source")
          val dst: Concept = model.concept(el \@ "target")

          val concept = Try[Relationship] {
            val r = rm.newInstance(src, dst)
            if (!r.valid) { throw new IllegalStateException("Invalid") }
            r
          } recover {
            case _ => OtherRelationships.associationRelationship.newInstance(src, dst)
          } get


          model.add(el \@ "id")(concept)
        }
        case _ =>
      }
    }


    // process views after all
    val diagrams = (xml \ "folder").filter { n => "diagrams" == n \@ "type" } \\ "element"
    for {dia <- diagrams if dia \@ "{http://www.w3.org/2001/XMLSchema-instance}type" == "archimate:ArchimateDiagramModel"} {
      val view = model.add( dia \@ "id" ) { new View() withName( dia \@ "name" ) }

      def applyChildren(childs: NodeSeq, baseNode: Option[ViewNode]): Unit = {
        val base = baseNode map { n => Point(n.position.x - n.size.width/2, n.position.y - n.size.height/2) } getOrElse { Point(0, 0) }
        for {child <- childs} {

          val chType = child \@ "{http://www.w3.org/2001/XMLSchema-instance}type"
          val size = Try {
            (child \ "bounds") map { v => Size(java.lang.Double.parseDouble(v \@ "width"), java.lang.Double.parseDouble(v \@ "height")) } head
          } getOrElse {
            Size(100, 40)
          }
          val lt = Try {
            (child \ "bounds") map { v => Point(java.lang.Double.parseDouble(v \@ "x") + base.x, java.lang.Double.parseDouble(v \@ "y") + base.y) } head
          } getOrElse {
            base
          }
          val position = lt.copy(lt.x + size.width / 2, lt.y + size.height / 2)


          val node: ViewNode = if (chType == "archimate:DiagramObject") {
            val concept = model.concept[NodeConcept](child \@ "archimateElement")
            view.add(child \@ "id") {
              new ViewNodeConcept[NodeConcept](concept) withPosition (position) withSize (size)
            }
          } else if (chType == "archimate:Note") {
            view.add(child \@ "id") {
              new ViewNotes() withText ((child \ "content").text) withPosition (position) withSize (size)
            }
          } else if (chType == "archimate:Group") {
            view.add(child \@ "id") {
              new ViewGroup() withName (child \@ "name") withPosition (position) withSize (size)
            }
          } else {
            println(s"Unexpected type: ${chType}")
            null
          }

          for {
            bn <- baseNode.collect { case x: ViewObject with ViewConcept[Concept] => x }
            nn <- Option(node).collect { case x: ViewObject with ViewConcept[Concept] => x }
          } {
            val priority = Map(
              StructuralRelationships.compositionRelationship.key -> 0,
              StructuralRelationships.aggregationRelationship.key -> 1,
            ).withDefaultValue(2)

            val maybeRelationship = model
              .concepts[StructuralRelationship].toSeq
              .sortBy { r => priority(r.meta.key) }
              .collectFirst { case r if (r.source.id == bn.concept.id) && (r.target.id == nn.concept.id) => r }
              .orElse {
                Try[Relationship] {
                  model.add(s"${bn.concept.id}-${nn.concept.id}") {
                    if (bn.concept.isInstanceOf[Grouping]) {
                      new AggregationRelationship(bn.concept, nn.concept).validate
                    } else {
                      new CompositionRelationship(bn.concept, nn.concept).validate
                    }
                  }
                } toOption
              }

            for { rr <- maybeRelationship } {
              view.add(s"${rr.id}-${view.id}") {
                new ViewRelationship[Relationship](bn, nn)(rr)
              }
            }
          }

          applyChildren(child \ "child", Option(node))
        }
      }

      // first apply all nodes
      applyChildren(dia \ "child", None)

      // then apply their edges
      for {connection <- dia \\ "sourceConnection"} {
        val relId = (connection \@ "relationship")


        val points = {
          val source = view.get[ViewObject](connection \@ "source")
          val target = view.get[ViewObject](connection \@ "target")

          // http://grepcode.com/file/repository.grepcode.com/java/eclipse.org/3.5.2/org.eclipse/draw2d/3.5.2/org/eclipse/draw2d/RelativeBendpoint.java#RelativeBendpoint.getLocation%28%29
          // http://grepcode.com/file/repository.grepcode.com/java/eclipse.org/3.5.2/org.eclipse/draw2d/3.5.2/org/eclipse/draw2d/Figure.java#1705
          def toDouble(x: String) = Try { java.lang.Double.parseDouble(x) } getOrElse( 0.0 )
          val bendpoints = {
            for {p <- (connection \ "bendpoint")} yield {
              val startX = toDouble(p \@ "startX")
              val startY = toDouble(p \@ "startY")
              val endX = toDouble(p \@ "endX")
              val endY = toDouble(p \@ "endY")
              (startX, startY, endX, endY)
            }
          }
          val size = bendpoints.size
          for { ((startX, startY, endX, endY), i) <- bendpoints.zipWithIndex } yield {
            val w = (1.0+i) / (1.0+size)
            val x = w*(startX + source.position.x) + (1.0-w)*(endX + target.position.x)
            val y = w*(startY + source.position.y) + (1.0-w)*(endY + target.position.y)
            Point(x, y)
          }
        }

        //println(points)

        if (null != relId && relId.length > 0) {
          val relationship = model.concept[Relationship](connection \@ "relationship")
          val source = view.get[ViewObject with ViewConcept[Concept]](connection \@ "source")
          val target = view.get[ViewObject with ViewConcept[Concept]](connection \@ "target")
          view.add(connection \@ "id") { new ViewRelationship[Relationship](source, target)(relationship) } withPoints(points)
        } else {
          val source = view.get[ViewObject](connection \@ "source")
          val target = view.get[ViewObject](connection \@ "target")
          view.add(connection \@ "id") { new ViewConnection(source, target) } withPoints(points)
        }
      }

      //println(dia)
    }

    val str = json.toJsonString(model)
    json.fromJsonString(str) // check

    val jsonFile = new File(s"${name}.json")
    FileUtils.write(jsonFile, str, "UTF-8")

    publishModel(model)
  }

  private def getTp(el: Node) = {
    val tp = tpTransformation(
      StringUtils.uncapitalize(
        StringUtils.substringAfter(
          (el \ "@{http://www.w3.org/2001/XMLSchema-instance}type").text,
          ":"
        )
      )
    )
    tp
  }
}