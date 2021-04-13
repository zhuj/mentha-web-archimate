object traverser1 {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.edges._
  import org.mentha.tools.archimate.model.nodes.dsl._
  import org.mentha.tools.archimate.model.nodes.dsl.Business._
  import org.mentha.tools.archimate.model.nodes.dsl.Motivation._
  import org.mentha.tools.archimate.model.nodes.dsl.Implementation._
  import org.mentha.tools.archimate.model.nodes.dsl.Composition._
  import org.mentha.tools.archimate.model.view.dsl._
  import org.mentha.tools.archimate.model.view._

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


  import org.mentha.archimate.model.traverse._
  val traverser = new ModelTraversing(ModelTraversing.ChangesInfluence)(model)

  traverser($(de))( new ModelVisitor {
    override def visitVertex(vertex: Concept): Boolean = {
      println(s"visit-node: ${vertex.meta.name}@${vertex.id}")
      true
    }
    override def visitEdge(from: Concept, edge: EdgeConcept, to: Concept): Boolean = {
      println(s"visit-edge: ${from.meta.name}@${from.id} --~${edge.meta.name}~--> ${to.meta.name}@${to.id} ")
      true
    }
  })

  def main(args: Array[String]): Unit = {

  }
}