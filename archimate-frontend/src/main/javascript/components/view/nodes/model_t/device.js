import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

export const TYPE='device';

export class DeviceWidget extends BaseNodeLikeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_t device'; }
}

