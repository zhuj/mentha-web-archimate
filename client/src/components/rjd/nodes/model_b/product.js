import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='product';

export class ProductNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class ProductNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ProductNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerProductNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ProductNodeWidgetFactory());
}

