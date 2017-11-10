import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='servingRelationship';

export class ServingRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
}

