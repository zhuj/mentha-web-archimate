import java.io.File
import java.util

import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes.ElementMeta
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.view._

import scala.xml.XML

object fill {

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

    val layers = Seq(
      MotivationElements.motivationElements,
      CompositionElements.compositionElements,
      StrategyElements.strategyElements,
      BusinessElements.businessElements,
      ApplicationElements.applicationElements,
      TechnologyElements.technologyElements,
      PhysicalElements.physicalElements,
      ImplementationElements.implementationElements
    )

    val W = 140
    val H = 50
    val E = 20
    val WE = W + E
    val HE = H + E

    val model = new Model
    val view = model.add { new View() }

    def add(metas: Seq[ElementMeta[_]], yb: Double) = {
      val width = WE / 2.0 * metas.length
      for { (meta, idx) <- metas.zipWithIndex } yield {
        val e = model.add { meta.newInstance().asInstanceOf[Element] withName(meta.name) }
        val x = (idx * WE) / 2.0 - 0.5 * width
        val y = yb + (idx % 2) * HE
        e -> view.add { new ViewNodeConcept[Element](e) withPosition(Point(x, y)) withSize(Size(W, H)) }
      }
    }

    val height = layers.length * (2*HE)
    val layers_v = for { (layer, idx) <- layers.zipWithIndex } yield {
      val y = idx * 2*HE - 0.5 * height
      layer -> add(layer, y)
    }

    for {
      (_, l1v) <- layers_v
      (e1, e1v) <- l1v
    } {
      for { (_, l2v) <- layers_v } {

        val candidates = l2v
          .filter { case (e, _) => e != e1 }
          .sortBy { _ => random.nextInt() }
          .toStream
          .map { case (e, v) => (e, v, rels.get((e1.meta.name, e.meta.name))) }
          .collect { case (e, v, Some(rr)) => (e, v, rr(random.nextInt(rr.length))) }
          .take(3)

        for {(e2, e2v, (rn, _, _)) <- candidates } {
          println(e1.meta.name + " -- (" + rn + ") --> " + e2.meta.name)

          val r = model.add { edges.mapRelations(rn).newInstance(e1, e2) }
          val v = view.add { new ViewRelationship[Relationship](e1v, e2v)(r) }
        }

      }

    }


    val j = json.toJsonString(model)
    json.fromJsonString(j)

    val jsonFile = new File(s"src/test/elements2.json")
    FileUtils.write(jsonFile, j, "UTF-8")

  }

}
