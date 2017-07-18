import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='aggregationRelationship';

export class AggregationRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class AggregationRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <AggregationRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAggregationRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new AggregationRelationshipLinkWidgetFactory());
}

