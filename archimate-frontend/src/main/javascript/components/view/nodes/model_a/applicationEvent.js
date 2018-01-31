import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationEvent';

export class ApplicationEventWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a applicationEvent'; }
}

