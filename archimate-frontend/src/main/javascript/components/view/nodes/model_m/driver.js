import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='driver';

export class DriverWidget extends BaseMotivationWidget {
  
  getClassName(node) { return 'a-node model_m driver'; }
}

