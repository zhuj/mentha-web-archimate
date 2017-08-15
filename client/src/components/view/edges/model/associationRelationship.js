import React from 'react'
import _ from 'lodash'

import { ModelLinkWidget } from '../BaseLinkWidget'

export const TYPE='associationRelationship';

export class AssociationRelationshipWidget extends ModelLinkWidget {
  getBaseClassName(link) { return TYPE; }
}

