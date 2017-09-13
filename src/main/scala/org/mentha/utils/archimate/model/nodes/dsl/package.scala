package org.mentha.utils.archimate.model.nodes

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes.impl._

// @javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
package object dsl {

  /** @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757969 Derivation Rules ArchiMate® 3.0 Specification ]] */
  class derived extends scala.annotation.Annotation { }

  /** Motivation: The role of an individual, team, or organization (or classes thereof) that represents their interests in the outcome of the architecture. */
  def stakeholder(implicit model: Model): Stakeholder = model.add(new Stakeholder)

  /** Motivation: An external or internal condition that motivates an organization to define its goals and implement the changes necessary to achieve them. */
  def driver(implicit model: Model): Driver = model.add(new Driver)

  /** Motivation: The result of an analysis of the state of affairs of the enterprise with respect to some driver. */
  def assessment(implicit model: Model): Assessment = model.add(new Assessment)

  /** Motivation: A high-level statement of intent, direction, or desired end state for an organization and its stakeholders. */
  def goal(implicit model: Model): Goal = model.add(new Goal)

  /** Motivation: An end result that has been achieved. */
  def outcome(implicit model: Model): Outcome = model.add(new Outcome)

  /** Motivation: A qualitative statement of intent that should be met by the architecture. */
  def principle(implicit model: Model): Principle = model.add(new Principle)

  /** Motivation: A statement of need that must be met by the architecture. */
  def requirement(implicit model: Model): Requirement = model.add(new Requirement)

  /** Motivation: A factor that prevents or obstructs the realization of goals. */
  def constraint(implicit model: Model): Constraint = model.add(new Constraint)

  /** Motivation: The knowledge or expertise present in, or the interpretation given to, a core element in a particular context. */
  def meaning(implicit model: Model): Meaning = model.add(new Meaning)

  /** Motivation: The relative worth, utility, or importance of a core element or an outcome. */
  def value(implicit model: Model): Value = model.add(new Value)

  /** Strategy: An asset owned or controlled by an individual or organization. */
  def resource(implicit model: Model): Resource = model.add(new Resource)

  /** Strategy: An ability that an active structure element, such as an organization, person, or system, possesses. */
  def capability(implicit model: Model): Capability = model.add(new Capability)

  /** Strategy: An approach or plan for configuring some capabilities and resources of the enterprise, undertaken to achieve a goal. */
  def courseOfAction(implicit model: Model): CourseOfAction = model.add(new CourseOfAction)

  /** Business: A business entity that is capable of performing behavior. */
  def businessActor(implicit model: Model): BusinessActor = model.add(new BusinessActor)

  /** Business: The responsibility for performing specific behavior, to which an actor can be assigned, or the part an actor plays in a particular action or event. */
  def businessRole(implicit model: Model): BusinessRole = model.add(new BusinessRole)

  /** Business: An aggregate of two or more business internal active structure elements that work together to perform collective behavior. */
  def businessCollaboration(implicit model: Model): BusinessCollaboration = model.add(new BusinessCollaboration)

  /** Business: A point of access where a business service is made available to the environment. */
  def businessInterface(implicit model: Model): BusinessInterface = model.add(new BusinessInterface)

  /** Business: A sequence of business behaviors that achieves a specific outcome such as a defined set of products or business services. */
  def businessProcess(implicit model: Model): BusinessProcess = model.add(new BusinessProcess)

  /** Business: A collection of business behavior based on a chosen set of criteria (typically required business resources and/or competencies), closely aligned to an organization, but not necessarily explicitly governed by the organization. */
  def businessFunction(implicit model: Model): BusinessFunction = model.add(new BusinessFunction)

  /** Business: A unit of collective business behavior performed by (a collaboration of) two or more business roles. */
  def businessInteraction(implicit model: Model): BusinessInteraction = model.add(new BusinessInteraction)

  /** Business: A business behavior element that denotes an organizational state change. It may originate from and be resolved inside or outside the organization. */
  def businessEvent(implicit model: Model): BusinessEvent = model.add(new BusinessEvent)

  /** Business: An explicitly defined exposed business behavior. */
  def businessService(implicit model: Model): BusinessService = model.add(new BusinessService)

  /** Business: A concept used within a particular business domain. */
  def businessObject(implicit model: Model): BusinessObject = model.add(new BusinessObject)

  /** Business: A formal or informal specification of an agreement between a provider and a consumer that specifies the rights and obligations associated with a product and establishes functional and non-functional parameters for interaction. */
  def contract(implicit model: Model): Contract = model.add(new Contract)

  /** Business: A perceptible form of the information carried by a business object. */
  def representation(implicit model: Model): Representation = model.add(new Representation)

  /** Business: A coherent collection of services and/or passive structure elements, accompanied by a contract/set of agreements, which is offered as a whole to (internal or external) customers. */
  def product(implicit model: Model): Product = model.add(new Product)

  /** Application: An encapsulation of application functionality aligned to implementation structure, which is modular and replaceable. It encapsulates its behavior and data, exposes services, and makes them available through interfaces. */
  def applicationComponent(implicit model: Model): ApplicationComponent = model.add(new ApplicationComponent)

  /** Application: An aggregate of two or more application components that work together to perform collective application behavior. */
  def applicationCollaboration(implicit model: Model): ApplicationCollaboration = model.add(new ApplicationCollaboration)

  /** Application: A point of access where application services are made available to a user, another application component, or a node. */
  def applicationInterface(implicit model: Model): ApplicationInterface = model.add(new ApplicationInterface)

  /** Application: Automated behavior that can be performed by an application component. */
  def applicationFunction(implicit model: Model): ApplicationFunction = model.add(new ApplicationFunction)

  /** Application: A unit of collective application behavior performed by (a collaboration of) two or more application components. */
  def applicationInteraction(implicit model: Model): ApplicationInteraction = model.add(new ApplicationInteraction)

  /** Application: A sequence of application behaviors that achieves a specific outcome. */
  def applicationProcess(implicit model: Model): ApplicationProcess = model.add(new ApplicationProcess)

  /** Application: An application behavior element that denotes a state change. */
  def applicationEvent(implicit model: Model): ApplicationEvent = model.add(new ApplicationEvent)

  /** Application: An explicitly defined exposed application behavior. */
  def applicationService(implicit model: Model): ApplicationService = model.add(new ApplicationService)

  /** Application: Data structured for automated processing. */
  def dataObject(implicit model: Model): DataObject = model.add(new DataObject)

  /** Technology: A computational or physical resource that hosts, manipulates, or interacts with other computational or physical resources. */
  def node(implicit model: Model): Node = model.add(new Node)

  /** Technology: A physical IT resource upon which system software and artifacts may be stored or deployed for execution. */
  def device(implicit model: Model): Device = model.add(new Device)

  /** Technology: Software that provides or contributes to an environment for storing, executing, and using software or data deployed within it. */
  def systemSoftware(implicit model: Model): SystemSoftware = model.add(new SystemSoftware)

  /** Technology: An aggregate of two or more nodes that work together to perform collective technology behavior. */
  def technologyCollaboration(implicit model: Model): TechnologyCollaboration = model.add(new TechnologyCollaboration)

  /** Technology: A point of access where technology services offered by a node can be accessed. */
  def technologyInterface(implicit model: Model): TechnologyInterface = model.add(new TechnologyInterface)

  /** Technology: A link between two or more nodes, through which these nodes can exchange data or material., A path is used to model the logical communication (or distribution) relations between nodes. */
  def path(implicit model: Model): Path = model.add(new Path)

  /** Technology: A set of structures that connects computer systems or other electronic devices for transmission, routing, and reception of data or data-based communications such as voice and video., A communication network represents the physical communication infrastructure. */
  def communicationNetwork(implicit model: Model): CommunicationNetwork = model.add(new CommunicationNetwork)

  /** Technology: A collection of technology behavior that can be performed by a node. */
  def technologyFunction(implicit model: Model): TechnologyFunction = model.add(new TechnologyFunction)

  /** Technology: A sequence of technology behaviors that achieves a specific outcome. */
  def technologyProcess(implicit model: Model): TechnologyProcess = model.add(new TechnologyProcess)

  /** Technology: A unit of collective technology behavior performed by (a collaboration of) two or more nodes. */
  def technologyInteraction(implicit model: Model): TechnologyInteraction = model.add(new TechnologyInteraction)

  /** Technology: A technology behavior element that denotes a state change. */
  def technologyEvent(implicit model: Model): TechnologyEvent = model.add(new TechnologyEvent)

  /** Technology: An explicitly defined exposed technology behavior. */
  def technologyService(implicit model: Model): TechnologyService = model.add(new TechnologyService)

  /** Technology: A piece of data that is used or produced in a software development process, or by deployment and operation of a system. */
  def artifact(implicit model: Model): Artifact = model.add(new Artifact)

  /** Physical: One or more physical machines, tools, or instruments that can create, use, store, move, or transform materials. */
  def equipment(implicit model: Model): Equipment = model.add(new Equipment)

  /** Physical: A physical structure or environment. */
  def facility(implicit model: Model): Facility = model.add(new Facility)

  /** Physical: A physical network used to transport materials or energy. */
  def distributionNetwork(implicit model: Model): DistributionNetwork = model.add(new DistributionNetwork)

  /** Physical: Tangible physical matter or physical elements. */
  def material(implicit model: Model): Material = model.add(new Material)

  /** Implementation: A series of actions identified and designed to achieve specific results within specified time and resource constraints. */
  def workPackage(implicit model: Model): WorkPackage = model.add(new WorkPackage)

  /** Implementation: A precisely-defined outcome of a work package. */
  def deliverable(implicit model: Model): Deliverable = model.add(new Deliverable)

  /** Implementation: A behavior element that denotes a state change related to implementation or migration. */
  def implementationEvent(implicit model: Model): ImplementationEvent = model.add(new ImplementationEvent)

  /** Implementation: A relatively stable state of the architecture that exists during a limited period of time. */
  def plateau(implicit model: Model): Plateau = model.add(new Plateau)

  /** Implementation: A statement of difference between two plateaus. */
  def gap(implicit model: Model): Gap = model.add(new Gap)

  /** Composition: Aggregates or composes concepts that belong together based on some common characteristic. */
  def grouping(implicit model: Model): Grouping = model.add(new Grouping)

  /** Composition: A place or position where structure elements can be located or behavior can be performed */
  def location(implicit model: Model): Location = model.add(new Location)


  import org.mentha.utils.archimate.model.edges.RelationshipMeta
  def andJunction(relationship: RelationshipMeta[Relationship])(implicit model: Model): Junction = model.add(new AndJunction(relationship))
  def orJunction(relationship: RelationshipMeta[Relationship])(implicit model: Model): Junction = model.add(new OrJunction(relationship))

}
