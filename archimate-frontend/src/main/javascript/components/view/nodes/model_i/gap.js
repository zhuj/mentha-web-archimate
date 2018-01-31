import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='gap';

export class GapWidget extends BaseRepresentationWidget {

  getClassName(node) { return 'a-node model_i gap'; }
}


