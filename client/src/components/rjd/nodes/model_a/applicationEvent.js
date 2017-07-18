import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationEvent';

export class ApplicationEventNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationEventNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationEventNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationEventNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationEventNodeWidgetFactory());
}

