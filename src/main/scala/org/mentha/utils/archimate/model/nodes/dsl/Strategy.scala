package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Strategy {

  implicit class ImplicitResource(src: Resource)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Resource): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Resource): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Junction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Capability): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: CourseOfAction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Grouping): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Resource): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: Junction): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Capability): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Resource): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Junction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Capability): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: CourseOfAction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Resource): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `assigned to`(dst: Junction): AssignmentRelationship = _assigned_to(src, dst)(model)
    def `assigned to`(dst: Capability): AssignmentRelationship = _assigned_to(src, dst)(model)
    def `assigned to`(dst: Grouping): AssignmentRelationship = _assigned_to(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Junction): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Junction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitCapability(src: Capability)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Capability): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Capability): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Junction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Capability): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: CourseOfAction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Grouping): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Capability): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: Junction): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Capability): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Resource): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Junction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Capability): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: CourseOfAction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Resource): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Junction): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Junction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitCourseOfAction(src: CourseOfAction)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: CourseOfAction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: CourseOfAction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Junction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: CourseOfAction): ServingRelationship = _serves(src, dst)(model)
    def `serves`(dst: Grouping): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: CourseOfAction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

    def `triggers`(dst: Junction): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: CourseOfAction): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Junction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Capability): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: CourseOfAction): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: Resource): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Junction): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Junction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)

  }
}
