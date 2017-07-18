import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessInterface';

export class BusinessInterfaceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessInterfaceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessInterfaceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessInterfaceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessInterfaceNodeWidgetFactory());
}

