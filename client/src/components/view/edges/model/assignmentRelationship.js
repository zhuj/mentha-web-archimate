import React from 'react'
import _ from 'lodash'

import { StructuralRelationshipsWidget } from '../BaseLinkWidget'

export const TYPE='assignmentRelationship';

export class AssignmentRelationshipWidget extends StructuralRelationshipsWidget {
  getClassName(link) { return TYPE; }
}

