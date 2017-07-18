import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='andJunction';

export class AndJunctionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_x ' + this.props.node.nodeType; }
}

export class AndJunctionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <AndJunctionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAndJunctionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new AndJunctionNodeWidgetFactory());
}

