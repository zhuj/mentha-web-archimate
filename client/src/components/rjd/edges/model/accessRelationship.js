import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='accessRelationship';
export class AccessRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class AccessRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new AccessRelationshipLinkModel(); }
}

export class AccessRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class AccessRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <AccessRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAccessRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new AccessRelationshipLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new AccessRelationshipLinkInstanceFactory());
}

