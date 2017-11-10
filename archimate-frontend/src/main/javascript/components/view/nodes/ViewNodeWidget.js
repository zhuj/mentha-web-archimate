import React from 'react'

import { viewNodeConceptWidget } from './view/viewNodeConcept'
import { ViewNotesWidget } from './view/viewNotes'
import { ViewGroupWidget } from './view/viewGroup'

export const viewNodeWidget = (props) => {
  const { viewObject } = props.node;
  switch(viewObject['_tp']) {
    case 'viewNodeConcept': return viewNodeConceptWidget({ conceptInfo: viewObject['.conceptInfo'], ...props});
    case 'viewNotes': return (<ViewNotesWidget {...props}/>);
    case 'viewGroup': return (<ViewGroupWidget {...props}/>);
  }
};


