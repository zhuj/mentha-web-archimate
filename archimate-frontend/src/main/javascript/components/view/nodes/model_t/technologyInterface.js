import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyInterface';

export class TechnologyInterfaceWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t technologyInterface'; }
}

