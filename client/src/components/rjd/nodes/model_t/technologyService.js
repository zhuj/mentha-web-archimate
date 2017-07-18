import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='technologyService';

export class TechnologyServiceNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class TechnologyServiceNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <TechnologyServiceNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTechnologyServiceNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new TechnologyServiceNodeWidgetFactory());
}

