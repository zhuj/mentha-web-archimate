package doc.viewpoints

import org.mentha.tools.archimate.model.Model
import org.mentha.tools.archimate.model.utils.MkModel

/**
  * A project viewpoint is primarily used to model the management of architecture change. The “architecture” of the migration process from an old situation (current state Enterprise Architecture) to a new desired situation (target state Enterprise Architecture) has significant consequences on the medium and long-term growth strategy and the subsequent decision-making process.
  *
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758145]]
  * @see [[http://pubs.opengroup.org/architecture/archimate2-doc/chap11.html#_Toc371945288]]
  */
object MkProjectViewPoint extends MkModel {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.view._
  import org.mentha.tools.archimate.model.view.dsl._

  import org.mentha.tools.archimate.model.nodes.dsl.Implementation._
  import org.mentha.tools.archimate.model.nodes.dsl.Business._
  import org.mentha.tools.archimate.model.nodes.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(40, 40)
    implicit val model = new Model withId "vp-project"
    implicit val view = model.add("v-main") { new View(ProjectViewPoint) }

    val wp = in(view) node { workPackage withName "Work Package" }
    val g = in(view) node { goal withName "Goal" } place(directions.Up, wp)
    val d = in(view) node { deliverable withName "Deliverable" } place(directions.Right, wp)
    val br = in(view) node { businessRole withName "Business Role" } place(directions.Left, wp)
    val ba = in(view) node { businessActor withName "Business Actor" } place(directions.Left, br)

    in(view) edge { $(wp) `flows` "smth" `to` $(wp) } routeLoop(directions.Down, 1)
    in(view) edge { $(wp) `triggers` $(wp) } routeLoop(directions.Down, 2)
    in(view) edge { $(br) `assigned to` $(wp) }
    in(view) edge { $(ba) `assigned to` $(br) }
    in(view) edge { $(wp) `realizes` $(g) }
    in(view) edge { $(wp) `realizes` $(d) }

    println(json.toJsonString(model))
    publishModel(model)
  }

}
