import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='outcome';

export class OutcomeNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class OutcomeNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <OutcomeNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerOutcomeNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new OutcomeNodeWidgetFactory());
}

