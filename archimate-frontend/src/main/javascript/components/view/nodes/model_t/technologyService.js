import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyService';

export class TechnologyServiceWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t technologyService'; }
}

