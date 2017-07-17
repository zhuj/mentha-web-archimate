import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='specializationRelationship';
export class SpecializationRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class SpecializationRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new SpecializationRelationshipLinkModel(); }
}

export class SpecializationRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class SpecializationRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <SpecializationRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerSpecializationRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new SpecializationRelationshipLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new SpecializationRelationshipLinkInstanceFactory());
}

