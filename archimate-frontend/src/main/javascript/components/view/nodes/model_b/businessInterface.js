import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessInterface';

export class BusinessInterfaceWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_b businessInterface'; }
}

