import React from 'react'
import _ from 'lodash'

import { BaseNodeModel } from '../../base/BaseNodeModel'
import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='viewNotes';

export class ViewNotesNodeModel extends BaseNodeModel {
  constructor(NodeType = TYPE) { super(NodeType); }
  defaultNodeType() { return TYPE; }
}

export class ViewNotesNodeInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ViewNotesNodeModel(); }
}

export class ViewNotesNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
}

export class ViewNotesNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <ViewNotesNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerViewNotesNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new ViewNotesNodeWidgetFactory());
  diagramEngine.registerInstanceFactory(new ViewNotesNodeInstanceFactory());
}

