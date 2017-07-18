import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='deliverable';

export class DeliverableNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_i ' + this.props.node.nodeType; }
}

export class DeliverableNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <DeliverableNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerDeliverableNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new DeliverableNodeWidgetFactory());
}

