import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='aggregationRelationship';
export class AggregationRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class AggregationRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new AggregationRelationshipLinkModel(); }
}

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
  diagramEngine.registerInstanceFactory(new AggregationRelationshipLinkInstanceFactory());
}

