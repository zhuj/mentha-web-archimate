package org.mentha.tools.archimate.model

package object nodes {

  import impl.CompositionElements._
  import impl.ApplicationElements._
  import impl.BusinessElements._
  import impl.ImplementationElements._
  import impl.MotivationElements._
  import impl.PhysicalElements._
  import impl.StrategyElements._
  import impl.TechnologyElements._

  val allElements: Seq[ElementMeta[Element]] =
    compositionElements ++
    applicationElements ++
    businessElements ++
    implementationElements ++
    motivationElements ++
    physicalElements ++
    strategyElements ++
    technologyElements

  val allRelationshipConnectors: Seq[RelationshipConnectorMeta[RelationshipConnector]] =
    RelationshipConnectors.relationshipConnectors

  val allNodes: Seq[ConceptMeta[Concept]] =
    allElements ++ allRelationshipConnectors

  val businessInternalActiveStructureElements: Seq[ElementMeta[Element]] = Seq(
    impl.BusinessElements.businessActor,
    impl.BusinessElements.businessRole,
    impl.BusinessElements.businessCollaboration
  )

  val businessActiveStructureElements: Seq[ElementMeta[Element]] =
    businessInternalActiveStructureElements ++
    Seq(
      impl.BusinessElements.businessInterface
    )

  val businessInternalBehaviorElement: Seq[ElementMeta[Element]] = Seq(
    impl.BusinessElements.businessProcess,
    impl.BusinessElements.businessFunction,
    impl.BusinessElements.businessInteraction,
    impl.BusinessElements.businessEvent
  )

  val businessBehaviorElement: Seq[ElementMeta[Element]] =
    businessInternalBehaviorElement ++
    Seq(
      impl.BusinessElements.businessService
    )

  // A structure or behavior element in one of the core layers of the ArchiMate language.
  val coreElements: Seq[ElementMeta[Element]] =
    impl.BusinessElements.businessElements ++
    impl.ApplicationElements.applicationElements ++
    impl.TechnologyElements.technologyElements

  val mapElements: Map[String, ElementMeta[Element]] =
    allElements
      .map { m => (m.name, m) }
      .toMap

  val mapRelationshipConnectors: Map[String, RelationshipConnectorMeta[RelationshipConnector]] =
    allRelationshipConnectors
      .map { m => (m.name, m) }
      .toMap

}
