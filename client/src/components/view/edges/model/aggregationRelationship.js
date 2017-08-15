import React from 'react'
import _ from 'lodash'

import { StructuralRelationshipsWidget } from '../BaseLinkWidget'

export const TYPE='aggregationRelationship';

export class AggregationRelationshipWidget extends StructuralRelationshipsWidget {
  getBaseClassName(link) { return TYPE; }
}

