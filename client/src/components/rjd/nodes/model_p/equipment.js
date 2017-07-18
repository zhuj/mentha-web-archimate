import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='equipment';

export class EquipmentNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_p ' + this.props.node.nodeType; }
}

export class EquipmentNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <EquipmentNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerEquipmentNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new EquipmentNodeWidgetFactory());
}

