package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait BusinessElement extends BusinessLayer {}

/**
 * A business actor is a business entity that is capable of performing behavior.
 * @note A business actor is a business entity as opposed to a technical entity; i.e., it belongs to the Business Layer. Actors may, however, include entities outside the actual organization; e.g., customers and partners. A business actor can represent such business entities at different levels of detail, and may correspond to both an actor and an organizational unit in the TOGAF framework. Examples of business actors are humans, departments, and business units.
 * @note A business actor may be assigned to one or more business roles. It can then perform the behavior to which these business roles are assigned. A business actor can be aggregated in a location. The name of a business actor should preferably be a noun. Business actors may be specific individuals or organizations; e.g., “John Smith” or “ABC Corporation”, or they may be generic; e.g., “Customer” or “Supplier”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758005 BusinessActor ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessActor extends InternalActiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[BusinessActor] = BusinessElements.businessActor
}

/**
 * A business role is the responsibility for performing specific behavior, to which an actor can be assigned, or the part an actor plays in a particular action or event.
 * @note Business roles with certain responsibilities or skills are assigned to business processes or business functions. A business actor that is assigned to a business role is responsible that the corresponding behavior is carried out, either by performing it or by delegating and managing its performance. In addition to the relation of a business role with behavior, a business role is also useful in a (structural) organizational sense; for instance, in the division of labor within an organization.
 * @note A business role may be assigned to one or more business processes or business functions, while a business actor may be assigned to one or more business roles. A business interface or an application interface may serve a business role, while a business interface may be part of a business role (through a composition relationship, which is not shown explicitly in the interface notation). The name of a business role should preferably be a noun.
 * @note ArchiMate modelers may represent generic organizational entities that perform behavior as either business actors or business roles. For example, the business actor Supplier depicts an organizational entity, while the business role Supplier depicts a responsibility. Specific or generic business actors can be assigned to carry responsibilities depicted as business roles. For example, the specific business actor ABC Corporation or the generic business actor Business Partner can be assigned to the Supplier business role.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758006 BusinessRole ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessRole extends InternalActiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[BusinessRole] = BusinessElements.businessRole
}

/**
 * A business collaboration is an aggregate of two or more business internal active structure elements that work together to perform collective behavior.
 * @note A business process or function may be interpreted as the internal behavior of a single business role. In some cases, behavior is the collective effort of more than one business role; in fact, a collaboration of two or more business roles results in collective behavior which may be more than simply the sum of the behavior of the separate roles. Business collaborations represent this collective effort. Business interactions are used to describe the internal behavior that takes place within business collaboration. A collaboration is a (possibly temporary) collection of business roles or actors within an organization, which perform collaborative behavior (interactions). Unlike a department, which may also group roles, a business collaboration need not have an official (permanent) status within the organization; it is specifically aimed at a specific interaction or set of interactions between roles. It is especially useful in modeling B2B interactions between different organizations such as provider networks, and also for describing social networks.
 * @note A business collaboration may aggregate a number of business roles or actors, and may be assigned to one or more business interactions or other business internal behavior elements. A business interface or an application interface may serve a business collaboration, while a business collaboration may have business interfaces (through composition, and also through aggregation via derived relationships). The name of a business collaboration should preferably be a noun. It is also rather common to leave a business collaboration unnamed.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758007 BusinessCollaboration ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessCollaboration extends Collaboration with BusinessElement {
  @inline override def meta: ElementMeta[BusinessCollaboration] = BusinessElements.businessCollaboration
}

/**
 * A business interface is a point of access where a business service is made available to the environment.
 * @note A business interface exposes the functionality of a business service to other business roles or actors. It is often referred to as a channel (telephone, Internet, local office, etc.). The same business service may be exposed through different interfaces.
 * @note A business interface may be part of a business role or actor through a composition relationship, which is not shown in the standard notation, and a business interface may serve a business role. A business interface may be assigned to one or more business services, which means that these services are exposed by the interface. The name of a business interface should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758008 BusinessInterface ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessInterface extends ExternalActiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[BusinessInterface] = BusinessElements.businessInterface
}

/**
 * A business process represents a sequence of business behaviors that achieves a specific outcome such as a defined set of products or business services.
 * @note A business process describes the internal behavior performed by a business role that is required to produce a set of products and services. For a consumer, the products and services are relevant and the required behavior is merely a black box, hence the designation “internal”.
 * @note A complex business process may be an aggregation of other, finer-grained processes. To each of these, finer-grained roles may be assigned.
 * @note There is a potential many-to-many relationship between business processes and business functions. Informally speaking, processes describe some kind of “flow” of activities, whereas functions group activities according to required skills, knowledge, resources, etc.
 * @note A business process may be triggered by, or trigger, any other business behavior element (e.g., business event, business process, business function, or business interaction). A business process may access business objects. A business process may realize one or more business services and may use (internal) business services or application services. A business role may be assigned to a business process to perform this process manually or automated, respectively. The name of a business process should clearly indicate a predefined sequence of actions, and may include the word “process”. Examples are “adjudicate claim”, “employee on-boarding”, “approval process”, or “financial reporting”.
 * @note In an ArchiMate model, the existence of business processes is depicted. High-level business, end-to-end processes, macro flows, and workflows can all be expressed with the same business process element in the ArchiMate language. It does not, however, list the flow of activities in detail. This is typically done during business process modeling, where a business process can be expanded using a business process design language; e.g., BPMN.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758011 BusinessProcess ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessProcess extends Process with BusinessElement {
  @inline override def meta: ElementMeta[BusinessProcess] = BusinessElements.businessProcess
}

/**
 * A business function is a collection of business behavior based on a chosen set of criteria (typically required business resources and/or competences), closely aligned to an organization, but not necessarily explicitly governed by the organization.
 * @note Just like a business process, a business function also describes internal behavior performed by a business role. However, while a business process groups behavior based on a sequence or flow of activities that is needed to realize a product or service, a business function typically groups behavior based on required business resources, skills, competences, knowledge, etc.
 * @note There is a potential many-to-many relation between business processes and business functions. Complex processes in general involve activities that offer various functions. In this sense a business process forms a string of business functions. In general, a business function delivers added value from a business point of view. Organizational units or applications may coincide with business functions due to their specific grouping of business activities.
 * @note A business function may be triggered by, or trigger, any other business behavior element (business event, business process, business function, or business interaction). A business function may access business objects. A business function may realize one or more business services and may be served by business, application, or technology services. A business role may be assigned to a business function. The name of a business function should clearly indicate a well-defined behavior. Examples are customer management, claims administration, member services, recycling, or payment processing.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758012 BusinessFunction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessFunction extends Function with BusinessElement {
  @inline override def meta: ElementMeta[BusinessFunction] = BusinessElements.businessFunction
}

/**
 * A business interaction is a unit of collective business behavior performed by (a collaboration of) two or more business roles.
 * @note A business interaction is similar to a business process/function, but while a process/function may be performed by a single role, an interaction is performed by a collaboration of multiple roles. The roles in the collaboration share the responsibility for performing the interaction.
 * @note A business interaction may be triggered by, or trigger, any other business behavior element (business event, business process, business function, or business interaction). A business interaction may access business objects. A business interaction may realize one or more business services and may use (internal) business services or application services. A business collaboration or an application collaboration may be assigned to a business interaction. The name of a business interaction should preferably be a verb in the simple present tense.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758013 BusinessInteraction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessInteraction extends Interaction with BusinessElement {
  @inline override def meta: ElementMeta[BusinessInteraction] = BusinessElements.businessInteraction
}

/**
 * A business event is a business behavior element that denotes an organizational state change. It may originate from and be resolved inside or outside the organization.
 * @note Business processes and other business behavior may be triggered or interrupted by a business event. Also, business processes may raise events that trigger other business processes, functions, or interactions. Unlike business processes, functions, and interactions, a business event is instantaneous: it does not have duration. Events may originate from the environment of the organization (e.g., from a customer), but also internal events may occur generated by, for example, other processes within the organization.
 * @note A business event may have a time attribute that denotes the moment or moments at which the event happens. For example, this can be used to model time schedules; e.g., to model an event that triggers a recurring business process to execute every first Monday of the month.
 * @note A business event may trigger or be triggered (raised) by a business process, business function, or business interaction. A business event may access a business object and may be composed of other business events. The name of a business event should preferably be a verb in the perfect tense; e.g., claim received.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758014 BusinessEvent ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessEvent extends Event with BusinessElement {
  @inline override def meta: ElementMeta[BusinessEvent] = BusinessElements.businessEvent
}

/**
 * A business service represents an explicitly defined exposed business behavior.
 * @note A business service exposes the functionality of business roles or collaborations to their environment. This functionality is accessed through one or more business interfaces. A business service is realized by one or more business processes, business functions, or business interactions that are performed by the business roles or business collaborations, respectively. It may access business objects.
 * @note A business service should provide a unit of behavior that is meaningful from the point of view of the environment. It has a purpose, which states this utility. The environment includes the (behavior of) users from outside as well as inside the organization. Business services can be external, customer-facing services (e.g., a travel insurance service) or internal support services (e.g., a resource management service).
 * @note A business service is associated with a value. A business service may serve a business process, business function, or business interaction. A business process, business function, or business interaction may realize a business service. A business interface may be assigned to a business service. A business service may access business objects. The name of a business service should preferably be a verb ending with “ing”; e.g., transaction processing. Also, a name explicitly containing the word “service” may be used.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758015 BusinessService ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessService extends ExternalBehaviorElement with BusinessElement {
  @inline override def meta: ElementMeta[BusinessService] = BusinessElements.businessService
}

/**
 * A business object represents a concept used within a particular business domain.
 * @note The ArchiMate language in general focuses on the modeling of types, not instances, since this is the most relevant at the Enterprise Architecture level of description. Hence a business object typically models an object type (cf. a UML class) of which multiple instances may exist in operations. Only occasionally, business objects represent actual instances of information produced and consumed by behavior elements such as business processes. This is in particular the case for singleton types; i.e., types that have only one instance.
 * @note A wide variety of types of business objects can be defined. Business objects are passive in the sense that they do not trigger or perform processes. A business object could be used to represent information assets that are relevant from a business point of view and can be realized by data objects.
 * @note Business objects may be accessed (e.g., in the case of information objects, they may be created, read, written) by a business process, function, business interaction, business event, or business service. A business object may have association, specialization, aggregation, or composition relationships with other business objects. A business object may be realized by a representation or by a data object (or both). The name of a business object should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758018 BusinessObject ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class BusinessObject extends PassiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[BusinessObject] = BusinessElements.businessObject
}

/**
 * A contract represents a formal or informal specification of an agreement between a provider and a consumer that specifies the rights and obligations associated with a product and establishes functional and non-functional parameters for interaction.
 * @note The contract element may be used to model a contract in the legal sense, but also a more informal agreement associated with a product. It may also be or include a Service-Level Agreement (SLA), describing an agreement about the functionality and quality of the services that are part of a product. A contract is a specialization of a business object.
 * @note The relationships that apply to a business object also apply to a contract. In addition, a contract may have an aggregation relationship with a product. The name of a contract is preferably a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758019 Contract ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Contract extends PassiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[Contract] = BusinessElements.contract
}

/**
 * A representation represents a perceptible form of the information carried by a business object.
 * @note Representations (for example, messages or documents) are the perceptible carriers of information that are related to business objects. If relevant, representations can be classified in various ways; for example, in terms of medium (electronic, paper, audio, etc.) or format (HTML, ASCII, PDF, RTF, etc.). A single business object can have a number of different representations. Also, a single representation can realize one or more specific business objects.
 * @note A meaning can be associated with a representation that carries this meaning. The name of a representation is preferably a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758020 Representation ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Representation extends PassiveStructureElement with BusinessElement {
  @inline override def meta: ElementMeta[Representation] = BusinessElements.representation
}

/**
 * A product represents a coherent collection of services and/or passive structure elements, accompanied by a contract/set of agreements, which is offered as a whole to (internal or external) customers.
 * @note This definition covers both intangible, services-based, or information products that are common in information-intensive organizations, and tangible, physical products. A financial or information product consists of a collection of services, and a contract that specifies the characteristics, rights, and requirements associated with the product. “Buying” a product gives the customer the right to use the associated services.
 * @note Generally, the product element is used to specify a product type. The number of product types in an organization is typically relatively stable compared to, for example, the processes that realize or support the products. “Buying” is usually one of the services associated with a product, which results in a new instance of that product (belonging to a specific customer). Similarly, there may be services to modify or destroy a product.
 * @note A product may aggregate or compose business services, application services, and technology services, business objects, data objects, and technology objects, as well as a contract. Hence a product may aggregate or compose elements from other layers than the Business Layer.
 * @note A value may be associated with a product. The name of a product is usually the name which is used in the communication with customers, or possibly a more generic noun (e.g., “travel insurance”).
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap08.html#_Toc451758023 Product ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Product extends CompositeElement with BusinessElement {
  @inline override def meta: ElementMeta[Product] = BusinessElements.product
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object BusinessElements {

  case object businessActor extends ElementMeta[BusinessActor] {
    override def newInstance(): BusinessActor = new BusinessActor
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bac"
    override def name: String = "businessActor"
  }
  case object businessRole extends ElementMeta[BusinessRole] {
    override def newInstance(): BusinessRole = new BusinessRole
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bro"
    override def name: String = "businessRole"
  }
  case object businessCollaboration extends ElementMeta[BusinessCollaboration] {
    override def newInstance(): BusinessCollaboration = new BusinessCollaboration
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bcl"
    override def name: String = "businessCollaboration"
  }
  case object businessInterface extends ElementMeta[BusinessInterface] {
    override def newInstance(): BusinessInterface = new BusinessInterface
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bif"
    override def name: String = "businessInterface"
  }
  case object businessProcess extends ElementMeta[BusinessProcess] {
    override def newInstance(): BusinessProcess = new BusinessProcess
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bpr"
    override def name: String = "businessProcess"
  }
  case object businessFunction extends ElementMeta[BusinessFunction] {
    override def newInstance(): BusinessFunction = new BusinessFunction
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bfn"
    override def name: String = "businessFunction"
  }
  case object businessInteraction extends ElementMeta[BusinessInteraction] {
    override def newInstance(): BusinessInteraction = new BusinessInteraction
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bia"
    override def name: String = "businessInteraction"
  }
  case object businessEvent extends ElementMeta[BusinessEvent] {
    override def newInstance(): BusinessEvent = new BusinessEvent
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bev"
    override def name: String = "businessEvent"
  }
  case object businessService extends ElementMeta[BusinessService] {
    override def newInstance(): BusinessService = new BusinessService
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bsv"
    override def name: String = "businessService"
  }
  case object businessObject extends ElementMeta[BusinessObject] {
    override def newInstance(): BusinessObject = new BusinessObject
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bob"
    override def name: String = "businessObject"
  }
  case object contract extends ElementMeta[Contract] {
    override def newInstance(): Contract = new Contract
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bco"
    override def name: String = "contract"
  }
  case object representation extends ElementMeta[Representation] {
    override def newInstance(): Representation = new Representation
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bre"
    override def name: String = "representation"
  }
  case object product extends ElementMeta[Product] {
    override def newInstance(): Product = new Product
    override def layerObject: LayerObject = BusinessLayer
    override def key: String = "bpr"
    override def name: String = "product"
  }

  val businessElements: Seq[ElementMeta[_]] = Seq(businessActor, businessRole, businessCollaboration, businessInterface, businessProcess, businessFunction, businessInteraction, businessEvent, businessService, businessObject, contract, representation, product)

}
