import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='location';

export class LocationNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_c ' + this.props.node.nodeType; }
}

export class LocationNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <LocationNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerLocationNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new LocationNodeWidgetFactory());
}

