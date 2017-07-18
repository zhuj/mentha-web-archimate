import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='triggeringRelationship';

export class TriggeringRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class TriggeringRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <TriggeringRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerTriggeringRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new TriggeringRelationshipLinkWidgetFactory());
}

