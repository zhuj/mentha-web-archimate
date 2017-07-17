import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import * as RJD from '../../rjd'

export const TYPE='viewRelationship';
export class ViewRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = 'default') {
    super(linkType);
    this.conceptInfo = {};
  }

  getLinkType() {
    return this.linkType;
  }

  deSerializeSource(id, edge) {
    return {
      ...super.deSerializeSource(id, edge),
      type: edge.conceptInfo['_tp'],
      conceptInfo: edge.conceptInfo
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

export class ViewRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ViewRelationshipLinkModel(); }
}

export const registerViewRelationshipLink = (diagramEngine) => {
  diagramEngine.registerInstanceFactory(new ViewRelationshipLinkInstanceFactory());
}

