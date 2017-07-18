import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='realizationRelationship';

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
}

