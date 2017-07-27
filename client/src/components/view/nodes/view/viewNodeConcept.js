import React from 'react'
import _ from 'lodash'

import { GroupingWidget } from '../model_c/grouping.js'
import { LocationWidget } from '../model_c/location.js'
import { ApplicationComponentWidget } from '../model_a/applicationComponent.js'
import { ApplicationCollaborationWidget } from '../model_a/applicationCollaboration.js'
import { ApplicationInterfaceWidget } from '../model_a/applicationInterface.js'
import { ApplicationFunctionWidget } from '../model_a/applicationFunction.js'
import { ApplicationInteractionWidget } from '../model_a/applicationInteraction.js'
import { ApplicationProcessWidget } from '../model_a/applicationProcess.js'
import { ApplicationEventWidget } from '../model_a/applicationEvent.js'
import { ApplicationServiceWidget } from '../model_a/applicationService.js'
import { DataObjectWidget } from '../model_a/dataObject.js'
import { BusinessActorWidget } from '../model_b/businessActor.js'
import { BusinessRoleWidget } from '../model_b/businessRole.js'
import { BusinessCollaborationWidget } from '../model_b/businessCollaboration.js'
import { BusinessInterfaceWidget } from '../model_b/businessInterface.js'
import { BusinessProcessWidget } from '../model_b/businessProcess.js'
import { BusinessFunctionWidget } from '../model_b/businessFunction.js'
import { BusinessInteractionWidget } from '../model_b/businessInteraction.js'
import { BusinessEventWidget } from '../model_b/businessEvent.js'
import { BusinessServiceWidget } from '../model_b/businessService.js'
import { BusinessObjectWidget } from '../model_b/businessObject.js'
import { ContractWidget } from '../model_b/contract.js'
import { RepresentationWidget } from '../model_b/representation.js'
import { ProductWidget } from '../model_b/product.js'
import { WorkPackageWidget } from '../model_i/workPackage.js'
import { DeliverableWidget } from '../model_i/deliverable.js'
import { ImplementationEventWidget } from '../model_i/implementationEvent.js'
import { PlateauWidget } from '../model_i/plateau.js'
import { GapWidget } from '../model_i/gap.js'
import { StakeholderWidget } from '../model_m/stakeholder.js'
import { DriverWidget } from '../model_m/driver.js'
import { AssessmentWidget } from '../model_m/assessment.js'
import { GoalWidget } from '../model_m/goal.js'
import { OutcomeWidget } from '../model_m/outcome.js'
import { PrincipleWidget } from '../model_m/principle.js'
import { RequirementWidget } from '../model_m/requirement.js'
import { ConstraintWidget } from '../model_m/constraint.js'
import { MeaningWidget } from '../model_m/meaning.js'
import { ValueWidget } from '../model_m/value.js'
import { EquipmentWidget } from '../model_p/equipment.js'
import { FacilityWidget } from '../model_p/facility.js'
import { DistributionNetworkWidget } from '../model_p/distributionNetwork.js'
import { MaterialWidget } from '../model_p/material.js'
import { ResourceWidget } from '../model_s/resource.js'
import { CapabilityWidget } from '../model_s/capability.js'
import { CourseOfActionWidget } from '../model_s/courseOfAction.js'
import { NodeWidget } from '../model_t/node.js'
import { DeviceWidget } from '../model_t/device.js'
import { SystemSoftwareWidget } from '../model_t/systemSoftware.js'
import { TechnologyCollaborationWidget } from '../model_t/technologyCollaboration.js'
import { TechnologyInterfaceWidget } from '../model_t/technologyInterface.js'
import { PathWidget } from '../model_t/path.js'
import { CommunicationNetworkWidget } from '../model_t/communicationNetwork.js'
import { TechnologyFunctionWidget } from '../model_t/technologyFunction.js'
import { TechnologyProcessWidget } from '../model_t/technologyProcess.js'
import { TechnologyInteractionWidget } from '../model_t/technologyInteraction.js'
import { TechnologyEventWidget } from '../model_t/technologyEvent.js'
import { TechnologyServiceWidget } from '../model_t/technologyService.js'
import { ArtifactWidget } from '../model_t/artifact.js'
import { AndJunctionWidget } from '../model_x/andJunction.js'
import { OrJunctionWidget } from '../model_x/orJunction.js'

