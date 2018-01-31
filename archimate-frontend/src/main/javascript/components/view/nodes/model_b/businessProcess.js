import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessProcess';

export class BusinessProcessWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessProcess'; }
}

