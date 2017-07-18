import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='goal';

export class GoalNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class GoalNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <GoalNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerGoalNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new GoalNodeWidgetFactory());
}

