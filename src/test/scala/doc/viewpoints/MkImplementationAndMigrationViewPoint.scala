package doc.viewpoints

import doc.viewpoints.MkProjectViewPoint.publishModel
import org.mentha.utils.archimate.model.utils.MkModel

/**
  * The implementation and migration viewpoint is used to relate programs and projects to the parts of the architecture that they implement. This view allows modeling of the scope of programs, projects, project activities in terms of the plateaus that are realized or the individual architecture elements that are affected. In addition, the way the elements are affected may be indicated by annotating the relationships.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758147]]
  * @see [[http://pubs.opengroup.org/architecture/archimate2-doc/chap11.html#_Toc371945290]]
  */
object MkImplementationAndMigrationViewPoint extends MkModel {

  import org.mentha.utils.archimate.model._
  import org.mentha.utils.archimate.model.view._
  import org.mentha.utils.archimate.model.view.dsl._

  import org.mentha.utils.archimate.model.edges._
  import org.mentha.utils.archimate.model.nodes.dsl.Implementation._
  import org.mentha.utils.archimate.model.nodes.dsl.Motivation._
  import org.mentha.utils.archimate.model.nodes.dsl.Business._
  import org.mentha.utils.archimate.model.nodes.dsl.Composition._
  import org.mentha.utils.archimate.model.nodes.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(40, 40)
    implicit val model = new Model withId "vp-impl-n-mig"
    implicit val view = model.add("v-main") { new View(ProjectViewPoint) }

    val de = in(view) node { deliverable withName "Deliverable" }
    val wp = in(view) node { workPackage withName "Work Package" } place(directions.Left, de)

    val br = in(view) node { businessRole withName "Business Role" } place(directions.Left, wp)
    val ba = in(view) node { businessActor withName "Business Actor" } place(directions.Down, br)

    val lo = in(view) node { location withName "Location" } place(directions.Up, br)

    val rq = in(view) node { requirement withName "Requirement/\nConstraint" } place(directions.Up, de)
    val go = in(view) node { goal withName "Goal" } place(directions.Right, rq)

    val pl = in(view) node { plateau withName "Plateau" } place(directions.Down, de)
    val ga = in(view) node { gap withName "Gap" } place(directions.Down, pl)

    in(view) edge { $(wp) `flows` "smth" `to` $(wp) } routeLoop(directions.Down, 1)
    in(view) edge { $(wp) `triggers` $(wp) } routeLoop(directions.Down, 2)

    in(view) edge { $(lo) `associated with` $(de) } flex (-1, -1, -1)
    in(view) edge { $(lo) `associated with` $(wp) } flex (-1, -1)

    in(view) edge { $(lo) `assigned to` $(br) }
    in(view) edge { $(ba) `assigned to` $(br) }
    in(view) edge { $(br) `assigned to` $(wp) }

    in(view) edge { $(wp) `realizes` $(de) }
    in(view) edge { $(de) `realizes` $(rq) }
    in(view) edge { $(rq) `realizes` $(go) }

    in(view) edge { $(de) `realizes` $(pl) }
    in(view) edge { $(pl) `triggers` $(pl) } routeLoop (directions.Left, 1)
    in(view) edge { $(pl) `associated with` $(ga) }

    println(json.toJsonString(model))
    publishModel(model)
  }

}
