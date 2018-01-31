import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='product';

export class ProductWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b product'; }
}

