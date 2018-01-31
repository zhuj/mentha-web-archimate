import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='contract';

export class ContractWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_b contract'; }
}

