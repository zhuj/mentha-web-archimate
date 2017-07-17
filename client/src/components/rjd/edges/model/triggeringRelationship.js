import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='triggeringRelationship';
export class TriggeringRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class TriggeringRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new TriggeringRelationshipLinkModel(); }
}

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
  diagramEngine.registerInstanceFactory(new TriggeringRelationshipLinkInstanceFactory());
}

