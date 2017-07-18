import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='influenceRelationship';

export class InfluenceRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class InfluenceRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <InfluenceRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerInfluenceRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new InfluenceRelationshipLinkWidgetFactory());
}

