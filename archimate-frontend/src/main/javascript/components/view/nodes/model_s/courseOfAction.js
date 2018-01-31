import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='courseOfAction';

export class CourseOfActionWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_s courseOfAction'; }
}

