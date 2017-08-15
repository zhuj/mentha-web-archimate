import React from 'react'
import _ from 'lodash'

import { StructuralRelationshipsWidget } from '../BaseLinkWidget'

export const TYPE='realizationRelationship';

export class RealizationRelationshipWidget extends StructuralRelationshipsWidget {
  getBaseClassName(link) { return TYPE; }
}

