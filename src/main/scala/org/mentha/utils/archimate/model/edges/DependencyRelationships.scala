package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html
  */
object DependencyRelationships {

  case object serving extends RelationshipMeta[ServingRelationship] {
    override def key: Char = 'v'
    override def name: String = "servingRelationship"
    override def newInstance(source: Concept, target: Concept): ServingRelationship = new ServingRelationship(source, target)
  }
  case object access extends RelationshipMeta[AccessRelationship] {
    override def key: Char = 'a'
    override def name: String = "accessRelationship"
    override def newInstance(source: Concept, target: Concept): AccessRelationship = new AccessRelationship(source, target)()
  }
  case object influence extends RelationshipMeta[InfluenceRelationship] {
    override def key: Char = 'n'
    override def name: String = "influenceRelationship"
    override def newInstance(source: Concept, target: Concept): InfluenceRelationship = new InfluenceRelationship(source, target)()
  }

  val dependencyRelations: Seq[RelationshipMeta[_]] = Seq(serving, access, influence)

}

/**
  * Dependency relationships describe how elements support or are used by other elements.
  * @param source
  * @param target
  */
abstract class DependencyRelationship(source: Concept, target: Concept)
  extends Relationship(source, target) {

}

/**
  * The serving relationship models that an element provides its functionality to another element.
  * The serving relationship represents a control dependency, denoted by a solid line.
  * The serving relationship describes how the services or interfaces offered by a behavior or active structure element serve entities in their environment. This relationship is applied for both the behavior aspect and the active structure aspect.
  * @param provider
  * @param consumer
  */
final class ServingRelationship(provider: Concept, consumer: Concept)
  extends DependencyRelationship(provider, consumer) {
  @inline override def meta: RelationshipMeta[ServingRelationship] = DependencyRelationships.serving
}

/**
  * The access relationship models the ability of behavior and active structure elements to observe or act upon passive structure elements.
  * The access relationship represents a data dependency, denoted by a dashed line.
  * The access relationship indicates that a process, function, interaction, service, or event “does something” with a passive structure element
  * @param behavior
  * @param subject
  */
final class AccessRelationship(behavior: Concept, subject: Concept)(var access: AccessType = ReadWriteAccess)
  extends DependencyRelationship(behavior, subject) {
  @inline override def meta: RelationshipMeta[AccessRelationship] = DependencyRelationships.access

  def withAccess(access: AccessType): this.type = {
    this.access = access
    this
  }

}

/**
  * The influence relationship models that an element affects the implementation or achievement of some motivation element.
  * The influence relationship is the weakest type of dependency, used to model how motivation elements are influenced by other elements.
  * The influence relationship is used to describe that some architectural element influences achievement or implementation of a motivation element, such as a goal or a principle.
  * @param source
  * @param target
  */
final class InfluenceRelationship(source: Concept, target: Concept)(var influence: String = null)
  extends DependencyRelationship(source, target) {
  @inline override def meta: RelationshipMeta[InfluenceRelationship] = DependencyRelationships.influence

  def withInfluence(influence: String): this.type = {
    this.influence = influence
    this
  }

}
