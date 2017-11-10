import React from 'react'

import { ViewConnectionWidget } from './view/viewConnection'
import { ViewPrototypeLinkWidget } from './view/viewPrototype'
import { viewRelationshipWidget } from './view/viewRelationship'

export const viewEdgeWidget = (props) => {
  const { viewObject } = props.link;
  if (!viewObject) {
    return (<ViewPrototypeLinkWidget {...props}/>)
  } else {
    switch (viewObject['_tp']) {
      case 'viewRelationship': return viewRelationshipWidget({ conceptInfo: viewObject['.conceptInfo'], ...props});
      case 'viewConnection': return (<ViewConnectionWidget {...props}/>);
    }
  }
};

