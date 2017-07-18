import React from 'react'
import _ from 'lodash'

import { BaseNodeModel } from '../../base/BaseNodeModel'
import * as RJD from '../../rjd'

export const TYPE='viewNodeConcept';

export class ViewNodeConceptNodeModel extends BaseNodeModel {
  constructor(NodeType = TYPE) {
    super(NodeType);
    this.conceptInfo = {};
  }

  defaultNodeType() { return TYPE; }
  getNodeName() { return this.conceptInfo['name']; }

  deSerializeSource(id, node) {
    return { ... super.deSerializeSource(id, node),
      type: node.conceptInfo['_tp'],
      conceptInfo: node.conceptInfo
    };
  }

  serialize() {
    return _.merge(super.serialize(), {
      conceptInfo: this.conceptInfo
    });
  }

  deSerialize(data) {
    super.deSerialize(data);
    this.conceptInfo = data.conceptInfo;
  }

}

export class ViewNodeConceptNodeInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ViewNodeConceptNodeModel(); }
}


export const registerViewNodeConceptNode = (diagramEngine) => {
  diagramEngine.registerInstanceFactory(new ViewNodeConceptNodeInstanceFactory());
}

