import { registerCompositionRelationshipLink } from './model/compositionRelationship'
import { registerAggregationRelationshipLink } from './model/aggregationRelationship'
import { registerAssignmentRelationshipLink } from './model/assignmentRelationship'
import { registerRealizationRelationshipLink } from './model/realizationRelationship'
import { registerServingRelationshipLink } from './model/servingRelationship'
import { registerAccessRelationshipLink } from './model/accessRelationship'
import { registerInfluenceRelationshipLink } from './model/influenceRelationship'
import { registerTriggeringRelationshipLink } from './model/triggeringRelationship'
import { registerFlowRelationshipLink } from './model/flowRelationship'
import { registerSpecializationRelationshipLink } from './model/specializationRelationship'
import { registerAssociationRelationshipLink } from './model/associationRelationship'

import { registerViewConnectionLink } from './view/viewConnection'
import { registerViewRelationshipLink } from './view/viewRelationship'
import { registerViewAbstractLink } from './view/viewAbstractLink'

export const registerEdges = (diagramEngine) => {
 registerCompositionRelationshipLink(diagramEngine);
 registerAggregationRelationshipLink(diagramEngine);
 registerAssignmentRelationshipLink(diagramEngine);
 registerRealizationRelationshipLink(diagramEngine);
 registerServingRelationshipLink(diagramEngine);
 registerAccessRelationshipLink(diagramEngine);
 registerInfluenceRelationshipLink(diagramEngine);
 registerTriggeringRelationshipLink(diagramEngine);
 registerFlowRelationshipLink(diagramEngine);
 registerSpecializationRelationshipLink(diagramEngine);
 registerAssociationRelationshipLink(diagramEngine);
 registerViewConnectionLink(diagramEngine);
 registerViewRelationshipLink(diagramEngine);
  registerViewAbstractLink(diagramEngine);
}

