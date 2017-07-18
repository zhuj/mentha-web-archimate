import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='distributionNetwork';

export class DistributionNetworkNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_p ' + this.props.node.nodeType; }
}

export class DistributionNetworkNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <DistributionNetworkNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerDistributionNetworkNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new DistributionNetworkNodeWidgetFactory());
}

