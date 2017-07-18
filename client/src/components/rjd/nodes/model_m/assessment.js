import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='assessment';

export class AssessmentNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class AssessmentNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <AssessmentNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAssessmentNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new AssessmentNodeWidgetFactory());
}

