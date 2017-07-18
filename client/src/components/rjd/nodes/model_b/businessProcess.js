import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessProcess';

export class BusinessProcessNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessProcessNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessProcessNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessProcessNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessProcessNodeWidgetFactory());
}

