import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='driver';

export class DriverNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class DriverNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <DriverNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerDriverNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new DriverNodeWidgetFactory());
}

