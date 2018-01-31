import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='artifact';

export class ArtifactWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t artifact'; }
}

