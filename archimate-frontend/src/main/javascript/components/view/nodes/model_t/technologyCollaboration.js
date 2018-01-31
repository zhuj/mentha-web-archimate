import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='technologyCollaboration';

export class TechnologyCollaborationWidget extends ModelNodeWidget {

  getClassName(node) { return 'a-node model_t technologyCollaboration'; }
}

