import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='accessRelationship';

export class AccessRelationshipWidget extends ModelLinkWidget {
  getClassName(link) {
    switch (this.props.conceptInfo['access']) {
      case "r": return TYPE + " r";
      case "w": return TYPE + " w";
      case "rw": return TYPE + " r w";
    }
    return TYPE;
  }
}

