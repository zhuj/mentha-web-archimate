import java.io.File
import java.util

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes.ElementMeta
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.view._

import scala.xml.XML

object fill3 {

  val random = new util.Random(0)
  val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

  val keys = (xml \ "relations" \ "key")
    .map { el => (el \ "@char").text.charAt(0) -> ( (el \ "@relationship").text, (el \ "@verbs" ).text ) }
    .toMap

  val association: Char = 'o' // <key char="o" relationship="AssociationRelationship" />
  val rels = (xml \ "relations" \ "source")
    .flatMap {
      s => (s \ "target").flatMap {
        t => (t \ "@relations").text.toSeq
          .filterNot { _ == association }
          .map { keys }
          .map { r => (
            StringUtils.uncapitalize( r._1 ),
            StringUtils.uncapitalize( (s \ "@concept").text ),
            StringUtils.uncapitalize( (t \ "@concept").text )
          ) }
      }
    }
    .groupBy {
      case (_, s, t) => (s, t)
    }

  def main(args: Array[String]): Unit = {

    val layers = Map(
      "motivationElements" -> MotivationElements.motivationElements,
      "compositionElements" -> CompositionElements.compositionElements,
      "strategyElements" -> StrategyElements.strategyElements,
      "businessElements" -> BusinessElements.businessElements,
      "applicationElements" -> ApplicationElements.applicationElements,
      "technologyElements" -> TechnologyElements.technologyElements,
      "physicalElements" -> PhysicalElements.physicalElements,
      "implementationElements" -> ImplementationElements.implementationElements
    )

    val W = 140
    val H = 50
    val WE = W + 40
    val HE = H + 50

    val model = new Model

    def add(metas: Seq[ElementMeta[_]], name: String) = {
      val view = model.add { new View() withName(name) }
      val width = WE / 2.0 * metas.length
      val objects = for { (meta, idx) <- metas.zipWithIndex } yield {
        val e = model.add { meta.newInstance().asInstanceOf[Element] withName(meta.name) }
        val x = (idx * WE) / 2.0 - 0.5 * width
        val y = (idx % 2) * HE
        e -> view.add { new ViewNodeConcept[Element](e) withPosition(Point(x, y)) withSize(Size(W, H)) }
      }
      view -> objects
    }

    val layers_v = for { (name, layer) <- layers } yield {
      layer -> add(layer, name)
    }

    val j = json.toJsonString(model)
    json.fromJsonString(j)

    val jsonFile = new File(s"src/test/elements3.json")
    FileUtils.write(jsonFile, j, "UTF-8")

  }

}
