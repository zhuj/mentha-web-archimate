import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='facility';

export class FacilityNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_p ' + this.props.node.nodeType; }
}

export class FacilityNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <FacilityNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerFacilityNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new FacilityNodeWidgetFactory());
}

