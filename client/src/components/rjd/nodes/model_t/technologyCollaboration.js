import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyCollaboration';

export class TechnologyCollaborationNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyCollaborationNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyCollaborationNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyCollaborationNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyCollaborationNodeWidgetFactory());
}

