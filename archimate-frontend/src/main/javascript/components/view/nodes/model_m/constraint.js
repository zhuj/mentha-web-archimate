import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='constraint';

export class ConstraintWidget extends BaseMotivationWidget {
  
  getClassName(node) { return 'a-node model_m constraint'; }
}

