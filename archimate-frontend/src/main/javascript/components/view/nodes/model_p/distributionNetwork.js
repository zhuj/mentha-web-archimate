import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='distributionNetwork';

export class DistributionNetworkWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_p distributionNetwork'; }
}

