import React from 'react'

import { ViewNotesWidget } from './view/viewNotes'
import { viewNodeConceptWidget } from './view/viewNodeConcept'

export const viewNodeWidget = (props) => {
  const { viewObject } = props.node;
  switch(viewObject['_tp']) {
    case 'viewNodeConcept': return viewNodeConceptWidget({ conceptInfo: viewObject['.conceptInfo'], ...props});
    case 'viewNotes': return (<ViewNotesWidget {...props}/>);
  }
};


