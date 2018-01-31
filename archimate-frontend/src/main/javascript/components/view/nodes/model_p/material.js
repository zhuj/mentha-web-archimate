import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='material';

export class MaterialWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_p material'; }
}

