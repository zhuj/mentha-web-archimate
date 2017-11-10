package doc.viewpoints

import org.mentha.tools.archimate.model.Model
import org.mentha.tools.archimate.model.utils.MkModel

/**
  * (v2 only) The goal contribution viewpoint allows a designer or analyst to model the influence relationships between goals and requirements. The resulting views can be used to analyze the impact that goals have on each other or to detect conflicts between stakeholder goals.
  *
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758136]]
  * @see [[http://pubs.opengroup.org/architecture/archimate2-doc/chap10.html#_Toc371945273]]
  */
object MkGoalContributionViewPoint extends MkModel {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.nodes.dsl.Motivation._
  import org.mentha.tools.archimate.model.nodes.dsl._
  import org.mentha.tools.archimate.model.view._
  import org.mentha.tools.archimate.model.view.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(40, 50)
    implicit val model = new Model withId "vp-goal-contribution"
    implicit val view = model.add("v-main") { new View(GoalRealizationViewPoint) }

    val g = in(view) node { goal withName "Goal" }
    val p = in(view) node { principle withName "Principle" } place(directions.Right, g)
    val rq = in(view) node { requirement withName "Requirement/\nConstraint" } place(directions.Down, g, p)

    in(view) edge { $(p) `realizes` $(g) } flex(0)
    in(view) edge { $(rq) `realizes` $(g) } flex(0)
    in(view) edge { $(rq) `realizes` $(p) } flex(0)

    in(view) edge { $(g) `influences` "+/-" `in` $(g) } routeLoop(directions.Up, 1)
    in(view) edge { $(g) `influences` "+/-" `in` $(p) } flex(1)
    in(view) edge { $(g) `influences` "+/-" `in` $(rq) } flex(1)

    in(view) edge { $(p) `influences` "+/-" `in` $(p) } routeLoop(directions.Up, 1)
    in(view) edge { $(p) `influences` "+/-" `in` $(g) } flex(1)
    in(view) edge { $(p) `influences` "+/-" `in` $(rq) } flex(1)

    in(view) edge { $(rq) `influences` "+/-" `in` $(rq) } routeLoop(directions.Down, 1)
    in(view) edge { $(rq) `influences` "+/-" `in` $(g) } flex(1)
    in(view) edge { $(rq) `influences` "+/-" `in` $(p) } flex(1)


    println(json.toJsonString(model))
    publishModel(model)
  }

}
