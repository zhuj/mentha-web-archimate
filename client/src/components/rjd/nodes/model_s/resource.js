import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='resource';

export class ResourceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_s ' + this.props.node.nodeType; }
}

export class ResourceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ResourceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerResourceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ResourceNodeWidgetFactory());
}

