import React from 'react'
import _ from 'lodash'

import { BaseNodeWidget } from '../../base/BaseNodeWidget'
import * as RJD from '../../rjd'

export const TYPE='courseOfAction';

export class CourseOfActionNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getNodeClassName() { return 'a-node model_s ' + this.props.node.nodeType; }
}

export class CourseOfActionNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, node) {
    return (
        <CourseOfActionNodeWidget node={node} diagramEngine={diagramEngine} />
      );
  }
}

export const registerCourseOfActionNode = (diagramEngine) => {
  diagramEngine.registerNodeFactory(new CourseOfActionNodeWidgetFactory());
}

