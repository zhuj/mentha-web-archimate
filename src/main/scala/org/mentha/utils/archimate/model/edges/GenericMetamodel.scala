package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

object GenericMetamodel {

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
  * Dependency relationships describe how elements support or are used by other elements.
  * @param source
  * @param target
  */
abstract class DependencyRelationship(source: Concept, target: Concept)
  extends Relationship(source, target) {

}

/**
  * The dynamic relationships describe temporal dependencies between elements within the architecture.
  * @param from
  * @param to
  */
abstract class DynamicRelationship(from: Concept, to: Concept)
  extends Relationship(from, to) {

}

/**
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757964 Other Relationships ArchiMate® 3.0 Specification]]
  * @param from
  * @param to
  */
abstract class OtherRelationship(from: Concept, to: Concept)
  extends Relationship(from, to) {

}