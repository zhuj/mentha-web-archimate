package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html
  */
object DynamicRelationships {

  case object triggering extends RelationshipMeta[TriggeringRelationship] {
    override def newInstance(source: Concept, target: Concept): TriggeringRelationship = new TriggeringRelationship(source, target)
  }
  case object flow extends RelationshipMeta[FlowRelationship] {
    override def newInstance(source: Concept, target: Concept): FlowRelationship = new FlowRelationship(source, target)()
  }

  val dynamicRelations: Seq[RelationshipMeta[_]] = Seq(triggering, flow)

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
  * The triggering relationship describes a temporal or causal relationship between elements.
  * The triggering relationship is used to model the temporal or causal precedence of behavior elements in a process.
  * Note that this does not necessarily represent that one behavior element actively starts another: a traffic light turning green also triggers the cars to go through the intersection.
  * @param from
  * @param to
  */
final class TriggeringRelationship(from: Concept, to: Concept)
  extends DynamicRelationship(from, to) {
  @inline override def meta: RelationshipMeta[TriggeringRelationship] = DynamicRelationships.triggering
}

/**
  * The flow relationship represents transfer from one element to another.
  * The flow relationship is used to model the flow of, for example, information, goods, or money between behavior elements.
  * A flow relationship does not imply a causal relationship.
  * @param from
  * @param to
  * @param what
  */
final class FlowRelationship(from: Concept, to: Concept)(var what: String = null)
  extends DynamicRelationship(from, to) {
  @inline override def meta: RelationshipMeta[FlowRelationship] = DynamicRelationships.flow

  def withFlows(flows: String): this.type = {
    this.what = flows
    this
  }

}
