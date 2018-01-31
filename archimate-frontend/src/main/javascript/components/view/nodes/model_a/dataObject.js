import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='dataObject';

export class DataObjectWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a dataObject'; }
}

