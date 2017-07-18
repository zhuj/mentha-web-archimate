import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='requirement';

export class RequirementNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class RequirementNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <RequirementNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerRequirementNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new RequirementNodeWidgetFactory());
}

