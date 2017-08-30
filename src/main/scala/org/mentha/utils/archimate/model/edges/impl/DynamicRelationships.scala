package org.mentha.utils.archimate.model.edges.impl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._


/**
 * Transfer from one element to another.
 * ==Overview==
 * The flow relationship represents transfer from one element to another.
 * The flow relationship is used to model the flow of, for example, information, goods, or money between behavior elements.
 * @note A flow relationship does not imply a causal relationship.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489946000 FlowRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class FlowRelationship(source: Concept, target: Concept)(var flow: String = null)
  extends DynamicRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[FlowRelationship] = DynamicRelationships.flowRelationship
  @inline def withFlow(flow: String): this.type = {
    this.flow = flow
    this
  }
}

/**
 * Describes a temporal or causal relationship between elements.
 * ==Overview==
 * The triggering relationship describes a temporal or causal relationship between elements.
 * The triggering relationship is used to model the temporal or causal precedence of behavior elements in a process.
 * The usual interpretation of a triggering relationship is that the source element should be completed before the target element can start, although weaker interpretations are also permitted.
 * @note Note that this does not necessarily represent that one behavior element actively starts another; a traffic light turning green also triggers the cars to go through the intersection.
 * @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc489945999 TriggeringRelationship ArchiMate® 3.0 Specification ]]
 */
@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
final class TriggeringRelationship(source: Concept, target: Concept)
  extends DynamicRelationship(source: Concept, target: Concept) {
  @inline override def meta: RelationshipMeta[TriggeringRelationship] = DynamicRelationships.triggeringRelationship
}

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object DynamicRelationships {

  case object flowRelationship extends RelationshipMeta[FlowRelationship] {
    override def key: Char = 'f'
    override def name: String = "flowRelationship"
    override def newInstance(source: Concept, target: Concept): FlowRelationship = new FlowRelationship(source, target)()
  }
  case object triggeringRelationship extends RelationshipMeta[TriggeringRelationship] {
    override def key: Char = 't'
    override def name: String = "triggeringRelationship"
    override def newInstance(source: Concept, target: Concept): TriggeringRelationship = new TriggeringRelationship(source, target)
  }

  val dynamicRelationships: Seq[RelationshipMeta[Relationship]] = Seq(flowRelationship, triggeringRelationship)

}
