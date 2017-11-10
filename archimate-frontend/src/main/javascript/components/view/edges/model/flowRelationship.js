import React from 'react'
import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='flowRelationship';

export class FlowRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
  drawTitle(link) {
    const conceptInfo = this.getConceptInfo(link);
    if (conceptInfo) { return this.drawTitleText(link, conceptInfo['flows'], "middle", 0, "50%"); }
    return null;
  }
}

