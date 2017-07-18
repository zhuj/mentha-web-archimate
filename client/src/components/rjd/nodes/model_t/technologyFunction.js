import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyFunction';

export class TechnologyFunctionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyFunctionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyFunctionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyFunctionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyFunctionNodeWidgetFactory());
}

