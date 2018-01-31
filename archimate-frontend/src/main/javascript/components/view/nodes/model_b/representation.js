import React from 'react'
import { BaseRepresentationWidget } from '../_base'

export const TYPE='representation';

export class RepresentationWidget extends BaseRepresentationWidget {
  
  getClassName(node) { return 'a-node model_b representation'; }
}

