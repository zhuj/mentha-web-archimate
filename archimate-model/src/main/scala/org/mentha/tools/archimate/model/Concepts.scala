package org.mentha.tools.archimate.model

import org.apache.commons.lang3.builder.EqualsBuilder
import org.mentha.tools.archimate.model.edges.RelationshipMeta
import org.mentha.tools.archimate.model.nodes.{ElementMeta, RelationshipConnectorMeta}

/**
  * A model is a collection of concepts. A concept is either an element or a relationship.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap03.html#_Toc451757929 Language Structure ArchiMate® 3.0 Specification ]]
  */
object Concepts {

}

/**
  * An element is either a behavior element, a structure element, a motivation element, or a composite element.
  * All concepts are are vertex (could be source and target for an edge)
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap03.html#_Toc451757929 Language Structure ArchiMate® 3.0 Specification ]]
  */
sealed abstract class Concept
  extends IdentifiedArchimateObject
    with VersionedArchimateObject
    with DescribedArchimateObject
    with PropsArchimateObject
    with Vertex {

  def meta: ConceptMeta[Concept]
  private[model] def checkIncomingRelationship(incoming: Relationship): List[String] = Nil
  private[model] def checkOutgoingRelationship(outgoing: Relationship): List[String] = Nil
}

/**
  * Links two Concepts.
  * EdgeConcept is a Concept (due to the fact that it's possible to connect edges together)
  */
sealed abstract class EdgeConcept
  extends Concept
    with Edge[Concept] {

  // TODO: XXX: make it work safer - scheme should not contain cycles on the edges level
  override def isDeleted: Boolean = {
    super.isDeleted || (source.isDeleted || target.isDeleted)
  }

}

/**
  * Subclass of Concept - only Elements and Junctions.
  */
sealed abstract class NodeConcept
  extends Concept {

}

/**
  * Basic unit in the ArchiMate metamodel. Used to define and describe the constituent parts of Enterprise Architectures and their unique set of characteristics.
  */
abstract class Element
  extends NodeConcept
    with NamedArchimateObject {

  override def meta: ElementMeta[Element]
}

/**
  * Base class for connectors (junctions)
  * @see elements.Junction
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757967 Junction ArchiMate® 3.0 Specification ]]
  */
abstract class RelationshipConnector
  extends NodeConcept {

  def relationship: RelationshipMeta[Relationship]
  override def meta: RelationshipConnectorMeta[RelationshipConnector]
}

/**
  * A connection between a source and target concept. Classified as structural, dependency, dynamic, or other.
  * @note The ArchiMate language defines a core set of generic relationships, each of which can connect a predefined set of source and target concepts (in most cases elements, but in a few cases also other relationships).
  * @note Many of these relationships are ‘overloaded’; i.e., their exact meaning differs depending on the source and destination concepts that they connect.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html Relationships ArchiMate® 3.0 Specification ]]
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap02.html#_Toc451757928 Relationship Definition ArchiMate® 3.0 Specification]]
  * @param source
  * @param target
  */
abstract class Relationship(val source: Concept, val target: Concept)
  extends EdgeConcept
    with ValidArchimateObject {

  private[model] val _validationErrors: List[String] = {
    val builder = List.newBuilder[String]
    if (!meta.isLinkPossible(source.meta, target.meta)) {
      builder += s"Relationship `${meta.name}` is not possible between `${source.meta.name}` and `${target.meta.name}`"
    }
    // TODO: make sure it's recursion safe
    target.checkIncomingRelationship(this).foreach { v => builder += v }
    source.checkOutgoingRelationship(this).foreach { v => builder += v }
    builder.result()
  }

  @inline override def validationErrors: List[String] = _validationErrors

  private[model] var _derivation: Derivation = Derivation(this)
  @inline def derivation: Derivation = _derivation

  override def meta: RelationshipMeta[Relationship]

  def similarEqualsBuilder(that: this.type): EqualsBuilder = {
    new EqualsBuilder()
      .append(this.getClass, that.getClass)
      .append(this.source, that.source)
      .append(this.target, that.target)
  }

}
