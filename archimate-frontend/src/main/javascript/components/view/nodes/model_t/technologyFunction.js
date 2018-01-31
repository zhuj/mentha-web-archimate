import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyFunction';

export class TechnologyFunctionWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_t technologyFunction'; }
}

