import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='flowRelationship';

export class FlowRelationshipWidget extends ModelLinkWidget {
  getClassName(link) { return TYPE; }
}

