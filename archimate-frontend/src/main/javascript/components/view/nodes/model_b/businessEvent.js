import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessEvent';

export class BusinessEventWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessEvent'; }
}

