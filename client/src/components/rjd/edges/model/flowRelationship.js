import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='flowRelationship';
export class FlowRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class FlowRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new FlowRelationshipLinkModel(); }
}

export class FlowRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class FlowRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <FlowRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerFlowRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new FlowRelationshipLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new FlowRelationshipLinkInstanceFactory());
}

