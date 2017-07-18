import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='value';

export class ValueNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class ValueNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ValueNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerValueNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ValueNodeWidgetFactory());
}

