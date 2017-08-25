import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='representation';

export class RepresentationWidget extends BaseRepresentationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_b representation'; }
}

