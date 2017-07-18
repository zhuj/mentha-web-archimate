import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationProcess';

export class ApplicationProcessNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationProcessNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationProcessNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationProcessNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationProcessNodeWidgetFactory());
}

