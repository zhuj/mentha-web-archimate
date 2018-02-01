import React from 'react'
import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='associationRelationship';

export class AssociationRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
  drawTitle(link) {
    const conceptInfo = this.getConceptInfo(link);
    if (conceptInfo) {
      // TODO: if (link.isSelectedForEdit()) { return this.renderEditTitle(link, 'predicate'); }
      return this.drawTitleText(link, conceptInfo['predicate'], "middle", 0, "50%");
    }
    return null;
  }
}

