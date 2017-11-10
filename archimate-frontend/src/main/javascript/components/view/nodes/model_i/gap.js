import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='gap';

export class GapWidget extends BaseRepresentationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_i gap'; }
}


