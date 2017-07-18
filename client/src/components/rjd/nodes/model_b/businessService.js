import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessService';

export class BusinessServiceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessServiceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessServiceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessServiceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessServiceNodeWidgetFactory());
}

