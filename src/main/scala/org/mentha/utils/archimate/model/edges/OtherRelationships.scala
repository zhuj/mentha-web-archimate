package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html
  */
object OtherRelationships {

  case object specialization extends RelationshipMeta[SpecializationRelationship] {
    override def key: Char = 's'
    override def name: String = "specializationRelationship"
    override def newInstance(source: Concept, target: Concept): SpecializationRelationship = new SpecializationRelationship(source, target)
  }
  case object association extends RelationshipMeta[AssociationRelationship] {
    override def key: Char = 'o'
    override def name: String = "associationRelationship"
    override def newInstance(source: Concept, target: Concept): AssociationRelationship = new AssociationRelationship(source, target)
  }

  val otherRelations: Seq[RelationshipMeta[Relationship]] = Seq(specialization, association)

}

/**
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757964 Other Relationships ArchiMate® 3.0 Specification]]
  * @param from
  * @param to
  */
abstract class OtherRelationship(from: Concept, to: Concept)
  extends Relationship(from, to) {

}

/**
  * The specialization relationship indicates that an element is a particular kind of another element.
  * @note The specialization relationship has been inspired by the generalization relationship in UML class diagrams, but is applicable to specialize a wider range of concepts. The specialization relationship can relate any instance of a concept with another instance of the same concept.
  * @note A specialization relationship is always allowed between two instances of the same element.
  * @note Alternatively, a specialization relationship can be expressed by nesting the specialized element inside the generic element.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757965 Specialization Relationship ArchiMate® 3.0 Specification]]
  * @param from
  * @param to
  */
final class SpecializationRelationship(from: Concept, to: Concept)
  extends OtherRelationship(from, to) {
  @inline override def meta: RelationshipMeta[SpecializationRelationship] = OtherRelationships.specialization
}

/**
  * An association models an unspecified relationship, or one that is not represented by another ArchiMate relationship.
  * @note An association relationship is always allowed between two elements, or between a relationship and an element.
  * @note The association relationship can be used when drawing a first high-level model where relationships are initially denoted in a generic way, and later refined to show more specific relationship types.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757966 Association Relationship ArchiMate® 3.0 Specification]]
  * @param from
  * @param to
  */
final class AssociationRelationship(from: Concept, to: Concept)
  extends OtherRelationship(from, to) {
  @inline override def meta: RelationshipMeta[AssociationRelationship] = OtherRelationships.association
}
