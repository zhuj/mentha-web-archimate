import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='equipment';

export class EquipmentWidget extends BaseNodeLikeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_p equipment'; }
}

