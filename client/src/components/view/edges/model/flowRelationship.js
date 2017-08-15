import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='flowRelationship';

export class FlowRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
}

