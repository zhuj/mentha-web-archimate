package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Junctions {

  implicit class ImplicitJunction(src: Junction)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Concept): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Concept): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Junction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Concept): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Concept): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Junction): AccessRelationship = _accesses_of(src, dst)($0)(model)
      def `of`(dst: Concept): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Junction): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Concept): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Junction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Concept): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `assigned to`(dst: Junction): AssignmentRelationship = _assigned_to(src, dst)(model)
    def `assigned to`(dst: Concept): AssignmentRelationship = _assigned_to(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Junction): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Concept): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Junction): AccessRelationship = _reads(src, dst)(model)
    def `reads`(dst: Concept): AccessRelationship = _reads(src, dst)(model)

    def `realizes`(dst: Junction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Concept): RealizationRelationship = _realizes(src, dst)(model)

    def `writes`(dst: Junction): AccessRelationship = _writes(src, dst)(model)
    def `writes`(dst: Concept): AccessRelationship = _writes(src, dst)(model)

  }
}
