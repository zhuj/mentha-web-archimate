package doc.viewpoints

import doc.viewpoints.MkProjectViewPoint.publishModel
import org.mentha.tools.archimate.model.Model
import org.mentha.tools.archimate.model.utils.MkModel

/**
  * The information structure viewpoint is comparable to the traditional information models created in the development of almost any information system. It shows the structure of the information used in the enterprise or in a specific business process or application, in terms of data types or (object-oriented) class structures. Furthermore, it may show how the information at the business level is represented at the application level in the form of the data structures used there, and how these are then mapped onto the underlying technology infrastructure; e.g., by means of a database schema.
  *
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/apdxc.html#_Toc451758130]]
  * @see [[http://pubs.opengroup.org/architecture/archimate2-doc/chap08.html#_Toc371945245]]
  */
object MkInformationStructureViewPoint extends MkModel {

  import org.mentha.tools.archimate.model._
  import org.mentha.tools.archimate.model.view._
  import org.mentha.tools.archimate.model.view.dsl._

  import org.mentha.tools.archimate.model.nodes.dsl.Business._
  import org.mentha.tools.archimate.model.nodes.dsl.Application._
  import org.mentha.tools.archimate.model.nodes.dsl.Technology._
  import org.mentha.tools.archimate.model.nodes.dsl._
  import org.mentha.tools.archimate.model.edges._

  def main(args: Array[String]): Unit = {

    implicit val space = Size(40, 40)
    implicit val model = new Model withId "vp-info-structure"
    implicit val view = model.add("v-main") { new View(ProjectViewPoint) }

    val me = in(view) node { meaning withName "Meaning" }
    val re = in(view) node { representation withName "Representation" } place(directions.Down, me)
    val bo = in(view) node { businessObject withName "Business Object" } place(directions.Right, re)
    val da = in(view) node { dataObject withName "Data Object" } place(directions.Down, bo)
    val ar = in(view) node { artifact withName "Artifact" } place(directions.Down, da)

    in(view) edge { $(re) `associated with` $(me) }
    in(view) edge { $(re) `realizes` $(bo) }
    in(view) edge { $(da) `realizes` $(bo) }
    in(view) edge { $(ar) `realizes` $(da) }

    println(json.toJsonString(model))
    publishModel(model)
  }

}

