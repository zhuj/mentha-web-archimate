import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyInterface';

export class TechnologyInterfaceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyInterfaceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyInterfaceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyInterfaceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyInterfaceNodeWidgetFactory());
}

