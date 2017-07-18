import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='artifact';

export class ArtifactNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_t ' + this.props.node.nodeType; }
}

export class ArtifactNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ArtifactNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerArtifactNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ArtifactNodeWidgetFactory());
}

