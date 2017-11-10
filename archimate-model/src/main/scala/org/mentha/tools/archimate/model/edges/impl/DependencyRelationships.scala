package org.mentha.tools.archimate.model.edges.impl

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._


/**
 * Models that an element affects the implementation or achievement of some motivation element.
 * ==Overview==
 * The influence relationship models that an element affects the implementation or achievement of some motivation element.
 * The influence relationship is used to describe that some architectural element influences achievement or implementation of a motivation element, such as a goal or a principle. In general, a motivation element is realized to a certain degree.
 * @note Additional to this ‘vertical’ use of contribution, from core elements upwards to requirements and goals, the relationship can also be used to model ‘horizontal’ contributions between motivation elements. The influence relationship in that case describes that some motivation element may influence (the achievement or implementation of) another motivation element. In general, a motivation element is achieved to a certain degree. An influence by some other element may affect this degree, depending on the degree in which this other element is satisfied itself.
 * @note The realization relationship should be used to represent relationships that are critical to the existence or realization of the target, while the influence relationship should be used to represent relationships that are not critical to the target object’s existence or realization.
 * @note Moreover, an influence relationship can be used to model either: (1) The fact that an element positively contributes to the achievement or implementation of some motivation element, or (2) The fact that an element negatively influences – i.e., prevents or counteracts – such achievement.
 * @note Attributes can be used to indicate the sign and/or strength of the influence. The choice of possible attribute values is left to the modeler; e.g., {++, +, 0, -, --} or [0..10]. By default, the influence relationship models a contribution with unspecified sign and strength.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945997 InfluenceRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class InfluenceRelationship(source: Concept, target: Concept)(var influence: String = null)
  extends DependencyRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[InfluenceRelationship] = DependencyRelationships.influenceRelationship
  @inline def withInfluence(influence: String): this.type = {
    this.influence = influence
    this
  }
}

/**
 * Models the ability of behavior and active structure elements to observe or act upon passive structure elements.
 * ==Overview==
 * The access relationship models the ability of behavior and active structure elements to observe or act upon passive structure elements.
 * The usual interpretation of an access relationship is that the whole of the target element is accessed by the source element.
 * @note The access relationship indicates that a process, function, interaction, service, or event “does something” with a passive structure element; e.g., create a new object, read data from the object, write or modify the object data, or delete the object.
 * @note The relationship can also be used to indicate that the object is just associated with the behavior; e.g., it models the information that comes with an event, or the information that is made available as part of a service.
 * @note Alternatively, an access relationship can be expressed by nesting the passive structure element inside the behavior or active structure element that accesses it; for example, nesting a data object inside an application component.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945996 AccessRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class AccessRelationship(source: Concept, target: Concept)(var access: AccessType = ReadWriteAccess)
  extends DependencyRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[AccessRelationship] = DependencyRelationships.accessRelationship
  @inline def withAccess(access: AccessType): this.type = {
    this.access = access
    this
  }
}

/**
 * Models that an element provides its functionality to another element.
 * ==Overview==
 * The serving relationship models that an element provides its functionality to another element.
 * The usual interpretation of a serving relationship is that the whole of the source element serves (is used by) the target element.
 * @note The serving relationship describes how the services or interfaces offered by a behavior or active structure element serve entities in their environment. This relationship is applied for both the behavior aspect and the active structure aspect.
 * @note Compared to the earlier versions of this standard, the name of this relationship has been changed from ‘used by’ to ‘serving’, to better reflect its direction with an active verb: a service serves a user. The meaning of the relationship has not been altered. The ‘used by’ designation is still allowed but deprecated, and will be removed in a future version of the standard.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945995 ServingRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class ServingRelationship(source: Concept, target: Concept)
  extends DependencyRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[ServingRelationship] = DependencyRelationships.servingRelationship
}

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
object DependencyRelationships {

  case object influenceRelationship extends RelationshipMeta[InfluenceRelationship] {
    override final def key: Char = 'n'
    override final def name: String = "influenceRelationship"
    override final def newInstance(source: Concept, target: Concept): InfluenceRelationship = new InfluenceRelationship(source, target)()
  }
  case object accessRelationship extends RelationshipMeta[AccessRelationship] {
    override final def key: Char = 'a'
    override final def name: String = "accessRelationship"
    override final def newInstance(source: Concept, target: Concept): AccessRelationship = new AccessRelationship(source, target)()
  }
  case object servingRelationship extends RelationshipMeta[ServingRelationship] {
    override final def key: Char = 'v'
    override final def name: String = "servingRelationship"
    override final def newInstance(source: Concept, target: Concept): ServingRelationship = new ServingRelationship(source, target)
  }

  val dependencyRelationships: Seq[RelationshipMeta[Relationship]] = Seq(influenceRelationship, accessRelationship, servingRelationship)

}
