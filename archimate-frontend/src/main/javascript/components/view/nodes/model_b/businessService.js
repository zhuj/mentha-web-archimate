import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessService';

export class BusinessServiceWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessService'; }
}

