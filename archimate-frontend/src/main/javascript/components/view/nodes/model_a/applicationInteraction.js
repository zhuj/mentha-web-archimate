import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationInteraction';

export class ApplicationInteractionWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_a applicationInteraction'; }
}

