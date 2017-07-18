import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='businessRole';

export class BusinessRoleNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_b ' + this.props.node.nodeType; }
}

export class BusinessRoleNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <BusinessRoleNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerBusinessRoleNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new BusinessRoleNodeWidgetFactory());
}

