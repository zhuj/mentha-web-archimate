import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='systemSoftware';

export class SystemSoftwareNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class SystemSoftwareNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <SystemSoftwareNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerSystemSoftwareNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new SystemSoftwareNodeWidgetFactory());
}

