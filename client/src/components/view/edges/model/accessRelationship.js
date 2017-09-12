import React from 'react'
import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='accessRelationship';

export class AccessRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) {
    switch (this.props.conceptInfo['access']) {
      case "r": return TYPE + " r";
      case "w": return TYPE + " w";
      case "rw": return TYPE + " r w";
    }
    return TYPE;
  }
  // drawTitle(link) {
  //   const conceptInfo = this.getConceptInfo(link);
  //   if (conceptInfo) {
  //     const fullTitle = (a) => {
  //       switch (a) {
  //         case "r": return "reads";
  //         case "w": return "writes";
  //         case "rw": return "reads and writes";
  //       }
  //       return a;
  //     };
  //     return this.drawTitleText(link, fullTitle(conceptInfo['access']), "middle", 0, "50%");
  //   }
  //   return null;
  // }
}

