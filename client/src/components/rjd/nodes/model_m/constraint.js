import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='constraint';

export class ConstraintNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class ConstraintNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ConstraintNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerConstraintNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ConstraintNodeWidgetFactory());
}

