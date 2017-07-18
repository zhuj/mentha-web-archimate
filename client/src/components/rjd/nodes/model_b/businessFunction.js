import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessFunction';

export class BusinessFunctionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessFunctionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessFunctionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessFunctionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessFunctionNodeWidgetFactory());
}

