package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait ApplicationElement extends ApplicationLayer {}

/**
 * An encapsulation of application functionality aligned to implementation structure, which is modular and replaceable. It encapsulates its behavior and data, exposes services, and makes them available through interfaces.
 * ==Overview==
 * An application component represents an encapsulation of application functionality aligned to implementation structure, which is modular and replaceable. It encapsulates its behavior and data, exposes services, and makes them available through interfaces.
 * @note An application component is a self-contained unit. As such, it is independently deployable, re-usable, and replaceable. An application component performs one or more application functions. It encapsulates its contents: its functionality is only accessible through a set of application interfaces. Cooperating application components are connected via application collaborations.
 * @note An application component may be assigned to one or more application functions. An application component has one or more application interfaces, which expose its functionality. Application interfaces of other application components may serve an application component. The name of an application component should preferably be a noun.
 * @note The application component element is used to model entire applications (i.e., deployed and operational IT systems, as defined by the TOGAF framework) and individual parts of such applications, at all relevant levels of detail.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758029 ApplicationComponent ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationComponent extends InternalActiveStructureElement with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationComponent] = ApplicationElements.applicationComponent
}

/**
 * An aggregate of two or more application components that work together to perform collective application behavior.
 * ==Overview==
 * An application collaboration represents an aggregate of two or more application components that work together to perform collective application behavior.
 * @note An application collaboration specifies which components cooperate to perform some task. The collaborative behavior, including, for example, the communication pattern of these components, is modeled by an application interaction. An application collaboration typically models a logical or temporary collaboration of application components, and does not exist as a separate entity in the enterprise.
 * @note Application collaboration is a specialization of component, and aggregates two or more (cooperating) application components. An application collaboration is an active structure element that may be assigned to one or more application interactions, business interactions, or other application or business internal behavior elements, which model the associated behavior. An application interface may serve an application collaboration, and an application collaboration may be composed of application interfaces. The name of an application collaboration should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758030 ApplicationCollaboration ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationCollaboration extends Collaboration with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationCollaboration] = ApplicationElements.applicationCollaboration
}

/**
 * A point of access where application services are made available to a user, another application component, or a node.
 * ==Overview==
 * An application interface represents a point of access where application services are made available to a user, another application component, or a node.
 * @note An application interface specifies how the functionality of a component can be accessed by other elements. An application interface exposes application services to the environment. The same application service may be exposed through different interfaces, and the same interface may expose multiple services.
 * @note In a sense, an application interface specifies a kind of contract that a component realizing this interface must fulfill. This may include parameters, protocols used, pre- and post-conditions, and data formats.
 * @note An application interface may be part of an application component through composition (not shown in the standard notation), which means that these interfaces are provided by that component, and can serve other application components. An application interface can be assigned to application services, which means that the interface exposes these services to the environment. The name of an application interface should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758031 ApplicationInterface ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationInterface extends ExternalActiveStructureElement with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationInterface] = ApplicationElements.applicationInterface
}

/**
 * Automated behavior that can be performed by an application component.
 * ==Overview==
 * An application function represents automated behavior that can be performed by an application component.
 * @note An application function describes the internal behavior of an application component. If this behavior is exposed externally, this is done through one or more services. An application function abstracts from the way it is implemented. Only the necessary behavior is specified.
 * @note An application function may realize one or more application services. Application services of other application functions and technology services may serve an application function. An application function may access data objects. An application component may be assigned to an application function (which means that the application component performs the application function). The name of an application function should preferably be a verb ending with “ing”; e.g., “accounting”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758034 ApplicationFunction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationFunction extends Function with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationFunction] = ApplicationElements.applicationFunction
}

/**
 * A unit of collective application behavior performed by (a collaboration of) two or more application components.
 * ==Overview==
 * An application interaction represents a unit of collective application behavior performed by (a collaboration of) two or more application components.
 * @note An application interaction describes the collective behavior that is performed by the components that participate in an application collaboration. This may, for example, include the communication pattern between these components. An application interaction can also specify the externally visible behavior needed to realize an application service. The details of the interaction between the application components involved in an application interaction can be expressed during the detailed application design using, for example, a UML interaction diagram.
 * @note An application collaboration may be assigned to an application interaction. An application interaction may realize an application service. Application services and technology services may serve an application interaction. An application interaction may access data objects. The name of an application interaction should clearly identify a series of application behaviors; e.g., “Client profile creation” or “Update customer records”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758035 ApplicationInteraction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationInteraction extends Interaction with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationInteraction] = ApplicationElements.applicationInteraction
}

/**
 * A sequence of application behaviors that achieves a specific outcome.
 * ==Overview==
 * An application process represents a sequence of application behaviors that achieves a specific outcome.
 * @note An application process describes the internal behavior performed by an application component that is required to realize a set of services. For a (human or automated) consumer the services are relevant and the required behavior is merely a black box, hence the designation “internal”.
 * @note An application process may realize application services. Other application services may serve (be used by) an application process. An application process may access data objects. An application component may be assigned to an application process (which means that this component performs the process). The name of an application process should clearly identify a series of application behaviors; e.g., “Claims adjudication process”, or “General ledger update job”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758036 ApplicationProcess ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationProcess extends Process with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationProcess] = ApplicationElements.applicationProcess
}

/**
 * An application behavior element that denotes a state change.
 * ==Overview==
 * An application event is an application behavior element that denotes a state change.
 * @note Application functions and other application behavior may be triggered or interrupted by an application event. Also, application behavior may raise events that trigger other application behavior. Unlike processes, functions, and interactions, an event is instantaneous; it does not have duration. Events may originate from the environment of the organization (e.g., from an external application), but also internal events may occur generated by, for example, other applications within the organization.
 * @note An application event may have a time attribute that denotes the moment or moments at which the event happens. For example, this can be used to model time schedules; e.g., an event that triggers a daily batch process.
 * @note An application event may trigger or be triggered (raised) by an application function, process, or interaction. An application event may access a data object and may be composed of other application events. The name of an application event should preferably be a verb in the perfect tense; e.g., “claim received”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758037 ApplicationEvent ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationEvent extends Event with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationEvent] = ApplicationElements.applicationEvent
}

/**
 * An explicitly defined exposed application behavior.
 * ==Overview==
 * An application service represents an explicitly defined exposed application behavior.
 * @note An application service exposes the functionality of components to their environment. This functionality is accessed through one or more application interfaces. An application service is realized by one or more application functions that are performed by the component. It may require, use, and produce data objects.
 * @note An application service should be meaningful from the point of view of the environment; it should provide a unit of behavior that is, in itself, useful to its users. It has a purpose, which states this utility to the environment. This means, for example, that if this environment includes business processes, application services should have business relevance.
 * @note A purpose may be associated with an application service. An application service may serve business processes, business functions, business interactions, or application functions. An application function may realize an application service. An application interface may be assigned to an application service. An application service may access data objects. The name of an application service should preferably be a verb ending with “ing”; e.g., “transaction processing”. Also, a name explicitly containing the word “service” may be used.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758038 ApplicationService ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class ApplicationService extends ExternalBehaviorElement with ApplicationElement {
  @inline override def meta: ElementMeta[ApplicationService] = ApplicationElements.applicationService
}

/**
 * Data structured for automated processing.
 * ==Overview==
 * A data object represents data structured for automated processing.
 * @note A data object should be a self-contained piece of information with a clear meaning to the business, not just to the application level. Typical examples of data objects are a customer record, a client database, or an insurance claim.
 * @note The ArchiMate language in general focuses on the modeling of types, not instances, since this is the most relevant at the Enterprise Architecture level of description. Hence a data object typically models an object type (cf. a UML class) of which multiple instances may exist in operational applications. An important exception is when a data object is used to model a data collection such as a database, of which only one instance exists.
 * @note An application function or process can operate on data objects. A data object may be communicated via interactions and used or produced by application services. A data object can be accessed by an application function, application interaction, or application service. A data object may realize a business object, and may be realized by an artifact. A data object may have association, specialization, aggregation, or composition relationships with other data objects. The name of a data object should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap09.html#_Toc451758041 DataObject ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class DataObject extends PassiveStructureElement with ApplicationElement {
  @inline override def meta: ElementMeta[DataObject] = ApplicationElements.dataObject
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object ApplicationElements {

  case object applicationComponent extends ElementMeta[ApplicationComponent] {
    override def newInstance(): ApplicationComponent = new ApplicationComponent
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "aco"
    override def name: String = "applicationComponent"
  }
  case object applicationCollaboration extends ElementMeta[ApplicationCollaboration] {
    override def newInstance(): ApplicationCollaboration = new ApplicationCollaboration
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "acl"
    override def name: String = "applicationCollaboration"
  }
  case object applicationInterface extends ElementMeta[ApplicationInterface] {
    override def newInstance(): ApplicationInterface = new ApplicationInterface
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "aif"
    override def name: String = "applicationInterface"
  }
  case object applicationFunction extends ElementMeta[ApplicationFunction] {
    override def newInstance(): ApplicationFunction = new ApplicationFunction
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "afn"
    override def name: String = "applicationFunction"
  }
  case object applicationInteraction extends ElementMeta[ApplicationInteraction] {
    override def newInstance(): ApplicationInteraction = new ApplicationInteraction
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "aia"
    override def name: String = "applicationInteraction"
  }
  case object applicationProcess extends ElementMeta[ApplicationProcess] {
    override def newInstance(): ApplicationProcess = new ApplicationProcess
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "apr"
    override def name: String = "applicationProcess"
  }
  case object applicationEvent extends ElementMeta[ApplicationEvent] {
    override def newInstance(): ApplicationEvent = new ApplicationEvent
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "aev"
    override def name: String = "applicationEvent"
  }
  case object applicationService extends ElementMeta[ApplicationService] {
    override def newInstance(): ApplicationService = new ApplicationService
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "asv"
    override def name: String = "applicationService"
  }
  case object dataObject extends ElementMeta[DataObject] {
    override def newInstance(): DataObject = new DataObject
    override def layerObject: LayerObject = ApplicationLayer
    override def key: String = "ado"
    override def name: String = "dataObject"
  }

  val applicationElements: Seq[ElementMeta[Element]] = Seq(applicationComponent, applicationCollaboration, applicationInterface, applicationFunction, applicationInteraction, applicationProcess, applicationEvent, applicationService, dataObject)

}
