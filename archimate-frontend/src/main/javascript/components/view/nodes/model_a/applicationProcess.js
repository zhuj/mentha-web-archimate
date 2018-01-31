import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationProcess';

export class ApplicationProcessWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_a applicationProcess'; }
}

