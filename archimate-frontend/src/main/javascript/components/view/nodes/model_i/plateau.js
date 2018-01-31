import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

export const TYPE='plateau';

export class PlateauWidget extends BaseNodeLikeWidget {
  
  getClassName(node) { return 'a-node model_i plateau'; }
}

