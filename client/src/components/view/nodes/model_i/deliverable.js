import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='deliverable';

export class DeliverableWidget extends BaseRepresentationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_i deliverable'; }
}


