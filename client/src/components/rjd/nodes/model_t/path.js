import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='path';

export class PathNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class PathNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <PathNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerPathNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new PathNodeWidgetFactory());
}

