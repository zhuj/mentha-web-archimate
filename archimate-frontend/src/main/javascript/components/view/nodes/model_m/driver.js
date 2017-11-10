import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='driver';

export class DriverWidget extends BaseMotivationWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_m driver'; }
}

