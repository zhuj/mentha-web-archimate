package org.mentha.tools.archimate.model

import org.mentha.tools.archimate.model.edges.impl._

package object edges {

  import StructuralRelationships._
  import DependencyRelationships._
  import DynamicRelationships._
  import OtherRelationships._

  val allRelations: Seq[RelationshipMeta[Relationship]] =
    structuralRelationships ++
    dependencyRelationships ++
    dynamicRelationships ++
    otherRelationships

  val mapRelations: Map[String, RelationshipMeta[Relationship]] =
    allRelations
      .map { m => (m.name, m) }
      .toMap

  @inline def _composes(whole: Concept, part: Concept)(implicit model: Model): CompositionRelationship = model.add { new CompositionRelationship(whole, part) }
  @inline def _aggregates(whole: Concept, part: Concept)(implicit model: Model): AggregationRelationship = model.add { new AggregationRelationship(whole, part) }
  @inline def _assigned_to(src: Concept, dst: Concept)(implicit model: Model): AssignmentRelationship = model.add { new AssignmentRelationship(src, dst) }
  @inline def _realizes(src: Concept, dst: Concept)(implicit model: Model): RealizationRelationship = model.add { new RealizationRelationship(src, dst) }
  @inline def _accesses(src: Concept, dst: Concept)(accessType: AccessType)(implicit model: Model): AccessRelationship = model.add { new AccessRelationship(src, dst)(accessType) }
  @inline def _serves(src: Concept, dst: Concept)(implicit model: Model): ServingRelationship = model.add { new ServingRelationship(src, dst) }
  @inline def _triggers(src: Concept, dst: Concept)(implicit model: Model): TriggeringRelationship = model.add { new TriggeringRelationship(src, dst) }
  @inline def _specializes(src: Concept, dst: Concept)(implicit model: Model): SpecializationRelationship = model.add { new SpecializationRelationship(src, dst) }
  @inline def _associated_with(src: Concept, dst: Concept)(implicit model: Model): AssociationRelationship = model.add { new AssociationRelationship(src, dst)() }

  @inline def _flows_to(src: Concept, dst: Concept)(what: String)(implicit model: Model): FlowRelationship = model.add { new FlowRelationship(src, dst)(what) }
  @inline def _influences_in(src: Concept, dst: Concept)(what: String)(implicit model: Model): InfluenceRelationship = model.add { new InfluenceRelationship(src, dst)(what) }

  @inline def _reads(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadAccess)(model)
  @inline def _writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(WriteAccess)(model)
  @inline def _reads_and_writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadWriteAccess)(model)
  @inline def _accesses_of(src: Concept, dst: Concept)(accessType: AccessType)(implicit model: Model): AccessRelationship = _accesses(src, dst)(accessType)(model)

//  // Association Relationship (any concepts)
//  implicit class AssociationRelations(val src: Concept)(implicit val model: Model) {
//    @inline def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)
//  }

}
