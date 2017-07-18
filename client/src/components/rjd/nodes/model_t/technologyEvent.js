import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyEvent';

export class TechnologyEventNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyEventNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyEventNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyEventNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyEventNodeWidgetFactory());
}

