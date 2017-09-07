package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Motivation {

  implicit class ImplicitStakeholder(src: Stakeholder)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Stakeholder): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Stakeholder): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Stakeholder): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Junction): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Stakeholder): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

  }

  implicit class ImplicitDriver(src: Driver)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Driver): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Driver): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Driver): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

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

  }

  implicit class ImplicitAssessment(src: Assessment)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Assessment): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Assessment): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Assessment): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

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

  }

  implicit class ImplicitGoal(src: Goal)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Goal): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Goal): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Goal): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

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

  }

  implicit class ImplicitOutcome(src: Outcome)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Outcome): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Outcome): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Outcome): SpecializationRelationship = _specializes(src, dst)(model)

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
    def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitPrinciple(src: Principle)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Principle): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Principle): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Principle): SpecializationRelationship = _specializes(src, dst)(model)

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
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitRequirement(src: Requirement)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Constraint): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Constraint): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Constraint): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Requirement): SpecializationRelationship = _specializes(src, dst)(model)

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
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitConstraint(src: Constraint)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Constraint): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Constraint): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Constraint): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Requirement): SpecializationRelationship = _specializes(src, dst)(model)

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
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitMeaning(src: Meaning)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Meaning): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Meaning): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Meaning): SpecializationRelationship = _specializes(src, dst)(model)

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

  }

  implicit class ImplicitValue(src: Value)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Junction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Value): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Junction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Value): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Junction): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Value): SpecializationRelationship = _specializes(src, dst)(model)

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

  }
}
