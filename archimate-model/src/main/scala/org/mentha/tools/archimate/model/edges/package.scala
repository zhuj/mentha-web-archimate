package org.mentha.tools.archimate.model

import org.mentha.tools.archimate.model.edges.impl._

import scala.reflect.ClassTag

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


  // ===================== DSL ONLY ====================== //
  //   The following methods should be used for DSL only   //

  /** it adds relationship to the model with duplication check: it will return existing relationship if there is the similar one in the model */
  @inline private def _add[T <: Relationship](model: Model, candidate: T)(implicit tp: ClassTag[T]): T =  {
    model.concepts[T]
      .find { c => candidate.eq(c) || candidate.similarEqualsBuilder(c.asInstanceOf[candidate.type]).isEquals }
      .getOrElse { model.add { candidate } }
  }

  @inline def _composes(whole: Concept, part: Concept)(implicit model: Model): CompositionRelationship = _add(model, new CompositionRelationship(whole, part))
  @inline def _aggregates(whole: Concept, part: Concept)(implicit model: Model): AggregationRelationship = _add(model, new AggregationRelationship(whole, part))
  @inline def _assigned_to(src: Concept, dst: Concept)(implicit model: Model): AssignmentRelationship = _add(model, new AssignmentRelationship(src, dst))
  @inline def _realizes(src: Concept, dst: Concept)(implicit model: Model): RealizationRelationship = _add(model, new RealizationRelationship(src, dst))
  @inline def _accesses(src: Concept, dst: Concept)(accessType: AccessType)(implicit model: Model): AccessRelationship = _add(model, new AccessRelationship(src, dst)(accessType))
  @inline def _serves(src: Concept, dst: Concept)(implicit model: Model): ServingRelationship = _add(model, new ServingRelationship(src, dst))
  @inline def _triggers(src: Concept, dst: Concept)(implicit model: Model): TriggeringRelationship = _add(model, new TriggeringRelationship(src, dst))
  @inline def _specializes(src: Concept, dst: Concept)(implicit model: Model): SpecializationRelationship = _add(model, new SpecializationRelationship(src, dst))
  @inline def _associated_with(src: Concept, dst: Concept)(implicit model: Model): AssociationRelationship = _add(model, new AssociationRelationship(src, dst)())

  @inline def _flows_to(src: Concept, dst: Concept)(what: String)(implicit model: Model): FlowRelationship = _add(model, new FlowRelationship(src, dst)(what))
  @inline def _influences_in(src: Concept, dst: Concept)(what: String)(implicit model: Model): InfluenceRelationship = _add(model, new InfluenceRelationship(src, dst)(what))

  @inline def _reads(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadAccess)(model)
  @inline def _writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(WriteAccess)(model)
  @inline def _reads_and_writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadWriteAccess)(model)
  @inline def _accesses_of(src: Concept, dst: Concept)(accessType: AccessType)(implicit model: Model): AccessRelationship = _accesses(src, dst)(accessType)(model)

}
