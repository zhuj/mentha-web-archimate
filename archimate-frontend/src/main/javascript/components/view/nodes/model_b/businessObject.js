import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessObject';

export class BusinessObjectWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessObject'; }
}

