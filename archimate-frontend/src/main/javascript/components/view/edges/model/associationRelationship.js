import React from 'react'
import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='associationRelationship';

export class AssociationRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
  drawTitle(link) {
    const conceptInfo = this.getConceptInfo(link);
    if (conceptInfo) { return this.drawTitleText(link, conceptInfo['predicate'], "middle", 0, "50%"); }
    return null;
  }
}

