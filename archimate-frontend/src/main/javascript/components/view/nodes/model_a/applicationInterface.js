import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationInterface';

export class ApplicationInterfaceWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a applicationInterface'; }
}

