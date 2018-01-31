import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessFunction';

export class BusinessFunctionWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessFunction'; }
}

