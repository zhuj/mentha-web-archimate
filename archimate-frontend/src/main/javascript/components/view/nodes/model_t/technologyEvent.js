import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyEvent';

export class TechnologyEventWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t technologyEvent'; }
}

