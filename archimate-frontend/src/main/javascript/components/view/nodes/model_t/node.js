import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

export const TYPE='node';

export class NodeWidget extends BaseNodeLikeWidget {

  getClassName(node) { return 'a-node model_t node'; }
}
