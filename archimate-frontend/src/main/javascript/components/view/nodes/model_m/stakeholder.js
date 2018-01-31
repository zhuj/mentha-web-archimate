import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='stakeholder';

export class StakeholderWidget extends BaseMotivationWidget {

  getClassName(node) { return 'a-node model_m stakeholder'; }
}

