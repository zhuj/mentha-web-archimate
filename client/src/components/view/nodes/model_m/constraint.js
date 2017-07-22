import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='constraint';

export class ConstraintWidget extends BaseMotivationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_m constraint'; }
}

