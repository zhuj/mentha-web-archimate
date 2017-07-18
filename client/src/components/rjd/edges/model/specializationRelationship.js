import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='specializationRelationship';

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
}

