package doc.viewpoint

import doc.MkModel

/**
  *
  */
object MkProjectViewPoint extends MkModel {

  import org.mentha.utils.archimate.model._
  import org.mentha.utils.archimate.model.view._
  import org.mentha.utils.archimate.model.view.View._

  import org.mentha.utils.archimate.model.edges._
  import org.mentha.utils.archimate.model.nodes.dsl.Application._
  import org.mentha.utils.archimate.model.nodes.dsl.Business._
  import org.mentha.utils.archimate.model.nodes.dsl.Implementation._
  import org.mentha.utils.archimate.model.nodes.dsl.Motivation._
  import org.mentha.utils.archimate.model.nodes.dsl._

  def main(args: Array[String]): Unit = {

    implicit val model = new Model withId("vp-project")
    implicit val view = model.add("v-main") { new View(ProjectViewPoint) }

    val wp = in(view) node { workPackage withName "Work Package" }
    val g = in(view) node { goal withName "Goal" } placeUp(wp)
    val d = in(view) node { deliverable withName "Deliverable" } placeLeft(wp)
    val br = in(view) node { businessRole withName "Business Role" } placeRight(wp)
    val ba = in(view) node { businessActor withName "Business Actor" } placeRight(br)

    in(view) edge { c(wp) `flows` "smth" `to` c(wp) } route ((-70, 40), (140, 0))
    in(view) edge { c(wp) `triggers` c(wp) } route ((-40, 30), (80, 0))
    in(view) edge { c(wp) `associated with` c(br) }
    in(view) edge { c(br) `associated with` c(ba) }
    in(view) edge { c(wp) `realizes` c(g) }
    in(view) edge { c(wp) `realizes` c(d) }

    println(json.toJsonString(model))
    publishModel(model)
  }

}
