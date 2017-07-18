import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='assignmentRelationship';

export class AssignmentRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class AssignmentRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <AssignmentRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAssignmentRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new AssignmentRelationshipLinkWidgetFactory());
}

