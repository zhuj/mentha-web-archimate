import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyInteraction';

export class TechnologyInteractionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyInteractionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyInteractionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyInteractionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyInteractionNodeWidgetFactory());
}

