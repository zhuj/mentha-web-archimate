package org.mentha.tools.archimate.model.nodes.impl

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.nodes._
import org.mentha.tools.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
sealed trait ImplementationElement extends ImplementationLayer {}

/**
 * A series of actions identified and designed to achieve specific results within specified time and resource constraints.
 * ==Overview==
 * A work package represents a series of actions identified and designed to achieve specific results within specified time and resource constraints.
 * @note The central behavioral element is a work package. A work package is a behavior element that has a clearly defined start and end date, and realizes a well-defined set of goals or deliverables.
 * @note The work package element can be used to model sub-projects or tasks within a project, complete projects, programs, or project portfolios.
 * @note Conceptually, a work package is similar to a business process, in that it consists of a set of causally-related tasks, aimed at producing a well-defined result. However, a work package is a unique, “one-off” process. Still, a work package can be described in a way very similar to the description of a process.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap13.html#_Toc451758085 WorkPackage ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class WorkPackage extends BehaviorElement with ImplementationElement {
  @inline override def meta: ElementMeta[WorkPackage] = ImplementationElements.workPackage
}

/**
 * A precisely-defined outcome of a work package.
 * ==Overview==
 * A deliverable represents a precisely-defined outcome of a work package.
 * @note Work packages produce deliverables. These may be results of any kind; e.g., reports, papers, services, software, physical products, etc., or intangible results such as organizational change. A deliverable may also be the implementation of (a part of) an architecture.
 * @note Often, deliverables are contractually specified and in turn formally reviewed, agreed, and signed off by the stakeholders as is, for example, prescribed by the TOGAF framework.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap13.html#_Toc451758086 Deliverable ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class Deliverable extends PassiveStructureElement with ImplementationElement {
  @inline override def meta: ElementMeta[Deliverable] = ImplementationElements.deliverable
}

/**
 * A behavior element that denotes a state change related to implementation or migration.
 * ==Overview==
 * An implementation event is a behavior element that denotes a state change related to implementation or migration.
 * @note Work packages may be triggered or interrupted by an implementation event. Also, work packages may raise events that trigger other behavior. Unlike a work package, an event is instantaneous: it does not have duration.
 * @note An implementation event may have a time attribute that denotes the moment or moments at which the event happens. For example, this can be used to model project schedules and milestones; e.g., an event that triggers a work package, an event that denotes its completion (with a triggering relationship from the work package to the event), or an event that denotes a lifecycle change of a deliverable (via an access relationship to that deliverable).
 * @note Implementation events access deliverables to fulfill project objectives. For example, in a project to deliver a completely new application along with the technology needed to host it, an implementation event “Release to production” could access the deliverables “Final build”, “staging environment”, and “Production environment”.
 * @note An implementation event may trigger or be triggered (raised) by a work package or a plateau. An implementation event may access a deliverable and may be composed of other implementation events.
 * @note An implementation event may be associated with any core element; e.g., to indicate a lifecycle state change. The name of an implementation event should preferably be a verb in the perfect tense; e.g., “project initiation phase completed”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap13.html#_Toc451758087 ImplementationEvent ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class ImplementationEvent extends Event with ImplementationElement {
  @inline override def meta: ElementMeta[ImplementationEvent] = ImplementationElements.implementationEvent
}

/**
 * A relatively stable state of the architecture that exists during a limited period of time.
 * ==Overview==
 * A plateau represents a relatively stable state of the architecture that exists during a limited period of time.
 * @note An important premise in the TOGAF framework is that the various architectures are described for different stages in time. In each of the Phases B, C, and D of the ADM, a Baseline Architecture and Target Architecture are created, describing the current situation and the desired future situation. In Phase E (Opportunities and Solutions), so-called Transition Architectures are defined, showing the enterprise at incremental states reflecting periods of transition between the Baseline and Target Architectures. Transition Architectures are used to allow for individual work packages and projects to be grouped into managed portfolios and programs, illustrating the business value at each stage. In order to support this, the plateau element is defined.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap13.html#_Toc451758088 Plateau ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class Plateau extends CompositeElement with ImplementationElement {
  @inline override def meta: ElementMeta[Plateau] = ImplementationElements.plateau
}

/**
 * A statement of difference between two plateaus.
 * ==Overview==
 * A gap represents a statement of difference between two plateaus.
 * @note The gap element is associated with two plateaus (e.g., Baseline and Target Architectures, or two subsequent Transition Architectures), and represents the differences between these plateaus.
 * @note In the TOGAF framework, a gap is an important outcome of a gap analysis in Phases B, C, and D of the ADM process, and forms an important input for the subsequent implementation and migration planning.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap13.html#_Toc451758089 Gap ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class Gap extends PassiveStructureElement with ImplementationElement {
  @inline override def meta: ElementMeta[Gap] = ImplementationElements.gap
}

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
object ImplementationElements {

  case object workPackage extends ElementMeta[WorkPackage] {
    override final def key: String = "iwp"
    override final def name: String = "workPackage"
    override final def layerObject: LayerObject = ImplementationLayer
    override final def newInstance(): WorkPackage = new WorkPackage
  }
  case object deliverable extends ElementMeta[Deliverable] {
    override final def key: String = "idl"
    override final def name: String = "deliverable"
    override final def layerObject: LayerObject = ImplementationLayer
    override final def newInstance(): Deliverable = new Deliverable
  }
  case object implementationEvent extends ElementMeta[ImplementationEvent] {
    override final def key: String = "iev"
    override final def name: String = "implementationEvent"
    override final def layerObject: LayerObject = ImplementationLayer
    override final def newInstance(): ImplementationEvent = new ImplementationEvent
  }
  case object plateau extends ElementMeta[Plateau] {
    override final def key: String = "ipl"
    override final def name: String = "plateau"
    override final def layerObject: LayerObject = ImplementationLayer
    override final def newInstance(): Plateau = new Plateau
  }
  case object gap extends ElementMeta[Gap] {
    override final def key: String = "iga"
    override final def name: String = "gap"
    override final def layerObject: LayerObject = ImplementationLayer
    override final def newInstance(): Gap = new Gap
  }

  val implementationElements: Seq[ElementMeta[Element]] = Seq(workPackage, deliverable, implementationEvent, plateau, gap)

}
