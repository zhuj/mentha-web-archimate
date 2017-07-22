import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='facility';

export class FacilityWidget extends BaseNodeLikeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_p facility'; }
}

