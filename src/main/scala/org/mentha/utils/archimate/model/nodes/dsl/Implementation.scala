package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Implementation {

  implicit class ImplicitWorkPackage(src: WorkPackage) {
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: WorkPackage)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: WorkPackage)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: WorkPackage)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Deliverable)(implicit model: Model): AccessRelationship = _accesses_of(src, dst)($0)(model)
      def `of`(dst: Grouping)(implicit model: Model): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: WorkPackage)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: WorkPackage)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Deliverable)(implicit model: Model): AccessRelationship = _reads(src, dst)(model)
    def `reads`(dst: Grouping)(implicit model: Model): AccessRelationship = _reads(src, dst)(model)

    def `realizes`(dst: Deliverable)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationComponent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessActor)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessRole)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Capability)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: CommunicationNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: CourseOfAction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Device)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: DistributionNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Equipment)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Facility)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Node)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Path)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Plateau)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Resource)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: SystemSoftware)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

    def `writes`(dst: Deliverable)(implicit model: Model): AccessRelationship = _writes(src, dst)(model)
    def `writes`(dst: Grouping)(implicit model: Model): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitDeliverable(src: Deliverable) {
    def `composes`(dst: Deliverable)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Deliverable)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Deliverable)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: ApplicationCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationComponent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Artifact)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessActor)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessObject)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessRole)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Capability)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CommunicationNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Contract)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DataObject)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Device)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DistributionNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Equipment)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Facility)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Material)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Node)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Path)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Plateau)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Representation)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Resource)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: SystemSoftware)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitImplementationEvent(src: ImplementationEvent) {
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ImplementationEvent)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ImplementationEvent)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: ImplementationEvent)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Deliverable)(implicit model: Model): AccessRelationship = _accesses_of(src, dst)($0)(model)
      def `of`(dst: Grouping)(implicit model: Model): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: ImplementationEvent)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Plateau)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: WorkPackage)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: ImplementationEvent)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: WorkPackage)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Grouping)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value)(implicit model: Model): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Deliverable)(implicit model: Model): AccessRelationship = _reads(src, dst)(model)
    def `reads`(dst: Grouping)(implicit model: Model): AccessRelationship = _reads(src, dst)(model)

    def `writes`(dst: Deliverable)(implicit model: Model): AccessRelationship = _writes(src, dst)(model)
    def `writes`(dst: Grouping)(implicit model: Model): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitPlateau(src: Plateau) {
    def `composes`(dst: ApplicationCollaboration)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationComponent)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationEvent)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationFunction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationInteraction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationInterface)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationProcess)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationService)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Artifact)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessActor)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessCollaboration)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessEvent)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessFunction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessInteraction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessInterface)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessObject)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessProcess)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessRole)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessService)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Capability)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: CommunicationNetwork)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Constraint)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Contract)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: CourseOfAction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: DataObject)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Device)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: DistributionNetwork)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Equipment)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Facility)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Goal)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Location)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Material)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Node)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Path)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Plateau)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Product)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Representation)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Resource)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: SystemSoftware)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyCollaboration)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyEvent)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyFunction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyInteraction)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyInterface)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyProcess)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyService)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: ApplicationCollaboration)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationComponent)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationEvent)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationFunction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationInteraction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationInterface)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationProcess)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationService)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Artifact)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessActor)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessCollaboration)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessEvent)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessFunction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessInteraction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessInterface)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessObject)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessProcess)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessRole)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessService)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Capability)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: CommunicationNetwork)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Constraint)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Contract)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: CourseOfAction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: DataObject)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Device)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: DistributionNetwork)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Equipment)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Facility)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Goal)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Location)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Material)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Node)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Path)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Plateau)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Product)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Representation)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Resource)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: SystemSoftware)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyCollaboration)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyEvent)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyFunction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyInteraction)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyInterface)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyProcess)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyService)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Grouping)(implicit model: Model): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Plateau)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Grouping)(implicit model: Model): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: ImplementationEvent)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Plateau)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)
    @derived def `triggers`(dst: WorkPackage)(implicit model: Model): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping)(implicit model: Model): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

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

    def `reads`(dst: Grouping)(implicit model: Model): AccessRelationship = _reads(src, dst)(model)

    def `realizes`(dst: ApplicationCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationComponent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Artifact)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessActor)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessObject)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessRole)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Capability)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CommunicationNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Contract)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DataObject)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Device)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DistributionNetwork)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Equipment)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Facility)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Material)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Node)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Path)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Representation)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Resource)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: SystemSoftware)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyCollaboration)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyEvent)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyFunction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInteraction)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInterface)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyProcess)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyService)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle)(implicit model: Model): RealizationRelationship = _realizes(src, dst)(model)

    def `writes`(dst: Grouping)(implicit model: Model): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitGap(src: Gap) {
    def `aggregates`(dst: Gap)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping)(implicit model: Model): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Gap)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping)(implicit model: Model): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Gap)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping)(implicit model: Model): SpecializationRelationship = _specializes(src, dst)(model)

  }
}
