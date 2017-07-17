import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='realizationRelationship';
export class RealizationRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class RealizationRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new RealizationRelationshipLinkModel(); }
}

export class RealizationRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class RealizationRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <RealizationRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerRealizationRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new RealizationRelationshipLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new RealizationRelationshipLinkInstanceFactory());
}

