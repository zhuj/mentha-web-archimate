package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait StrategyElement extends StrategyLayer {}

/**
 * An asset owned or controlled by an individual or organization.
 * ==Overview==
 * A resource represents an asset owned or controlled by an individual or organization.
 * @note Resources are a central concept in the field of strategic management, economics, computer science, portfolio management, and more. They are often considered, together with capabilities, to be sources of competitive advantage for organizations. Resources are analyzed in terms of strengths and weaknesses, and they are considered when implementing strategies. Due to resources being limited, they can often be a deciding factor for choosing which strategy, goal, and project to implement and in which order. Resources can be classified into tangible assets – financial assets (e.g., cash, securities, borrowing capacity), physical assets (e.g., plant, equipment, land, mineral reserves), intangible assets (technology; e.g., patents, copyrights, trade secrets; reputation; e.g., brand, relationships; culture), and human assets (skills/know-how, capacity for communication and collaboration, motivation).
 * @note Resources are realized by active and passive structure elements. The name of a resource should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap07.html#_Toc451757995 Resource ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Resource extends StructureElement with StrategyElement {
  @inline override def meta: ElementMeta[Resource] = StrategyElements.resource
}

/**
 * An ability that an active structure element, such as an organization, person, or system, possesses.
 * ==Overview==
 * A capability represents an ability that an active structure element, such as an organization, person, or system, possesses.
 * @note In the field of business, strategic thinking and planning delivers strategies and high-level goals that are often not directly implementable in the architecture of an organization. These long-term or generic plans need to be specified and made actionable in a way that both business leaders and Enterprise Architects can relate to and at a relatively high abstraction level.
 * @note Capabilities help to reduce this gap by focusing on business outcomes. On the one hand, they provide a high-level view of the current and desired abilities of an organization, in relation to its strategy and its environment. On the other hand, they are realized by various elements (people, processes, systems, and so on) that can be described, designed, and implemented using Enterprise Architecture approaches. Capabilities may also have serving relationships; for example, to denote that one capability contributes to another.
 * @note Capabilities are expressed in general and high-level terms and are typically realized by a combination of organization, people, processes, information, and technology. For example, marketing, customer contact, or outbound telemarketing.
 * @note Capabilities are typically aimed at achieving some goal or delivering value by realizing an outcome. Capabilities are themselves realized by core elements. To denote that a set of core elements together realizes a capability, grouping can be used.
 * @note Capabilities are often used for capability-based planning, to describe their evolution over time. To model such so-called capability increments, the specialization relationship can be used to denote that a certain capability increment is a specific version of that capability. Aggregating those increments and the core elements that realize them in plateaus can be used to model the evolution of the capabilities.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap07.html#_Toc451757997 Capability ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Capability extends BehaviorElement with StrategyElement {
  @inline override def meta: ElementMeta[Capability] = StrategyElements.capability
}

/**
 * An approach or plan for configuring some capabilities and resources of the enterprise, undertaken to achieve a goal.
 * ==Overview==
 * A course of action is an approach or plan for configuring some capabilities and resources of the enterprise, undertaken to achieve a goal.
 * @note A course of action represents what an enterprise has decided to do. Courses of action can be categorized as strategies and tactics. It is not possible to make a hard distinction between the two, but strategies tend to be long-term and fairly broad in scope, while tactics tend to be shorter-term and narrower in scope.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap07.html#_Toc451757998 CourseOfAction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class CourseOfAction extends BehaviorElement with StrategyElement {
  @inline override def meta: ElementMeta[CourseOfAction] = StrategyElements.courseOfAction
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object StrategyElements {

  case object resource extends ElementMeta[Resource] {
    override def key: String = "srs"
    override def name: String = "resource"
    override def layerObject: LayerObject = StrategyLayer
    override def newInstance(): Resource = new Resource
  }
  case object capability extends ElementMeta[Capability] {
    override def key: String = "scb"
    override def name: String = "capability"
    override def layerObject: LayerObject = StrategyLayer
    override def newInstance(): Capability = new Capability
  }
  case object courseOfAction extends ElementMeta[CourseOfAction] {
    override def key: String = "sca"
    override def name: String = "courseOfAction"
    override def layerObject: LayerObject = StrategyLayer
    override def newInstance(): CourseOfAction = new CourseOfAction
  }

  val strategyElements: Seq[ElementMeta[Element]] = Seq(resource, capability, courseOfAction)

}
