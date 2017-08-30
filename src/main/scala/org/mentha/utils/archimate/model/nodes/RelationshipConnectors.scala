package org.mentha.utils.archimate.model.nodes

import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges.RelationshipMeta
import org.mentha.utils.archimate.model.edges.impl.AssociationRelationship

/**
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757967 Junction ArchiMate® 3.0 Specification ]]
  */
object RelationshipConnectors {

  case object andJunction extends RelationshipConnectorMeta[AndJunction] {
    override def newInstance(relationship: RelationshipMeta[Relationship]): AndJunction = new AndJunction(relationship)
  }
  case object orJunction extends RelationshipConnectorMeta[OrJunction] {
    override def newInstance(relationship: RelationshipMeta[Relationship]): OrJunction = new OrJunction(relationship)
  }

  val relationshipConnectors: Seq[RelationshipConnectorMeta[RelationshipConnector]] = Seq(andJunction, orJunction)

}


/**
  * A junction is used to connect relationships of the same type.
  * @note A junction is not an actual relationship in the same sense as the other relationships described in this chapter, but rather a relationship connector.
  * @note A junction is used in a number of situations to connect relationships of the same type. A junction may have multiple incoming relationships and one outgoing relationship, one incoming relationship and multiple outgoing relationships, or multiple incoming and outgoing relationships (the latter can be considered a shorthand of two subsequent junctions).
  * @note The relationships that can be used in combination with a junction are all the dynamic relationships, as well as assignment, realization, and association. A junction is used to explicitly express that several elements together participate in the relationship (and junction) or that one of the elements participates in the relationship (or junction). A junction should either have one incoming and more than one outgoing relationships, or more than one incoming and one outgoing. It is allowed to omit arrowheads of relationships leading into a junction.
  * @note Junctions used on triggering relationships are similar to gateways in BPMN and forks and joins in UML activity diagrams. They can be used to model high-level process flow. A label may be added to outgoing triggering relationships of a junction to indicate a choice, condition, or guard that applies to that relationship. Such a label is only an informal indication. No formal, operational semantics has been defined for these relationships, because implementation-level languages such as BPMN and UML differ in their execution semantics and the ArchiMate language does not want to unduly constrain mappings to such languages.
  * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757967 Junction ArchiMate® 3.0 Specification ]]
  */
abstract class Junction(val relationship: RelationshipMeta[Relationship]) extends RelationshipConnector {

  private def check(r: Relationship, side: String): List[String] = {
    if (relationship.runtimeClass.isInstance(r) || r.isInstanceOf[AssociationRelationship]) { Nil }
    else {
      List( s"${StringUtils.capitalize(side)} relationship `${r.meta.name}` is not possible for Junction(`${relationship.name}`)" )
    }
  }

  override private[model] def checkIncomingRelationship(incoming: Relationship) = check(incoming, "incoming")
  override private[model] def checkOutgoingRelationship(outgoing: Relationship) = check(outgoing, "outgoing")

}

/** @inheritdoc */
final class AndJunction(relationship: RelationshipMeta[Relationship]) extends Junction(relationship) {
  @inline override def meta: RelationshipConnectorMeta[AndJunction] = RelationshipConnectors.andJunction
}

/** @inheritdoc */
final class OrJunction(relationship: RelationshipMeta[Relationship]) extends Junction(relationship) {
  @inline override def meta: RelationshipConnectorMeta[OrJunction] = RelationshipConnectors.orJunction
}
