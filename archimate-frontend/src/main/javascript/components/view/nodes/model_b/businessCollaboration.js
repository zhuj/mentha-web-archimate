import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessCollaboration';

export class BusinessCollaborationWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessCollaboration'; }
}

