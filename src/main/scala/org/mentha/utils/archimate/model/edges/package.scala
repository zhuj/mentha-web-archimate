package org.mentha.utils.archimate.model

package object edges {

  import StructuralRelationships._
  import DependencyRelationships._
  import DynamicRelationships._
  import OtherRelationships._

  val allRelations: Seq[RelationshipMeta[_]] =
    structuralRelations ++
    dependencyRelations ++
    dynamicRelations ++
    otherRelations

  val mapRelations: Map[String, RelationshipMeta[Relationship]] =
    allRelations
      .map { m => (m.name, m.asInstanceOf[RelationshipMeta[Relationship]]) }
      .toMap

  def _composes(whole: Concept, part: Concept)(implicit model: Model): CompositionRelationship = model.add(new CompositionRelationship(whole, part))
  def _aggregates(whole: Concept, part: Concept)(implicit model: Model): AggregationRelationship = model.add(new AggregationRelationship(whole, part))
  def _assigned_to(src: Concept, dst: Concept)(implicit model: Model): AssignmentRelationship = model.add(new AssignmentRelationship(src, dst))
  def _realizes(src: Concept, dst: Concept)(implicit model: Model): RealizationRelationship = model.add(new RealizationRelationship(src, dst))
  def _accesses(src: Concept, dst: Concept)(accessType: AccessType)(implicit model: Model): AccessRelationship = model.add(new AccessRelationship(src, dst)(accessType))
  def _serves(src: Concept, dst: Concept)(implicit model: Model): ServingRelationship = model.add(new ServingRelationship(src, dst))
  def _triggers(src: Concept, dst: Concept)(implicit model: Model): TriggeringRelationship = model.add(new TriggeringRelationship(src, dst))
  def _specializes(src: Concept, dst: Concept)(implicit model: Model): SpecializationRelationship = model.add( new SpecializationRelationship(src, dst) )
  def _associated_with(src: Concept, dst: Concept)(implicit model: Model): AssociationRelationship = model.add( new AssociationRelationship(src, dst) )
  def _flows_to(src: Concept, dst: Concept)(what: String)(implicit model: Model): FlowRelationship = model.add( new FlowRelationship(src, dst)(what) )
  def _influences_in(src: Concept, dst: Concept)(what: String)(implicit model: Model): InfluenceRelationship = model.add( new InfluenceRelationship(src, dst)(what) )

  def _reads(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadAccess)(model)
  def _writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(WriteAccess)(model)
  def _reads_and_writes(src: Concept, dst: Concept)(implicit model: Model): AccessRelationship = _accesses(src, dst)(ReadWriteAccess)(model)

  // Association Relationship (any concepts)
  implicit class AssociationRelations(val src: Concept)(implicit val model: Model) {
    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)
  }

}
