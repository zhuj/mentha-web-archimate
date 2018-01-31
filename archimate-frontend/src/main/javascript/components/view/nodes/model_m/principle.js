import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='principle';

export class PrincipleWidget extends BaseMotivationWidget {

  getClassName(node) { return 'a-node model_m principle'; }
}

