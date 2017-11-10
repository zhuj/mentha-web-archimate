package org.mentha.tools.archimate.model.edges.impl

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._


/**
 * Indicates that an element consists of one or more other concepts (elements are *part of* the container).
 * ==Overview==
 * The composition relationship indicates that an element *consists of* one or more other elements (elements are *part of* the container).
 * Composed elements exist only within its container, the relation describes how the container splits into its parts.
 * The usual interpretation of a composition relationship is that the whole or part of the source element is composed of the whole of the target element.
 * @note The composition relationship has been inspired by the composition relationship in UML class diagrams. In contrast to the aggregation relationship, the composed concept can be part of only one composition.
 * @note A composition relationship is always allowed between two instances of the same element type.
 * @note In addition to this, the metamodel explicitly defines other source and target elements that may be connected by a composition relationship.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945990 CompositionRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class CompositionRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[CompositionRelationship] = StructuralRelationships.compositionRelationship
}

/**
 * Expresses the allocation of responsibility, performance of behavior, or execution.
 * ==Overview==
 * The assignment relationship expresses the allocation of responsibility, performance of behavior, or execution.
 * The usual interpretation of an assignment relationship is that the whole or part of the source element is assigned the whole of the target element.
 * @note The assignment relationship links active structure elements with units of behavior that are performed by them, business actors with business roles that are fulfilled by them, and nodes with technology objects.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945992 AssignmentRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class AssignmentRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[AssignmentRelationship] = StructuralRelationships.assignmentRelationship
}

/**
 * Indicates that an element consists of one or more other concepts (elements are *grouped/aggregated* by the container).
 * ==Overview==
 * The aggregation relationship indicates that an element *groups* a number of other elements (elements are *grouped/aggregated* by the container).
 * Grouped elements are independent/separate, they could exists without the group.
 * The usual interpretation of an aggregation relationship is that the whole or part of the source element aggregates the whole of the target element.
 * @note The aggregation relationship has been inspired by the aggregation relationship in UML class diagrams. In contrast to the composition relationship, an object can be part of more than one aggregation.
 * @note An aggregation relationship is always allowed between two instances of the same element type.
 * @note In addition to this, the metamodel explicitly defines other source and target elements that may be connected by an aggregation relationship.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945991 AggregationRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class AggregationRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[AggregationRelationship] = StructuralRelationships.aggregationRelationship
}

/**
 * Indicates that an entity plays a critical role in the creation, achievement, sustenance, or operation of a more abstract entity.
 * ==Overview==
 * The realization relationship indicates that an entity plays a critical role in the creation, achievement, sustenance, or operation of a more abstract entity.
 * The usual interpretation of a realization relationship is that the whole or part of the source element realizes the whole of the target element.
 * @note The realization relationship indicates that more abstract entities (“what” or “logical”) are realized by means of more tangible entities (“how” or “physical”).
 * @note The realization relationship is used to model run-time realization; for example, that a business process realizes a business service, and that a data object realizes a business object, an artifact realizes an application component, or a core element realizes a motivation element.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945993 RealizationRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
final class RealizationRelationship(source: Concept, target: Concept)
  extends StructuralRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[RealizationRelationship] = StructuralRelationships.realizationRelationship
}

@javax.annotation.Generated(Array("org.mentha.tools.archimate.model.generator$"))
object StructuralRelationships {

  case object compositionRelationship extends RelationshipMeta[CompositionRelationship] {
    override final def key: Char = 'c'
    override final def name: String = "compositionRelationship"
    override final def newInstance(source: Concept, target: Concept): CompositionRelationship = new CompositionRelationship(source, target)
  }
  case object assignmentRelationship extends RelationshipMeta[AssignmentRelationship] {
    override final def key: Char = 'i'
    override final def name: String = "assignmentRelationship"
    override final def newInstance(source: Concept, target: Concept): AssignmentRelationship = new AssignmentRelationship(source, target)
  }
  case object aggregationRelationship extends RelationshipMeta[AggregationRelationship] {
    override final def key: Char = 'g'
    override final def name: String = "aggregationRelationship"
    override final def newInstance(source: Concept, target: Concept): AggregationRelationship = new AggregationRelationship(source, target)
  }
  case object realizationRelationship extends RelationshipMeta[RealizationRelationship] {
    override final def key: Char = 'r'
    override final def name: String = "realizationRelationship"
    override final def newInstance(source: Concept, target: Concept): RealizationRelationship = new RealizationRelationship(source, target)
  }

  val structuralRelationships: Seq[RelationshipMeta[Relationship]] = Seq(compositionRelationship, assignmentRelationship, aggregationRelationship, realizationRelationship)

}
