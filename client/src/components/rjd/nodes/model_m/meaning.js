import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='meaning';

export class MeaningNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class MeaningNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <MeaningNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerMeaningNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new MeaningNodeWidgetFactory());
}

