import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='plateau';

export class PlateauNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_i ' + this.props.node.nodeType; }
}

export class PlateauNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <PlateauNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerPlateauNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new PlateauNodeWidgetFactory());
}

