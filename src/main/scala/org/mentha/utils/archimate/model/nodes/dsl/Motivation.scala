package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Motivation {
  implicit class ImplicitStakeholder(src: Stakeholder) {
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Stakeholder)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Stakeholder)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Stakeholder)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Stakeholder)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
  implicit class ImplicitDriver(src: Driver) {
    def `aggregates`(dst: Driver)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Driver)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Driver)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
  implicit class ImplicitAssessment(src: Assessment) {
    def `aggregates`(dst: Assessment)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Assessment)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Assessment)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
  implicit class ImplicitGoal(src: Goal) {
    def `aggregates`(dst: Goal)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Goal)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Goal)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
  implicit class ImplicitOutcome(src: Outcome) {
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Outcome)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Outcome)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Outcome)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

 }
  implicit class ImplicitPrinciple(src: Principle) {
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Principle)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Principle)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Principle)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

 }
  implicit class ImplicitRequirement(src: Requirement) {
    def `composes`(dst: Constraint)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Constraint)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Constraint)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Requirement)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

 }
  implicit class ImplicitConstraint(src: Constraint) {
    def `composes`(dst: Constraint)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Constraint)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Constraint)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Requirement)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

 }
  implicit class ImplicitMeaning(src: Meaning) {
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Meaning)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Meaning)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Meaning)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
  implicit class ImplicitValue(src: Value) {
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Value)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Value)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Value)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String)(implicit model: Model) = new {
      def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

 }
}
