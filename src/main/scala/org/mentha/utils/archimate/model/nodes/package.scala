package org.mentha.utils.archimate.model

package object nodes {

  import impl.CompositionElements._
  import impl.ApplicationElements._
  import impl.BusinessElements._
  import impl.ImplementationElements._
  import impl.MotivationElements._
  import impl.PhysicalElements._
  import impl.StrategyElements._
  import impl.TechnologyElements._

  val allElements: Seq[ElementMeta[_]] =
    compositionElements ++
    applicationElements ++
    businessElements ++
    implementationElements ++
    motivationElements ++
    physicalElements ++
    strategyElements ++
    technologyElements

  val allRelationshipConnectors: Seq[RelationshipConnectorMeta[_]] =
    RelationshipConnectors.relationshipConnectors

  val allNodes: Seq[ConceptMeta[_]] =
    allElements ++ allRelationshipConnectors

  val businessInternalActiveStructureElements: Seq[ElementMeta[_]] = Seq(
    impl.BusinessElements.businessActor,
    impl.BusinessElements.businessRole,
    impl.BusinessElements.businessCollaboration
  )

  val businessActiveStructureElements: Seq[ElementMeta[_]] =
    businessInternalActiveStructureElements ++
    Seq(
      impl.BusinessElements.businessInterface
    )

  val businessInternalBehaviorElement: Seq[ElementMeta[_]] = Seq(
    impl.BusinessElements.businessProcess,
    impl.BusinessElements.businessFunction,
    impl.BusinessElements.businessInteraction,
    impl.BusinessElements.businessEvent
  )

  val businessBehaviorElement: Seq[ElementMeta[_]] =
    businessInternalBehaviorElement ++
    Seq(
      impl.BusinessElements.businessService
    )

  val coreElements: Seq[ElementMeta[_]] =
    impl.BusinessElements.businessElements ++
    impl.ApplicationElements.applicationElements ++
    impl.TechnologyElements.technologyElements

  val mapElements: Map[String, ElementMeta[Element]] =
    allElements
      .map { m => (m.name, m.asInstanceOf[ElementMeta[Element]]) }
      .toMap

  val mapRelationshipConnectors: Map[String, RelationshipConnectorMeta[RelationshipConnector]] =
    allRelationshipConnectors
      .map { m => (m.name, m.asInstanceOf[RelationshipConnectorMeta[RelationshipConnector]]) }
      .toMap

}
