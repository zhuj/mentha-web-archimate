import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='influenceRelationship';

export class InfluenceRelationshipWidget extends ModelLinkWidget {
  getClassName(link) { return TYPE; }
}

