import React from 'react'
import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='influenceRelationship';

export class InfluenceRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
  drawTitle(link) {
    const conceptInfo = this.getConceptInfo(link);
    if (conceptInfo) {
      // TODO: if (link.isSelectedForEdit()) { return this.renderEditTitle(link, 'influence'); }
      const text = conceptInfo['influence'];
      if (text) {
        let className = null;
        if (text.startsWith("+")) { className = "plus"; }
        else if (text.startsWith("-")) { className = "minus"; }
        return this.drawTitleText(link, text, "middle", 0, "75%", className);
      }
    }
    return null;
  }
}

