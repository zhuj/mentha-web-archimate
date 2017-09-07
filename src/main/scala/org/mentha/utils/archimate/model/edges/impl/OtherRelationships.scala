package org.mentha.utils.archimate.model.edges.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._


/**
 * Models an unspecified relationship, or one that is not represented by another ArchiMate relationship.
 * ==Overview==
 * An association relationship models an unspecified relationship, or one that is not represented by another ArchiMate relationship.
 * @note An association relationship is always allowed between two elements, or between a relationship and an element.
 * @note The association relationship can be used when drawing a first high-level model where relationships are initially denoted in a generic way, and later refined to show more specific relationship types.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489946003 AssociationRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class AssociationRelationship(source: Concept, target: Concept)
  extends OtherRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[AssociationRelationship] = OtherRelationships.associationRelationship
}

/**
 * Indicates that an element is a particular kind of another element.
 * ==Overview==
 * The specialization relationship indicates that an element is a particular kind of another element.
 * @note The specialization relationship has been inspired by the generalization relationship in UML class diagrams, but is applicable to specialize a wider range of concepts. The specialization relationship can relate any instance of a concept with another instance of the same concept.
 * @note A specialization relationship is always allowed between two instances of the same element.
 * @note Alternatively, a specialization relationship can be expressed by nesting the specialized element inside the generic element.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489946002 SpecializationRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class SpecializationRelationship(source: Concept, target: Concept)
  extends OtherRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[SpecializationRelationship] = OtherRelationships.specializationRelationship
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object OtherRelationships {

  case object associationRelationship extends RelationshipMeta[AssociationRelationship] {
    override final def key: Char = 'o'
    override final def name: String = "associationRelationship"
    override final def newInstance(source: Concept, target: Concept): AssociationRelationship = new AssociationRelationship(source, target)
  }
  case object specializationRelationship extends RelationshipMeta[SpecializationRelationship] {
    override final def key: Char = 's'
    override final def name: String = "specializationRelationship"
    override final def newInstance(source: Concept, target: Concept): SpecializationRelationship = new SpecializationRelationship(source, target)
  }

  val otherRelationships: Seq[RelationshipMeta[Relationship]] = Seq(associationRelationship, specializationRelationship)

}
