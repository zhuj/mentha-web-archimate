import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='representation';

export class RepresentationNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class RepresentationNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <RepresentationNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerRepresentationNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new RepresentationNodeWidgetFactory());
}

