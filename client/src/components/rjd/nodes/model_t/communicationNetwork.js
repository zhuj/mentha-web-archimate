import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='communicationNetwork';

export class CommunicationNetworkNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class CommunicationNetworkNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <CommunicationNetworkNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerCommunicationNetworkNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new CommunicationNetworkNodeWidgetFactory());
}

