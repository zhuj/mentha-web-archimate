import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='workPackage';

export class WorkPackageNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_i ' + this.props.node.nodeType; }
}

export class WorkPackageNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <WorkPackageNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerWorkPackageNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new WorkPackageNodeWidgetFactory());
}

