import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessActor';

export class BusinessActorNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessActorNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessActorNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessActorNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessActorNodeWidgetFactory());
}

