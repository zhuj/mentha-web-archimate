import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='applicationInteraction';

export class ApplicationInteractionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_a ' + this.props.node.nodeType; }
}

export class ApplicationInteractionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ApplicationInteractionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerApplicationInteractionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ApplicationInteractionNodeWidgetFactory());
}

