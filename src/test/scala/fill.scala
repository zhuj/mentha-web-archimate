import java.io.File

import org.apache.commons.io.FileUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes.ElementMeta
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.view._

object fill {

  def main(args: Array[String]): Unit = {

    val layers = Seq(
      MotivationElements.motivationElements,
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
        view.add { new ViewNodeConcept[Element](e) withPosition(Point(x, y)) withSize(Size(W, H)) }
      }
    }

    val height = layers.length * (2*HE)
    val layers_v = for { (layer, idx) <- layers.zipWithIndex } yield {
      val y = idx * 2*HE - 0.5 * height
      add(layer, y)
    }

    val jsonFile = new File(s"src/test/elements.json")
    FileUtils.write(jsonFile, json.toJsonString(model), "UTF-8")

  }

}
