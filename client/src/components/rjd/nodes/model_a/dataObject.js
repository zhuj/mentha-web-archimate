import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='dataObject';

export class DataObjectNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class DataObjectNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <DataObjectNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerDataObjectNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new DataObjectNodeWidgetFactory());
}

