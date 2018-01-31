import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='path';

export class PathWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t path'; }
}

