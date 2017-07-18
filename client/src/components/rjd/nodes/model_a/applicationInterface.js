import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationInterface';

export class ApplicationInterfaceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationInterfaceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationInterfaceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationInterfaceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationInterfaceNodeWidgetFactory());
}

