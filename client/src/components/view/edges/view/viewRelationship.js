import React from 'react'

import { CompositionRelationshipWidget } from '../model/compositionRelationship'
import { AggregationRelationshipWidget } from '../model/aggregationRelationship'
import { AssignmentRelationshipWidget } from '../model/assignmentRelationship'
import { RealizationRelationshipWidget } from '../model/realizationRelationship'
import { ServingRelationshipWidget } from '../model/servingRelationship'
import { AccessRelationshipWidget } from '../model/accessRelationship'
import { InfluenceRelationshipWidget } from '../model/influenceRelationship'
import { TriggeringRelationshipWidget } from '../model/triggeringRelationship'
import { FlowRelationshipWidget } from '../model/flowRelationship'
import { SpecializationRelationshipWidget } from '../model/specializationRelationship'
import { AssociationRelationshipWidget } from '../model/associationRelationship'

export const TYPE='viewRelationship';
export const viewRelationshipWidget = (props) => {
  const { conceptInfo } = props;
  switch(conceptInfo['_tp']) {
    case 'compositionRelationship': return (<CompositionRelationshipWidget {...props}/>);
    case 'aggregationRelationship': return (<AggregationRelationshipWidget {...props}/>);
    case 'assignmentRelationship': return (<AssignmentRelationshipWidget {...props}/>);
    case 'realizationRelationship': return (<RealizationRelationshipWidget {...props}/>);
    case 'servingRelationship': return (<ServingRelationshipWidget {...props}/>);
    case 'accessRelationship': return (<AccessRelationshipWidget {...props}/>);
    case 'influenceRelationship': return (<InfluenceRelationshipWidget {...props}/>);
    case 'triggeringRelationship': return (<TriggeringRelationshipWidget {...props}/>);
    case 'flowRelationship': return (<FlowRelationshipWidget {...props}/>);
    case 'specializationRelationship': return (<SpecializationRelationshipWidget {...props}/>);
    case 'associationRelationship': return (<AssociationRelationshipWidget {...props}/>);
  }
  return null;
};

