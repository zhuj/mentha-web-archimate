import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessEvent';

export class BusinessEventNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessEventNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessEventNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessEventNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessEventNodeWidgetFactory());
}

