import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationComponent';

export class ApplicationComponentNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationComponentNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationComponentNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationComponentNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationComponentNodeWidgetFactory());
}

