import React from 'react'
import { BaseMotivationWidget } from './_base'

export const TYPE='assessment';

export class AssessmentWidget extends BaseMotivationWidget {

  getClassName(node) { return 'a-node model_m assessment'; }
}

