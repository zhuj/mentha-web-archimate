import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='implementationEvent';

export class ImplementationEventWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_i implementationEvent'; }
}

