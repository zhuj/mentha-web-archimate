import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='goal';

export class GoalWidget extends BaseMotivationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_m goal'; }
}

