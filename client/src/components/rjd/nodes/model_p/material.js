import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='material';

export class MaterialNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_p ' + this.props.node.nodeType; }
}

export class MaterialNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <MaterialNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerMaterialNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new MaterialNodeWidgetFactory());
}

