import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationCollaboration';

export class ApplicationCollaborationNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationCollaborationNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationCollaborationNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationCollaborationNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationCollaborationNodeWidgetFactory());
}

