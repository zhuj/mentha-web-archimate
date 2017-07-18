import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='implementationEvent';

export class ImplementationEventNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_i ' + this.props.node.nodeType; }
}

export class ImplementationEventNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ImplementationEventNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerImplementationEventNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ImplementationEventNodeWidgetFactory());
}

