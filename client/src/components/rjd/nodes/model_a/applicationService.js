import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationService';

export class ApplicationServiceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationServiceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationServiceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationServiceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationServiceNodeWidgetFactory());
}

