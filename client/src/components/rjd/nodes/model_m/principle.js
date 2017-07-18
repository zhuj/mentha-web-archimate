import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='principle';

export class PrincipleNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_m ' + this.props.node.nodeType; }
}

export class PrincipleNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <PrincipleNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerPrincipleNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new PrincipleNodeWidgetFactory());
}

