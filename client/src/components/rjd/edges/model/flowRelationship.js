import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='flowRelationship';

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
}

