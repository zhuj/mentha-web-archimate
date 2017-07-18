import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='stakeholder';

export class StakeholderNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class StakeholderNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <StakeholderNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerStakeholderNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new StakeholderNodeWidgetFactory());
}

