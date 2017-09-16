package doc.examples

import org.mentha.utils.archimate.model.utils.MkModel

/**
  * Edges samples
  */
object MkEdges extends MkModel {

  import org.mentha.utils.archimate.model._
  import org.mentha.utils.archimate.model.edges._
  import org.mentha.utils.archimate.model.nodes.dsl.Business._
  import org.mentha.utils.archimate.model.nodes.dsl.Motivation._
  import org.mentha.utils.archimate.model.nodes.dsl._
  import org.mentha.utils.archimate.model.view._
  import org.mentha.utils.archimate.model.view.dsl._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(120, 10)
    implicit val model = new Model withId "ex-edges"
    implicit val view = model.add("v-main") { new View() }


    // compositionRelationship
    val o1l = in(view) node { goal }
    val o1r = in(view) node { goal } place(directions.Right, o1l)
    in(view) edge { $(o1l) `composes` $(o1r) }
    in(view) connection ( o1r, in(view) notes "Composition Relationship" doubleWidth() place(directions.Right, o1r) )

    // aggregationRelationship
    val o2l = in(view) node { goal } place(directions.Down, o1l)
    val o2r = in(view) node { goal } place(directions.Right, o2l)
    in(view) edge { $(o2l) `aggregates` $(o2r) }
    in(view) connection ( o2r, in(view) notes "Aggregation Relationship" doubleWidth() place(directions.Right, o2r) )

    // assignmentRelationship
    val o3l = in(view) node { businessActor } place(directions.Down, o2l)
    val o3r = in(view) node { businessFunction } place(directions.Right, o3l)
    in(view) edge { $(o3l) `assigned to` $(o3r) }
    in(view) connection ( o3r, in(view) notes "Assignment Relationship" doubleWidth() place(directions.Right, o3r) )

    // realizationRelationship
    val o4l = in(view) node { businessFunction } place(directions.Down, o3l)
    val o4r = in(view) node { businessService } place(directions.Right, o4l)
    in(view) edge { $(o4l) `realizes` $(o4r) }
    in(view) connection ( o4r, in(view) notes "Realization Relationship" doubleWidth() place(directions.Right, o4r) )

    // servingRelationship
    val o5l = in(view) node { businessFunction } place(directions.Down, o4l)
    val o5r = in(view) node { businessProcess } place(directions.Right, o5l)
    in(view) edge { $(o5l) `serves` $(o5r) }
    in(view) connection ( o5r, in(view) notes "Serving Relationship" doubleWidth() place(directions.Right, o5r) )

    // accessRelationship
    val o6l = in(view) node { businessProcess } place(directions.Down, o5l)
    val o6r = in(view) node { businessObject } place(directions.Right, o6l)
    in(view) edge { $(o6l) `accesses` ReadWriteAccess `of` $(o6r) }
    in(view) connection ( o6r, in(view) notes "Access Relationship" doubleWidth() place(directions.Right, o6r) )

    // influenceRelationship
    val o7l = in(view) node { driver } place(directions.Down, o6l)
    val o7r = in(view) node { goal } place(directions.Right, o7l)
    in(view) edge { $(o7l) `influences` "+/-" `in` $(o7r) }
    in(view) connection ( o7r, in(view) notes "Influence Relationship" doubleWidth() place(directions.Right, o7r) )

    // triggeringRelationship
    val o8l = in(view) node { businessEvent } place(directions.Down, o7l)
    val o8r = in(view) node { businessProcess } place(directions.Right, o8l)
    in(view) edge { $(o8l) `triggers` $(o8r) }
    in(view) connection ( o8r, in(view) notes "Triggering Relationship" doubleWidth() place(directions.Right, o8r) )

    // flowRelationship
    val o9l = in(view) node { businessEvent } place(directions.Down, o8l)
    val o9r = in(view) node { businessProcess } place(directions.Right, o9l)
    in(view) edge { $(o9l) `flows` "something" `to` $(o9r) }
    in(view) connection ( o9r, in(view) notes "Flow Relationship" doubleWidth() place(directions.Right, o9r) )

    // specializationRelationship
    val oAl = in(view) node { businessEvent } place(directions.Down, o9l)
    val oAr = in(view) node { businessEvent } place(directions.Right, oAl)
    in(view) edge { $(oAl) `specializes` $(oAr) }
    in(view) connection ( oAr, in(view) notes "Specialization Relationship" doubleWidth() place(directions.Right, oAr) )

    // associationRelationship
    val oBl = in(view) node { stakeholder } place(directions.Down, oAl)
    val oBr = in(view) node { businessEvent } place(directions.Right, oBl)
    in(view) edge { $(oBl) `associated with` $(oBr) withPredicate "is associated with" }
    in(view) connection ( oBr, in(view) notes "Association Relationship" doubleWidth() place(directions.Right, oBr) )

    println(json.toJsonString(model))
    publishModel(model)
  }

}
