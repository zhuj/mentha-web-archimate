import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='node';

export class NodeNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class NodeNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <NodeNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerNodeNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new NodeNodeWidgetFactory());
}

