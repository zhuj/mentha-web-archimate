import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='assignmentRelationship';
export class AssignmentRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class AssignmentRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new AssignmentRelationshipLinkModel(); }
}

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
  diagramEngine.registerInstanceFactory(new AssignmentRelationshipLinkInstanceFactory());
}

