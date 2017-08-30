package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Strategy {

  implicit class ImplicitResource(src: Resource) {
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Resource)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Resource)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Grouping)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)
    @derived def `serves`(dst: Capability)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)
    @derived def `serves`(dst: CourseOfAction)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Resource)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    @derived def `triggers`(dst: Capability)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    @derived def `triggers`(dst: Resource)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: Capability)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: CourseOfAction)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: Resource)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `assigned-to`(dst: Capability)(implicit model: Model): AssignmentRelationship = _assigned_to(src, dst)(model)
    def `assigned-to`(dst: Grouping)(implicit model: Model): AssignmentRelationship = _assigned_to(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: CourseOfAction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitCapability(src: Capability) {
    def `composes`(dst: Capability)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Capability)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Capability)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: CourseOfAction)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Grouping)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Capability)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: Capability)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    @derived def `triggers`(dst: Resource)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Capability)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: CourseOfAction)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: Resource)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitCourseOfAction(src: CourseOfAction) {
    def `composes`(dst: CourseOfAction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: CourseOfAction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: CourseOfAction)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Grouping)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: CourseOfAction)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: CourseOfAction)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: CourseOfAction)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: Capability)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      @derived def `to`(dst: Resource)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

  }
}
