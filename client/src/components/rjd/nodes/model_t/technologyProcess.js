import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyProcess';

export class TechnologyProcessNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyProcessNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyProcessNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyProcessNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyProcessNodeWidgetFactory());
}

