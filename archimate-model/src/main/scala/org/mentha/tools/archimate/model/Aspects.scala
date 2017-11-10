package org.mentha.tools.archimate.model

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap03.html
  * Classification of elements based on layer-independent characteristics related to the concerns of different stakeholders. Used for positioning elements in the ArchiMate metamodel.
  * It is important to understand that the classification of elements based on aspects and layers is only a global one.
  * It is impossible to define a strict boundary between the aspects and layers, because elements that link the different aspects and layers play a central role in a coherent architectural description. For example, running somewhat ahead of the later conceptual discussions, (business) functions and (business) roles serve as intermediary elements between “purely behavioral” elements and “purely structural” elements, and it may depend on the context whether a certain piece of software is considered to be part of the Application Layer or the Technology Layer.
  */
sealed trait Aspect extends ArchimateObject {
  def aspectObject: AspectObject
}

sealed trait CoreAspect extends Aspect {

}

/**
  * The Active Structure Aspect, which represents the structural elements (the business actors, application components, and devices that display actual behavior; i.e., the “subjects” of activity).
  */
trait ActiveStructureAspect extends CoreAspect {
  override def aspectObject: AspectObject = ActiveStructureAspect
}

/**
  *  The Behavior Aspect, which represents the behavior (processes, functions, events, and services) performed by the actors. Structural elements are assigned to behavioral elements, to show who or what displays the behavior.
  */
trait BehaviorAspect extends CoreAspect {
  override def aspectObject: AspectObject = BehaviorAspect
}

/**
  * The Passive Structure Aspect, which represents the objects on which behavior is performed. These are usually information objects in the Business Layer and data objects in the Application Layer, but they may also be used to represent physical objects.
  */
trait PassiveStructureAspect extends  CoreAspect {
  override def aspectObject: AspectObject = PassiveStructureAspect
}

/**
  * TODO
  */
trait MotivationAspect extends Aspect {
  override def aspectObject: AspectObject = MotivationAspect
}


trait AspectObject {
  self: Aspect =>

}

/**
  * The Active Structure Aspect, which represents the structural elements (the business actors, application components, and devices that display actual behavior; i.e., the “subjects” of activity).
  */
case object ActiveStructureAspect extends AspectObject with ActiveStructureAspect {

}

/**
  *  The Behavior Aspect, which represents the behavior (processes, functions, events, and services) performed by the actors. Structural elements are assigned to behavioral elements, to show who or what displays the behavior.
  */
case object BehaviorAspect extends AspectObject with BehaviorAspect {

}

/**
  * The Passive Structure Aspect, which represents the objects on which behavior is performed. These are usually information objects in the Business Layer and data objects in the Application Layer, but they may also be used to represent physical objects.
  */
case object PassiveStructureAspect extends AspectObject with PassiveStructureAspect {

}

/**
  * TODO
  */
case object MotivationAspect extends AspectObject with MotivationAspect {

}
