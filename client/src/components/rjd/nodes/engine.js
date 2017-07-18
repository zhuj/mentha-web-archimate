import { registerGroupingNode } from './model_c/grouping'
import { registerLocationNode } from './model_c/location'
import { registerApplicationComponentNode } from './model_a/applicationComponent'
import { registerApplicationCollaborationNode } from './model_a/applicationCollaboration'
import { registerApplicationInterfaceNode } from './model_a/applicationInterface'
import { registerApplicationFunctionNode } from './model_a/applicationFunction'
import { registerApplicationInteractionNode } from './model_a/applicationInteraction'
import { registerApplicationProcessNode } from './model_a/applicationProcess'
import { registerApplicationEventNode } from './model_a/applicationEvent'
import { registerApplicationServiceNode } from './model_a/applicationService'
import { registerDataObjectNode } from './model_a/dataObject'
import { registerBusinessActorNode } from './model_b/businessActor'
import { registerBusinessRoleNode } from './model_b/businessRole'
import { registerBusinessCollaborationNode } from './model_b/businessCollaboration'
import { registerBusinessInterfaceNode } from './model_b/businessInterface'
import { registerBusinessProcessNode } from './model_b/businessProcess'
import { registerBusinessFunctionNode } from './model_b/businessFunction'
import { registerBusinessInteractionNode } from './model_b/businessInteraction'
import { registerBusinessEventNode } from './model_b/businessEvent'
import { registerBusinessServiceNode } from './model_b/businessService'
import { registerBusinessObjectNode } from './model_b/businessObject'
import { registerContractNode } from './model_b/contract'
import { registerRepresentationNode } from './model_b/representation'
import { registerProductNode } from './model_b/product'
import { registerWorkPackageNode } from './model_i/workPackage'
import { registerDeliverableNode } from './model_i/deliverable'
import { registerImplementationEventNode } from './model_i/implementationEvent'
import { registerPlateauNode } from './model_i/plateau'
import { registerGapNode } from './model_i/gap'
import { registerStakeholderNode } from './model_m/stakeholder'
import { registerDriverNode } from './model_m/driver'
import { registerAssessmentNode } from './model_m/assessment'
import { registerGoalNode } from './model_m/goal'
import { registerOutcomeNode } from './model_m/outcome'
import { registerPrincipleNode } from './model_m/principle'
import { registerRequirementNode } from './model_m/requirement'
import { registerConstraintNode } from './model_m/constraint'
import { registerMeaningNode } from './model_m/meaning'
import { registerValueNode } from './model_m/value'
import { registerEquipmentNode } from './model_p/equipment'
import { registerFacilityNode } from './model_p/facility'
import { registerDistributionNetworkNode } from './model_p/distributionNetwork'
import { registerMaterialNode } from './model_p/material'
import { registerResourceNode } from './model_s/resource'
import { registerCapabilityNode } from './model_s/capability'
import { registerCourseOfActionNode } from './model_s/courseOfAction'
import { registerNodeNode } from './model_t/node'
import { registerDeviceNode } from './model_t/device'
import { registerSystemSoftwareNode } from './model_t/systemSoftware'
import { registerTechnologyCollaborationNode } from './model_t/technologyCollaboration'
import { registerTechnologyInterfaceNode } from './model_t/technologyInterface'
import { registerPathNode } from './model_t/path'
import { registerCommunicationNetworkNode } from './model_t/communicationNetwork'
import { registerTechnologyFunctionNode } from './model_t/technologyFunction'
import { registerTechnologyProcessNode } from './model_t/technologyProcess'
import { registerTechnologyInteractionNode } from './model_t/technologyInteraction'
import { registerTechnologyEventNode } from './model_t/technologyEvent'
import { registerTechnologyServiceNode } from './model_t/technologyService'
import { registerArtifactNode } from './model_t/artifact'
import { registerAndJunctionNode } from './model_x/andJunction'
import { registerOrJunctionNode } from './model_x/orJunction'

import { registerViewNotesNode } from './view/viewNotes'
import { registerViewNodeConceptNode } from './view/viewNodeConcept'

export const registerNodes = (diagramEngine) => {
 registerGroupingNode(diagramEngine);
 registerLocationNode(diagramEngine);
 registerApplicationComponentNode(diagramEngine);
 registerApplicationCollaborationNode(diagramEngine);
 registerApplicationInterfaceNode(diagramEngine);
 registerApplicationFunctionNode(diagramEngine);
 registerApplicationInteractionNode(diagramEngine);
 registerApplicationProcessNode(diagramEngine);
 registerApplicationEventNode(diagramEngine);
 registerApplicationServiceNode(diagramEngine);
 registerDataObjectNode(diagramEngine);
 registerBusinessActorNode(diagramEngine);
 registerBusinessRoleNode(diagramEngine);
 registerBusinessCollaborationNode(diagramEngine);
 registerBusinessInterfaceNode(diagramEngine);
 registerBusinessProcessNode(diagramEngine);
 registerBusinessFunctionNode(diagramEngine);
 registerBusinessInteractionNode(diagramEngine);
 registerBusinessEventNode(diagramEngine);
 registerBusinessServiceNode(diagramEngine);
 registerBusinessObjectNode(diagramEngine);
 registerContractNode(diagramEngine);
 registerRepresentationNode(diagramEngine);
 registerProductNode(diagramEngine);
 registerWorkPackageNode(diagramEngine);
 registerDeliverableNode(diagramEngine);
 registerImplementationEventNode(diagramEngine);
 registerPlateauNode(diagramEngine);
 registerGapNode(diagramEngine);
 registerStakeholderNode(diagramEngine);
 registerDriverNode(diagramEngine);
 registerAssessmentNode(diagramEngine);
 registerGoalNode(diagramEngine);
 registerOutcomeNode(diagramEngine);
 registerPrincipleNode(diagramEngine);
 registerRequirementNode(diagramEngine);
 registerConstraintNode(diagramEngine);
 registerMeaningNode(diagramEngine);
 registerValueNode(diagramEngine);
 registerEquipmentNode(diagramEngine);
 registerFacilityNode(diagramEngine);
 registerDistributionNetworkNode(diagramEngine);
 registerMaterialNode(diagramEngine);
 registerResourceNode(diagramEngine);
 registerCapabilityNode(diagramEngine);
 registerCourseOfActionNode(diagramEngine);
 registerNodeNode(diagramEngine);
 registerDeviceNode(diagramEngine);
 registerSystemSoftwareNode(diagramEngine);
 registerTechnologyCollaborationNode(diagramEngine);
 registerTechnologyInterfaceNode(diagramEngine);
 registerPathNode(diagramEngine);
 registerCommunicationNetworkNode(diagramEngine);
 registerTechnologyFunctionNode(diagramEngine);
 registerTechnologyProcessNode(diagramEngine);
 registerTechnologyInteractionNode(diagramEngine);
 registerTechnologyEventNode(diagramEngine);
 registerTechnologyServiceNode(diagramEngine);
 registerArtifactNode(diagramEngine);
 registerAndJunctionNode(diagramEngine);
 registerOrJunctionNode(diagramEngine);
 registerViewNotesNode(diagramEngine);
 registerViewNodeConceptNode(diagramEngine);
}

