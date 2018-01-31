import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationService';

export class ApplicationServiceWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a applicationService'; }
}

