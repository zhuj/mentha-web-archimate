import React from 'react'
import { BaseNodeLikeWidget } from '../_base'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='systemSoftware';

export class SystemSoftwareWidget extends BaseNodeLikeWidget {

  getClassName(node) { return 'a-node model_t systemSoftware'; }
}

