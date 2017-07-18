import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationFunction';

export class ApplicationFunctionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationFunctionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationFunctionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationFunctionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationFunctionNodeWidgetFactory());
}

