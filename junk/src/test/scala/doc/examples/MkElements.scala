package doc.examples

import org.apache.commons.lang3.StringUtils
import org.mentha.tools.archimate.model.Model
import org.mentha.tools.archimate.model.utils.MkModel

import scala.xml.XML

/**
  * Edges samples
  */
object MkElements extends MkModel {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.view._
  import org.mentha.tools.archimate.model.view.dsl._

  def main(args: Array[String]): Unit = {

    val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

    val elements = (xml \ "element")
      .map { el => (el \@ "layer", StringUtils.uncapitalize(el \@ "name"), el) }
      .groupBy { case (l, _, _) => l }

    implicit val space = Size(40, 10)
    implicit val model = new Model withId "ex-elements"

    for { (l, els) <- elements } {
      implicit val view = model.add(s"v-${l}") {  new View() withName { StringUtils.capitalize(l) } }

      var prev: Option[ViewObject] = None
      for { (_, n, el) <- els } {
        val ve = in(view) node { model add { nodes.mapElements(n).newInstance() } withName { StringUtils.capitalize(n) } } scaleWidth(1.5)
        for { p <- prev } { ve place(directions.Down, p) }
        prev = Some(ve)

        val vn1 = in(view) notes {  (el \ "info").map { _.text }.mkString("\n") } scaleWidth(4) scaleHeight (1.2) place(directions.Right, ve)
        //val vn2 = in(view) notes {  (el \ "text").map { _.text }.mkString("\n") } scaleWidth(10) scaleHeight(2.5) place(directions.Right, vn1)

        in(view) connection ( ve, vn1 )
        //in(view) connection ( vn1, vn2 )

      }
    }

    println(json.toJsonString(model))
    publishModel(model)
  }

}
