import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyProcess';

export class TechnologyProcessWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t technologyProcess'; }
}

