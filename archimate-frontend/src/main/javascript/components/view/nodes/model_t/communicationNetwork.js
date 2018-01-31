import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='communicationNetwork';

export class CommunicationNetworkWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_t communicationNetwork'; }
}

