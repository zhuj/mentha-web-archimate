import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='device';

export class DeviceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class DeviceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <DeviceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerDeviceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new DeviceNodeWidgetFactory());
}

