import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='deliverable';

export class DeliverableWidget extends BaseRepresentationWidget {

  getClassName(node) { return 'a-node model_i deliverable'; }
}


