import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='businessActor';

export class BusinessActorWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_b businessActor'; }
}

