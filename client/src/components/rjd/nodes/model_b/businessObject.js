import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessObject';

export class BusinessObjectNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessObjectNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessObjectNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessObjectNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessObjectNodeWidgetFactory());
}

