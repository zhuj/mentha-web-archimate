import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyInteraction';

export class TechnologyInteractionWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t technologyInteraction'; }
}

