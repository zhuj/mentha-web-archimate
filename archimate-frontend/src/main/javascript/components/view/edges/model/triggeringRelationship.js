import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='triggeringRelationship';

export class TriggeringRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
}

