import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessCollaboration';

export class BusinessCollaborationNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessCollaborationNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessCollaborationNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessCollaborationNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessCollaborationNodeWidgetFactory());
}

