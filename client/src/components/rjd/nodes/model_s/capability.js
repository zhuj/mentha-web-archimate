import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='capability';

export class CapabilityNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_s ' + this.props.node.nodeType; }
}

export class CapabilityNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <CapabilityNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerCapabilityNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new CapabilityNodeWidgetFactory());
}

