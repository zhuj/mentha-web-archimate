package org.mentha.utils.archimate.model.nodes.dsl

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.nodes.impl._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

@javax.annotation.Generated(Array("org.mentha.utils.archimate.model.generator$"))
object Implementation {

  implicit class ImplicitWorkPackage(src: WorkPackage)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: WorkPackage): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: WorkPackage): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: WorkPackage): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Deliverable): AccessRelationship = _accesses_of(src, dst)($0)(model)
      def `of`(dst: Grouping): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: WorkPackage): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: WorkPackage): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Deliverable): AccessRelationship = _reads(src, dst)(model)
    def `reads`(dst: Grouping): AccessRelationship = _reads(src, dst)(model)

    def `realizes`(dst: Deliverable): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationComponent): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationEvent): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationFunction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationInteraction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationInterface): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationProcess): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: ApplicationService): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessActor): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessEvent): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessFunction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessInteraction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessInterface): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessProcess): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessRole): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: BusinessService): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Capability): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: CommunicationNetwork): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: CourseOfAction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Device): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: DistributionNetwork): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Equipment): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Facility): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Node): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Path): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Plateau): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Resource): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: SystemSoftware): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyEvent): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyFunction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyInteraction): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyInterface): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyProcess): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: TechnologyService): RealizationRelationship = _realizes(src, dst)(model)

    def `writes`(dst: Deliverable): AccessRelationship = _writes(src, dst)(model)
    def `writes`(dst: Grouping): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitDeliverable(src: Deliverable)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Deliverable): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Deliverable): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Deliverable): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `realizes`(dst: ApplicationCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationComponent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationService): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Artifact): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessActor): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessObject): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessRole): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessService): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Capability): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CommunicationNetwork): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Contract): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DataObject): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Device): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DistributionNetwork): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Equipment): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Facility): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Material): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Node): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Path): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Plateau): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Representation): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Resource): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: SystemSoftware): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyService): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)

  }

  implicit class ImplicitImplementationEvent(src: ImplementationEvent)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ImplementationEvent): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ImplementationEvent): AggregationRelationship = _aggregates(src, dst)(model)

    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: ImplementationEvent): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Deliverable): AccessRelationship = _accesses_of(src, dst)($0)(model)
      def `of`(dst: Grouping): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: ImplementationEvent): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Plateau): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: WorkPackage): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: ImplementationEvent): FlowRelationship = _flows_to(src, dst)($0)(model)
      def `to`(dst: WorkPackage): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `influences`($0: String) = new {
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Deliverable): AccessRelationship = _reads(src, dst)(model)
    def `reads`(dst: Grouping): AccessRelationship = _reads(src, dst)(model)

    def `writes`(dst: Deliverable): AccessRelationship = _writes(src, dst)(model)
    def `writes`(dst: Grouping): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitPlateau(src: Plateau)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `composes`(dst: ApplicationCollaboration): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationComponent): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationEvent): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationFunction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationInteraction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationInterface): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationProcess): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: ApplicationService): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Artifact): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessActor): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessCollaboration): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessEvent): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessFunction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessInteraction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessInterface): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessObject): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessProcess): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessRole): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: BusinessService): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Capability): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: CommunicationNetwork): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Constraint): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Contract): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: CourseOfAction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: DataObject): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Device): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: DistributionNetwork): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Equipment): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Facility): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Goal): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Location): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Material): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Node): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Path): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Plateau): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Product): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Representation): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Requirement): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Resource): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: SystemSoftware): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyCollaboration): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyEvent): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyFunction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyInteraction): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyInterface): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyProcess): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: TechnologyService): CompositionRelationship = _composes(src, dst)(model)

    def `aggregates`(dst: ApplicationCollaboration): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationComponent): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationEvent): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationFunction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationInteraction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationInterface): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationProcess): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: ApplicationService): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Artifact): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessActor): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessCollaboration): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessEvent): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessFunction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessInteraction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessInterface): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessObject): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessProcess): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessRole): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: BusinessService): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Capability): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: CommunicationNetwork): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Constraint): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Contract): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: CourseOfAction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: DataObject): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Device): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: DistributionNetwork): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Equipment): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Facility): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Goal): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Location): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Material): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Node): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Path): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Plateau): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Product): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Representation): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Requirement): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Resource): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: SystemSoftware): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyCollaboration): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyEvent): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyFunction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyInteraction): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyInterface): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyProcess): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: TechnologyService): AggregationRelationship = _aggregates(src, dst)(model)

    def `serves`(dst: Grouping): ServingRelationship = _serves(src, dst)(model)

    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Plateau): SpecializationRelationship = _specializes(src, dst)(model)

    def `accesses`($0: AccessType) = new {
      def `of`(dst: Grouping): AccessRelationship = _accesses_of(src, dst)($0)(model)
    }

    def `triggers`(dst: Grouping): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: ImplementationEvent): TriggeringRelationship = _triggers(src, dst)(model)
    def `triggers`(dst: Plateau): TriggeringRelationship = _triggers(src, dst)(model)
    @derived def `triggers`(dst: WorkPackage): TriggeringRelationship = _triggers(src, dst)(model)

    def `flows`($0: String) = new {
      def `to`(dst: Grouping): FlowRelationship = _flows_to(src, dst)($0)(model)
    }

    def `assigned-to`(dst: Grouping): AssignmentRelationship = _assigned_to(src, dst)(model)

    def `influences`($0: String) = new {
      def `in`(dst: Constraint): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Grouping): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      def `in`(dst: Requirement): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Assessment): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Driver): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Goal): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Meaning): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Outcome): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Principle): InfluenceRelationship = _influences_in(src, dst)($0)(model)
      @derived def `in`(dst: Value): InfluenceRelationship = _influences_in(src, dst)($0)(model)
    }

    def `reads`(dst: Grouping): AccessRelationship = _reads(src, dst)(model)

    def `realizes`(dst: ApplicationCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationComponent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: ApplicationService): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Artifact): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessActor): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessObject): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessRole): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: BusinessService): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Capability): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CommunicationNetwork): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Constraint): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Contract): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: CourseOfAction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DataObject): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Device): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: DistributionNetwork): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Equipment): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Facility): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Grouping): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Location): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Material): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Node): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Path): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Product): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Representation): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Requirement): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: Resource): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: SystemSoftware): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyCollaboration): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyEvent): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyFunction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInteraction): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyInterface): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyProcess): RealizationRelationship = _realizes(src, dst)(model)
    def `realizes`(dst: TechnologyService): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Goal): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Outcome): RealizationRelationship = _realizes(src, dst)(model)
    @derived def `realizes`(dst: Principle): RealizationRelationship = _realizes(src, dst)(model)

    def `writes`(dst: Grouping): AccessRelationship = _writes(src, dst)(model)

  }

  implicit class ImplicitGap(src: Gap)(implicit val model: Model) {

    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)

    def `aggregates`(dst: Gap): AggregationRelationship = _aggregates(src, dst)(model)
    def `aggregates`(dst: Grouping): AggregationRelationship = _aggregates(src, dst)(model)

    def `composes`(dst: Gap): CompositionRelationship = _composes(src, dst)(model)
    def `composes`(dst: Grouping): CompositionRelationship = _composes(src, dst)(model)

    def `specializes`(dst: Gap): SpecializationRelationship = _specializes(src, dst)(model)
    def `specializes`(dst: Grouping): SpecializationRelationship = _specializes(src, dst)(model)

  }
}
