package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html
  */
object StructuralRelationships {

  case object composition extends RelationshipMeta[CompositionRelationship] {
    override def newInstance(source: Concept, target: Concept): CompositionRelationship = new CompositionRelationship(source, target)
  }
  case object aggregation extends RelationshipMeta[AggregationRelationship] {
    override def newInstance(source: Concept, target: Concept): AggregationRelationship = new AggregationRelationship(source, target)
  }
  case object assignment extends RelationshipMeta[AssignmentRelationship] {
    override def newInstance(source: Concept, target: Concept): AssignmentRelationship = new AssignmentRelationship(source, target)
  }
  case object realization extends RelationshipMeta[RealizationRelationship] {
    override def newInstance(source: Concept, target: Concept): RealizationRelationship = new RealizationRelationship(source, target)
  }

  val structuralRelations: Seq[RelationshipMeta[_]] = Seq(composition, aggregation, assignment, realization)

}

/**
  * Structural relationships represent the ‘static’ coherence within an architecture. The composing concept (the ‘from’ side of the relationship) is always an element; the composed concept (the ‘to’ side of the relationship) may in some cases also be another relationship.
  * Structural relationships model the static construction or composition of concepts of the same or different types.
  * @param source
  * @param target
  */
abstract class StructuralRelationship(source: Concept, target: Concept)
  extends Relationship(source, target) {

}

/**
  * The composition relationship indicates that an element *consists of* one or more other elements (elements are *part of* the container).
  * Composed elements exist only within its container, the relation describes how the container splits into its parts.
  * The usual interpretation of a composition relationship is that whole or part of the source element is composed of the whole of the target element.
  * @param whole
  * @param part
  */
final class CompositionRelationship(whole: Concept, part: Concept)
  extends StructuralRelationship(whole, part) {
  @inline override def meta: RelationshipMeta[CompositionRelationship] = StructuralRelationships.composition
}

/**
  * The aggregation relationship indicates that an element *groups* a number of other elements (elements are *grouped/aggregated by* the container).
  * Grouped elements are independent/separate, they could exists without the group.
  * The usual interpretation of an aggregation relationship is that whole or part of the source element aggregates the whole of the target element.
  * @param whole
  * @param part
  */
final class AggregationRelationship(whole: Concept, part: Concept)
  extends StructuralRelationship(whole, part) {
  @inline override def meta: RelationshipMeta[AggregationRelationship] = StructuralRelationships.aggregation
}

/**
  * The assignment relationship expresses the allocation of responsibility, performance of behavior, or execution.
  * The assignment relationship links active structure elements with units of behavior that are performed by them, business actors with business roles that are fulfilled by them, and nodes with technology objects. It can, for example, relate an internal active structure element with an internal behavior element, an interface with a service, or a node with a technology object.
  * The usual interpretation of an assignment relationship is that whole or part of the source element is assigned the whole of the target element.
  * @param source
  * @param target
  */
final class AssignmentRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source, target) {
  @inline override def meta: RelationshipMeta[AssignmentRelationship] = StructuralRelationships.assignment
}

/**
  * The realization relationship indicates that an entity plays a critical role in the creation, achievement, sustenance, or operation of a more abstract entity.
  * The realization relationship indicates that more abstract entities (“what” or “logical”) are realized by means of more tangible entities (“how” or “physical”)
  * The usual interpretation of a realization relationship is that the whole or part of the source element realizes the whole of the target element
  * @param source
  * @param target
  */
final class RealizationRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source, target) {
  @inline override def meta: RelationshipMeta[RealizationRelationship] = StructuralRelationships.realization
}