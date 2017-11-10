package org.mentha.tools.archimate.model.nodes

import org.mentha.tools.archimate.model._

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap04.html
  */
object GenericMetamodel {

}

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap04.html#_Toc451757940
  * The main hierarchy of behavior and structure elements of the ArchiMate language is presented in the metamodel fragment of Figure 4. It defines these elements in a generic, layer-independent way. Note that most of these elements (the white boxes) are abstract metamodel elements; i.e., these are not instantiated in models but only serve to structure the metamodel. The notation presented in this chapter is therefore the generic way in which the specializations of these elements (i.e., the elements of the different architecture layers) are depicted. The concrete elements (the gray boxes), which can be used to model the Enterprise Architecture at a strategic level, are described in more detail in Chapter 7.
  * This generic metamodel fragment consists of two main types of elements: structure (‘nouns’) and behavior elements (‘verbs’).
  */
object BehaviorAndStructureElements {

}

/**
  * Behavior Elements (verb)
  * Behavior elements represent the dynamic aspects of the enterprise. Similar to active structure elements, behavior elements can be subdivided into internal behavior elements and external behavior elements; i.e., the services that are exposed to the environment.
  */
abstract class BehaviorElement extends Element with BehaviorAspect {

}

/**
  * Structure elements are the strategic element resource, and structural elements, which can be subdivided into active structure elements and passive structure elements. Active structure elements can be further subdivided into external active structure elements (also called interfaces) and internal active structure elements.
  */
abstract class StructureElement extends Element {

}

sealed trait Internal {

}

sealed trait External {

}

/**
  * An external behavior element, called a *service*, represents an explicitly defined exposed behavior.
  * An explicitly defined exposed behavior.
  */
abstract class ExternalBehaviorElement extends BehaviorElement with External {

}

/**
  * An internal behavior element represents a unit of activity performed by one or more active structure elements.
  * A unit of activity performed by one or more active structure elements.
  */
abstract class InternalBehaviorElement extends BehaviorElement with Internal {

}

/**
  * A process represents a sequence of behaviors that achieves a specific outcome.
  * A sequence of behaviors that achieves a specific outcome.
  */
abstract class Process extends InternalBehaviorElement {

}

/**
  * A function represents a collection of behavior based on specific criteria, such as required resources, competences, or location.
  * A collection of behavior based on specific criteria, such as required resources, competences, or location.
  */
abstract class Function extends InternalBehaviorElement {

}

/**
  * An interaction is a unit of collective behavior performed by (a collaboration of) two or more active structure elements.
  * A unit of collective behavior performed by (a collaboration of) two or more structure elements.
  */
abstract class Interaction extends InternalBehaviorElement {

}

/**
  * A state change.
  * An event is a behavior element that denotes a state change.
  */
abstract class Event extends BehaviorElement {

}

/**
  * Active Structure Elements (subject)
  * Active structure elements are the subjects that can perform behavior. These can be subdivided into internal active structure elements; i.e., the business actors, application components, nodes, etc., that realize this behavior, and external active structure elements; i.e., the interfaces that expose this behavior to the environment.
  */
abstract class ActiveStructureElement extends StructureElement with ActiveStructureAspect {

}

/**
  * An external active structure element, called an *interface*, represents a point of access where one or more services are provided to the environment.
  * A point of access where one or more services are exposed available to the environment.
  */
abstract class ExternalActiveStructureElement extends ActiveStructureElement with External {

}

/**
  * An entity that is capable of performing behavior.
  * An internal active structure element represents an entity that is capable of performing behavior.
  */
abstract class InternalActiveStructureElement extends ActiveStructureElement with Internal {

}

/**
  * An aggregate of two or more active structure elements, working together to perform some collective behavior.
  * A collaboration is an aggregate of two or more active structure elements, working together to perform some collective behavior.
  */
abstract class Collaboration extends InternalActiveStructureElement {

}

/**
  * Passive Structure Elements (object)
  * Passive structure elements can be accessed by behavior elements.
  * An element on which behavior is performed.
  * A passive structure element is a structural element that cannot perform behavior. Active structure elements can perform behavior on passive structure elements.
  */
abstract class PassiveStructureElement extends StructureElement with PassiveStructureAspect {

}

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap04.html#_Toc451757948
  * Composite elements consist of other concepts, possibly from multiple aspects or layers of the language.
  * Grouping and location are generic composite elements.
  * Composite elements can themselves aggregate or compose other composite elements.
  */
abstract class CompositeElement extends Element {

}

/**
  * Generic element (neither BehaviorAndStructure nor Composite)
  */
abstract class GenericElement extends Element {

}