export const TYPE='viewNodeConcept';
export const viewNodeConceptWidget = (props) => {
  const { conceptInfo } = props;
  switch(conceptInfo['_tp']) {
    case 'grouping': return (<GroupingWidget {...props}/>);
    case 'location': return (<LocationWidget {...props}/>);
    case 'applicationComponent': return (<ApplicationComponentWidget {...props}/>);
    case 'applicationCollaboration': return (<ApplicationCollaborationWidget {...props}/>);
    case 'applicationInterface': return (<ApplicationInterfaceWidget {...props}/>);
    case 'applicationFunction': return (<ApplicationFunctionWidget {...props}/>);
    case 'applicationInteraction': return (<ApplicationInteractionWidget {...props}/>);
    case 'applicationProcess': return (<ApplicationProcessWidget {...props}/>);
    case 'applicationEvent': return (<ApplicationEventWidget {...props}/>);
    case 'applicationService': return (<ApplicationServiceWidget {...props}/>);
    case 'dataObject': return (<DataObjectWidget {...props}/>);
    case 'businessActor': return (<BusinessActorWidget {...props}/>);
    case 'businessRole': return (<BusinessRoleWidget {...props}/>);
    case 'businessCollaboration': return (<BusinessCollaborationWidget {...props}/>);
    case 'businessInterface': return (<BusinessInterfaceWidget {...props}/>);
    case 'businessProcess': return (<BusinessProcessWidget {...props}/>);
    case 'businessFunction': return (<BusinessFunctionWidget {...props}/>);
    case 'businessInteraction': return (<BusinessInteractionWidget {...props}/>);
    case 'businessEvent': return (<BusinessEventWidget {...props}/>);
    case 'businessService': return (<BusinessServiceWidget {...props}/>);
    case 'businessObject': return (<BusinessObjectWidget {...props}/>);
    case 'contract': return (<ContractWidget {...props}/>);
    case 'representation': return (<RepresentationWidget {...props}/>);
    case 'product': return (<ProductWidget {...props}/>);
    case 'workPackage': return (<WorkPackageWidget {...props}/>);
    case 'deliverable': return (<DeliverableWidget {...props}/>);
    case 'implementationEvent': return (<ImplementationEventWidget {...props}/>);
    case 'plateau': return (<PlateauWidget {...props}/>);
    case 'gap': return (<GapWidget {...props}/>);
    case 'stakeholder': return (<StakeholderWidget {...props}/>);
    case 'driver': return (<DriverWidget {...props}/>);
    case 'assessment': return (<AssessmentWidget {...props}/>);
    case 'goal': return (<GoalWidget {...props}/>);
    case 'outcome': return (<OutcomeWidget {...props}/>);
    case 'principle': return (<PrincipleWidget {...props}/>);
    case 'requirement': return (<RequirementWidget {...props}/>);
    case 'constraint': return (<ConstraintWidget {...props}/>);
    case 'meaning': return (<MeaningWidget {...props}/>);
    case 'value': return (<ValueWidget {...props}/>);
    case 'equipment': return (<EquipmentWidget {...props}/>);
    case 'facility': return (<FacilityWidget {...props}/>);
    case 'distributionNetwork': return (<DistributionNetworkWidget {...props}/>);
    case 'material': return (<MaterialWidget {...props}/>);
    case 'resource': return (<ResourceWidget {...props}/>);
    case 'capability': return (<CapabilityWidget {...props}/>);
    case 'courseOfAction': return (<CourseOfActionWidget {...props}/>);
    case 'node': return (<NodeWidget {...props}/>);
    case 'device': return (<DeviceWidget {...props}/>);
    case 'systemSoftware': return (<SystemSoftwareWidget {...props}/>);
    case 'technologyCollaboration': return (<TechnologyCollaborationWidget {...props}/>);
    case 'technologyInterface': return (<TechnologyInterfaceWidget {...props}/>);
    case 'path': return (<PathWidget {...props}/>);
    case 'communicationNetwork': return (<CommunicationNetworkWidget {...props}/>);
    case 'technologyFunction': return (<TechnologyFunctionWidget {...props}/>);
    case 'technologyProcess': return (<TechnologyProcessWidget {...props}/>);
    case 'technologyInteraction': return (<TechnologyInteractionWidget {...props}/>);
    case 'technologyEvent': return (<TechnologyEventWidget {...props}/>);
    case 'technologyService': return (<TechnologyServiceWidget {...props}/>);
    case 'artifact': return (<ArtifactWidget {...props}/>);
    case 'andJunction': return (<AndJunctionWidget {...props}/>);
    case 'orJunction': return (<OrJunctionWidget {...props}/>);
  }
  return null;
};

export const layerElements = {
  'motivationLayer':['stakeholder', 'driver', 'assessment', 'goal', 'outcome', 'principle', 'requirement', 'constraint', 'meaning', 'value'],
  'strategyLayer':['resource', 'capability', 'courseOfAction'],
  'businessLayer':['businessActor', 'businessRole', 'businessCollaboration', 'businessInterface', 'businessProcess', 'businessFunction', 'businessInteraction', 'businessEvent', 'businessService', 'businessObject', 'contract', 'representation', 'product'],
  'applicationLayer':['applicationComponent', 'applicationCollaboration', 'applicationInterface', 'applicationFunction', 'applicationInteraction', 'applicationProcess', 'applicationEvent', 'applicationService', 'dataObject'],
  'technologyLayer':['node', 'device', 'systemSoftware', 'technologyCollaboration', 'technologyInterface', 'path', 'communicationNetwork', 'technologyFunction', 'technologyProcess', 'technologyInteraction', 'technologyEvent', 'technologyService', 'artifact'],
  'physicalLayer':['equipment', 'facility', 'distributionNetwork', 'material'],
  'implementationLayer':['workPackage', 'deliverable', 'implementationEvent', 'plateau', 'gap'],
  'compositionLayer':['grouping', 'location'],
};