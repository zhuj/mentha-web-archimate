import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='outcome';

export class OutcomeWidget extends BaseMotivationWidget {
  
  getClassName(node) { return 'a-node model_m outcome'; }
}

