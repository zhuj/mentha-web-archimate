import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='contract';

export class ContractNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class ContractNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ContractNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerContractNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ContractNodeWidgetFactory());
}

