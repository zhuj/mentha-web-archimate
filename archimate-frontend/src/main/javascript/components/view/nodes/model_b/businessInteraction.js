import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessInteraction';

export class BusinessInteractionWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_b businessInteraction'; }
}

