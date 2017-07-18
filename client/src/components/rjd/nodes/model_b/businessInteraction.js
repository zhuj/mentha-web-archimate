import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessInteraction';

export class BusinessInteractionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessInteractionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessInteractionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessInteractionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessInteractionNodeWidgetFactory());
}

