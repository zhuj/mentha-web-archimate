import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='gap';

export class GapNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_i ' + this.props.node.nodeType; }
}

export class GapNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <GapNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerGapNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new GapNodeWidgetFactory());
}

