import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationCollaboration';

export class ApplicationCollaborationWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a applicationCollaboration'; }
}

