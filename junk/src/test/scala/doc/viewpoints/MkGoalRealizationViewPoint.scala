package doc.viewpoints

import org.mentha.tools.archimate.model.Model
import org.mentha.tools.archimate.model.utils.MkModel

/**
  * The goal realization viewpoint allows a designer to model the refinement of (high-level) goals into more tangible goals, and the refinement of tangible goals into requirements or constraints that describe the properties that are needed to realize the goals. The refinement of goals into sub-goals is modeled using the aggregation relationship. The refinement of goals into requirements is modeled using the realization relationship.
  *
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758136]]
  * @see [[http://pubs.opengroup.org/architecture/archimate2-doc/chap10.html#_Toc371945272]]
  */
object MkGoalRealizationViewPoint extends MkModel {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.nodes.dsl.Motivation._
  import org.mentha.tools.archimate.model.nodes.dsl._
  import org.mentha.tools.archimate.model.view._
  import org.mentha.tools.archimate.model.view.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(40, 50)
    implicit val model = new Model withId "vp-goal-realization"
    implicit val view = model.add("v-main") { new View(GoalRealizationViewPoint) }

    val g = in(view) node { goal withName "Goal" }
    val oc = in(view) node { outcome withName "Outcome" } place(directions.Right, g)
    val pr = in(view) node { principle withName "Principle" } place(directions.Right, oc)
    val rq = in(view) node { requirement withName "Requirement" } place(directions.Down, g, oc)
    val cs = in(view) node { constraint withName "Constraint" } place(directions.Down, oc, pr)


    in(view) edge { $(oc) `realizes` $(g) }
    in(view) edge { $(pr) `realizes` $(oc) }
    in(view) edge { $(pr) `realizes` $(g) } flex(1, 1)

    in(view) edge { $(rq) `realizes` $(g) }
    in(view) edge { $(rq) `realizes` $(oc) }
    in(view) edge { $(rq) `realizes` $(pr) }

    in(view) edge { $(cs) `realizes` $(g) }
    in(view) edge { $(cs) `realizes` $(oc) }
    in(view) edge { $(cs) `realizes` $(pr) }

    println(json.toJsonString(model))
    publishModel(model)
  }

}
