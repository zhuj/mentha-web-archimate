import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='orJunction';

export class OrJunctionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_x ' + this.props.node.nodeType; }
}

export class OrJunctionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <OrJunctionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerOrJunctionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new OrJunctionNodeWidgetFactory());
}

