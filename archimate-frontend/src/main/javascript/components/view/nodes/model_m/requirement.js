import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='requirement';

export class RequirementWidget extends BaseMotivationWidget {
  
  getClassName(node) { return 'a-node model_m requirement'; }
}

