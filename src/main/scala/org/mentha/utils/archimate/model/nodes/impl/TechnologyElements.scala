package org.mentha.utils.archimate.model.nodes.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
sealed trait TechnologyElement extends TechnologyLayer {}

/**
 * A computational or physical resource that hosts, manipulates, or interacts with other computational or physical resources.
 * ==Overview==
 * A node represents a computational or physical resource that hosts, manipulates, or interacts with other computational or physical resources.
 * @note Nodes are active structure elements that perform technology behavior and execute, store, and process technology objects such as artifacts (or materials, as outlined in Chapter 11). For instance, nodes are used to model application platforms, defined by the TOGAF framework as: “a collection of technology components of hardware and software that provide the services used to support applications”.
 * @note Nodes can be interconnected by paths. A node may be assigned to an artifact to model that the artifact is deployed on the node.
 * @note The name of a node should preferably be a noun. A node may consist of sub-nodes.
 * @note Artifacts deployed on a node may either be drawn inside the node or connected to it with an assignment relationship.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758047 Node ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Node extends InternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[Node] = TechnologyElements.node
}

/**
 * A physical IT resource upon which system software and artifacts may be stored or deployed for execution.
 * ==Overview==
 * A device is a physical IT resource upon which system software and artifacts may be stored or deployed for execution.
 * @note A device is a specialization of a node that represents a physical IT resource with processing capability. It is typically used to model hardware systems such as mainframes, PCs, or routers. Usually, they are part of a node together with system software. Devices may be composite; i.e., consist of sub-devices.
 * @note Devices can be interconnected by networks. Devices can be assigned to artifacts and to system software, to model that artifacts and system software are deployed on that device. A node can contain one or more devices.
 * @note The name of a device should preferably be a noun referring to the type of hardware; e.g., “IBM System z mainframe”.
 * @note Different icons may be used to distinguish between different types of devices; e.g., mainframes and PCs.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758048 Device ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Device extends InternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[Device] = TechnologyElements.device
}

/**
 * Software that provides or contributes to an environment for storing, executing, and using software or data deployed within it.
 * ==Overview==
 * System software represents software that provides or contributes to an environment for storing, executing, and using software or data deployed within it.
 * @note System software is a specialization of a node that is used to model the software environment in which artifacts run. This can be, for example, an operating system, a JEE application server, a database system, or a workflow engine. Also, system software can be used to represent, for example, communication middleware. Usually, system software is combined with a device representing the hardware environment to form a general node.
 * @note A device or system software can be assigned to other system software; e.g., to model different layers of software running on top of each other. System software can be assigned to artifacts, to model that these artifacts are deployed on that software. System software can realize other system software. A node can be composed of system software.
 * @note The name of system software should preferably be a noun referring to the type of execution environment; e.g., “JEE server”. System software may be composed of other system software; e.g., an operating system containing a database.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758049 SystemSoftware ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class SystemSoftware extends InternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[SystemSoftware] = TechnologyElements.systemSoftware
}

/**
 * An aggregate of two or more nodes that work together to perform collective technology behavior.
 * ==Overview==
 * A technology collaboration represents an aggregate of two or more nodes that work together to perform collective technology behavior.
 * @note A technology collaboration specifies which nodes cooperate to perform some task. The collaborative behavior, including, for example, the communication pattern of these nodes, is modeled by a technology interaction. A technology collaboration typically models a logical or temporary collaboration of nodes, and does not exist as a separate entity in the enterprise.
 * @note Technology collaboration is a specialization of node, and aggregates two or more (cooperating) nodes. A technology collaboration is an active structure element that may be assigned to one or more technology interactions or other technology internal behavior elements, which model the associated behavior. A technology interface may serve a technology collaboration, and a technology collaboration may be composed of technology interfaces. The name of a technology collaboration should preferably be a noun.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758050 TechnologyCollaboration ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyCollaboration extends Collaboration with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyCollaboration] = TechnologyElements.technologyCollaboration
}

/**
 * A point of access where technology services offered by a node can be accessed.
 * ==Overview==
 * A technology interface represents a point of access where technology services offered by a node can be accessed.
 * @note A technology interface specifies how the technology services of a node can be accessed by other nodes. A technology interface exposes a technology service to the environment. The same service may be exposed through different interfaces.
 * @note In a sense, a technology interface specifies a kind of contract that a component realizing this interface must fulfill. This may include, for example, parameters, protocols used, pre- and post-conditions, and data formats.
 * @note A technology interface may be part of a node through composition (not shown in the standard notation), which means that these interfaces are provided by that node, and can serve other nodes. A technology interface can be assigned to a technology service, to expose that service to the environment.
 * @note The name of a technology interface should preferably be a noun.
 * @note In previous versions of this standard, this element was called ‘infrastructure interface’. This usage is still permitted but deprecated, and will be removed from a future version of the standard.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758051 TechnologyInterface ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyInterface extends ExternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyInterface] = TechnologyElements.technologyInterface
}

/**
 * A link between two or more nodes, through which these nodes can exchange data or material.
 * A path is used to model the logical communication (or distribution) relations between nodes.
 * ==Overview==
 * A path represents a link between two or more nodes, through which these nodes can exchange data or material.
 * @note A path is used to model the logical communication (or distribution) relations between nodes. It is realized by one or more networks, which represent the physical communication (or distribution) links. The properties (e.g., bandwidth, latency) of a path are usually aggregated from these underlying networks.
 * @note A path connects two or more nodes. A path is realized by one or more networks. A path can aggregate nodes.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758052 Path ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Path extends InternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[Path] = TechnologyElements.path
}

/**
 * A set of structures that connects computer systems or other electronic devices for transmission, routing, and reception of data or data-based communications such as voice and video.
 * A communication network represents the physical communication infrastructure.
 * ==Overview==
 * A communication network represents a set of structures and behaviors that connects computer systems or other electronic devices for transmission, routing, and reception of data or data-based communications such as voice and video.
 * @note A communication network represents the physical communication infrastructure. It represents ”a set of products, concepts, and services that enable the connection of computer systems or devices for the purpose of transmitting data and other forms (e.g., voice and video) between the systems”, as defined by the TOGAF framework.
 * @note A communication network connects two or more devices. The most basic communication network is a single link between two devices, but it may comprise multiple links and associated network equipment. A network has properties such as bandwidth and latency. A communication network realizes one or more paths. It embodies the physical realization of the logical path between nodes.
 * @note A communication network can consist of sub-networks. It can aggregate devices and system software, for example, to model the routers, switches, and firewalls that are part of the network infrastructure.
 * @note Formerly, this element was called ‘network’. This usage is still permitted but deprecated, and will be removed from a future version of the standard.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758053 CommunicationNetwork ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class CommunicationNetwork extends InternalActiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[CommunicationNetwork] = TechnologyElements.communicationNetwork
}

/**
 * A collection of technology behavior that can be performed by a node.
 * ==Overview==
 * A technology function represents a collection of technology behavior that can be performed by a node.
 * @note A technology function describes the internal behavior of a node; for the user of a node that performs a technology function, this function is invisible. If its behavior is exposed externally, this is done through one or more technology services. A technology function abstracts from the way it is implemented. Only the necessary behavior is specified.
 * @note A technology function may realize technology services. Technology services of other technology functions may serve technology functions. A technology function may access technology objects. A node may be assigned to a technology function (which means that the node performs the technology function). The name of a technology function should preferably be a verb ending with “ing”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758056 TechnologyFunction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyFunction extends Function with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyFunction] = TechnologyElements.technologyFunction
}

/**
 * A sequence of technology behaviors that achieves a specific outcome.
 * ==Overview==
 * A technology process represents a sequence of technology behaviors that achieves a specific outcome.
 * @note A technology process describes internal behavior of a node; for the user of that node, this process is invisible. If its behavior is exposed externally, this is done through one or more technology services. A technology process abstracts from the way it is implemented. Only the necessary behavior is specified. It can use technology objects as input and use or transform these to produce other technology objects as output.
 * @note A technology process may realize technology services. Other technology services may serve (be used by) a technology process. A technology process may access technology objects. A node may be assigned to a technology process, which means that this node performs the process. The name of a technology process should clearly identify a series of technology behaviors; e.g., “System boot sequence” or “Replicate database”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758057 TechnologyProcess ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyProcess extends Process with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyProcess] = TechnologyElements.technologyProcess
}

/**
 * A unit of collective technology behavior performed by (a collaboration of) two or more nodes.
 * ==Overview==
 * A technology interaction represents a unit of collective technology behavior performed by (a collaboration of) two or more nodes.
 * @note A technology interaction describes the collective behavior that is performed by the nodes that participate in a technology collaboration. This may, for example, include the communication pattern between these components. A technology interaction can also specify the externally visible behavior needed to realize a technology service. The details of the interaction between the nodes involved in a technology interaction can be expressed during the detailed design using, for example, a UML interaction diagram.
 * @note A technology collaboration may be assigned to a technology interaction. A technology interaction may realize a technology service. Technology services may serve a technology interaction. A technology interaction may access artifacts. The name of a technology interaction should clearly identify a series of technology behaviors; e.g., “Client profile creation” or “Update customer records”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758058 TechnologyInteraction ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyInteraction extends Interaction with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyInteraction] = TechnologyElements.technologyInteraction
}

/**
 * A technology behavior element that denotes a state change.
 * ==Overview==
 * A technology event is a technology behavior element that denotes a state change.
 * @note Technology functions and other technology behavior may be triggered or interrupted by a technology event. Also, technology functions may raise events that trigger other infrastructure behavior. Unlike processes, functions, and interactions, an event is instantaneous: it does not have duration. Events may originate from the environment of the organization, but also internal events may occur generated by, for example, other devices within the organization.
 * @note A technology event may have a time attribute that denotes the moment or moments at which the event happens. For example, this can be used to model time schedules; e.g., to model an event that triggers a recurring infrastructure function such as making a daily backup.
 * @note A technology event may trigger or be triggered (raised) by a technology function, process, or interaction. A technology event may access a data object and may be composed of other technology events. The name of a technology event should preferably be a verb in the perfect tense; e.g., “message received”.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758059 TechnologyEvent ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyEvent extends Event with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyEvent] = TechnologyElements.technologyEvent
}

/**
 * An explicitly defined exposed technology behavior.
 * ==Overview==
 * A technology service represents an explicitly defined exposed technology behavior.
 * @note A technology service exposes the functionality of a node to its environment. This functionality is accessed through one or more technology interfaces. It may require, use, and produce artifacts.
 * @note A technology service should be meaningful from the point of view of the environment; it should provide a unit of behavior that is, in itself, useful to its users, such as application components and nodes.
 * @note Typical technology services may, for example, include messaging, storage, naming, and directory services. It may access artifacts; e.g., a file containing a message.
 * @note A technology service may serve application components or nodes. A technology service is realized by a technology function or process. A technology service is exposed by a node by assigning technology interfaces to it. A technology service may access artifacts. A technology service may consist of sub-services.
 * @note The name of a technology service should preferably be a verb ending with “ing”; e.g., “messaging”. Also, a name explicitly containing the word “service” may be used.
 * @note In previous versions of this standard, this element was called ‘infrastructure service’. This usage is still permitted but deprecated, and will be removed from a future version of the standard.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758060 TechnologyService ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TechnologyService extends ExternalBehaviorElement with TechnologyElement {
  @inline override def meta: ElementMeta[TechnologyService] = TechnologyElements.technologyService
}

/**
 * A piece of data that is used or produced in a software development process, or by deployment and operation of a system.
 * ==Overview==
 * An artifact represents a piece of data that is used or produced in a software development process, or by deployment and operation of an IT system.
 * @note An artifact represents a tangible element in the IT world. Artifact is a specialization of technology object. It is typically used to model (software) products such as source files, executables, scripts, database tables, messages, documents, specifications, and model files. An instance (copy) of an artifact can be deployed on a node. An artifact could be used to represent a physical data component that realizes a data object.
 * @note An application component or system software may be realized by one or more artifacts. A data object may be realized by one or more artifacts. A node may be assigned to an artifact to model that the artifact is deployed on the node. Thus, the two typical ways to use the artifact element are as an execution component or as a data file. In fact, these could be defined as specializations of the artifact element.
 * @note The name of an artifact should preferably be the name of the file it represents; e.g., “order.jar”. An artifact may consist of sub-artifacts.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap10.html#_Toc451758064 Artifact ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class Artifact extends PassiveStructureElement with TechnologyElement {
  @inline override def meta: ElementMeta[Artifact] = TechnologyElements.artifact
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object TechnologyElements {

  case object node extends ElementMeta[Node] {
    override final def key: String = "tnd"
    override final def name: String = "node"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): Node = new Node
  }
  case object device extends ElementMeta[Device] {
    override final def key: String = "tdv"
    override final def name: String = "device"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): Device = new Device
  }
  case object systemSoftware extends ElementMeta[SystemSoftware] {
    override final def key: String = "tss"
    override final def name: String = "systemSoftware"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): SystemSoftware = new SystemSoftware
  }
  case object technologyCollaboration extends ElementMeta[TechnologyCollaboration] {
    override final def key: String = "tcl"
    override final def name: String = "technologyCollaboration"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyCollaboration = new TechnologyCollaboration
  }
  case object technologyInterface extends ElementMeta[TechnologyInterface] {
    override final def key: String = "tif"
    override final def name: String = "technologyInterface"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyInterface = new TechnologyInterface
  }
  case object path extends ElementMeta[Path] {
    override final def key: String = "tph"
    override final def name: String = "path"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): Path = new Path
  }
  case object communicationNetwork extends ElementMeta[CommunicationNetwork] {
    override final def key: String = "tcn"
    override final def name: String = "communicationNetwork"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): CommunicationNetwork = new CommunicationNetwork
  }
  case object technologyFunction extends ElementMeta[TechnologyFunction] {
    override final def key: String = "tfn"
    override final def name: String = "technologyFunction"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyFunction = new TechnologyFunction
  }
  case object technologyProcess extends ElementMeta[TechnologyProcess] {
    override final def key: String = "tpr"
    override final def name: String = "technologyProcess"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyProcess = new TechnologyProcess
  }
  case object technologyInteraction extends ElementMeta[TechnologyInteraction] {
    override final def key: String = "tia"
    override final def name: String = "technologyInteraction"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyInteraction = new TechnologyInteraction
  }
  case object technologyEvent extends ElementMeta[TechnologyEvent] {
    override final def key: String = "tev"
    override final def name: String = "technologyEvent"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyEvent = new TechnologyEvent
  }
  case object technologyService extends ElementMeta[TechnologyService] {
    override final def key: String = "tsv"
    override final def name: String = "technologyService"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): TechnologyService = new TechnologyService
  }
  case object artifact extends ElementMeta[Artifact] {
    override final def key: String = "taf"
    override final def name: String = "artifact"
    override final def layerObject: LayerObject = TechnologyLayer
    override final def newInstance(): Artifact = new Artifact
  }

  val technologyElements: Seq[ElementMeta[Element]] = Seq(node, device, systemSoftware, technologyCollaboration, technologyInterface, path, communicationNetwork, technologyFunction, technologyProcess, technologyInteraction, technologyEvent, technologyService, artifact)

}
