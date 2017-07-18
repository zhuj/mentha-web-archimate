import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='grouping';

export class GroupingNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_c ' + this.props.node.nodeType; }
}

export class GroupingNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <GroupingNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerGroupingNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new GroupingNodeWidgetFactory());
}

