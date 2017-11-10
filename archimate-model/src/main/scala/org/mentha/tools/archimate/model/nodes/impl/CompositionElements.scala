package org.mentha.tools.archimate.model.nodes.impl

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.nodes._
import org.mentha.tools.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
sealed trait CompositionElement extends CompositionLayer {}

/**
 * Aggregates or composes concepts that belong together based on some common characteristic.
 * ==Overview==
 * The grouping element aggregates or composes concepts that belong together based on some common characteristic.
 * @note The grouping element is used to aggregate or compose an arbitrary group of concepts, which can be elements and/or relationships of the same or of different types. An aggregation or composition relationship is used to link the grouping element to the grouped concepts.
 * @note Concepts may be aggregated by multiple (overlapping) groups.
 * @note One useful way of employing grouping is for modeling Architecture and Solution Building Blocks (ABBs and SBBs), as described in the TOGAF framework. Another useful application of grouping is for modeling domains. For example, the TOGAF framework Glossary of Supplementary Definition (Section A.40) defines Information Domain as: “grouping of information (or data entities) by a set of criteria such as security classification, ownership, location, etc. In the context of security, Information Domains are defined as a set of users, their information objects, and a security policy”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap04.html#_Toc451757949 Grouping ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class Grouping extends CompositeElement with CompositionElement {
  @inline override def meta: ElementMeta[Grouping] = CompositionElements.grouping
}

/**
 * A place or position where structure elements can be located or behavior can be performed
 * ==Overview==
 * A location is a place or position where structure elements can be located or behavior can be performed.
 * @note The location element is used to model the places where (active and passive) structure elements such as business actors, application components, and devices are located. This is modeled by means of an aggregation relationship from a location to structure element. A location can also aggregate a behavior element, to indicate where the behavior is performed. This element corresponds to the “Where” column of the Zachman framework.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap04.html#_Toc451757950 Location ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class Location extends CompositeElement with CompositionElement {
  @inline override def meta: ElementMeta[Location] = CompositionElements.location
}

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
object CompositionElements {

  case object grouping extends ElementMeta[Grouping] {
    override final def key: String = "cgr"
    override final def name: String = "grouping"
    override final def layerObject: LayerObject = CompositionLayer
    override final def newInstance(): Grouping = new Grouping
  }
  case object location extends ElementMeta[Location] {
    override final def key: String = "clo"
    override final def name: String = "location"
    override final def layerObject: LayerObject = CompositionLayer
    override final def newInstance(): Location = new Location
  }

  val compositionElements: Seq[ElementMeta[Element]] = Seq(grouping, location)

}
