import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='capability';

export class CapabilityWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_s capability'; }
}

