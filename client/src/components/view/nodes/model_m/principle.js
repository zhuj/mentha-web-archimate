import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='principle';

export class PrincipleWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_m principle'; }
}

