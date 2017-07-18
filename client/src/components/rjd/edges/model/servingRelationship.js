import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='servingRelationship';

export class ServingRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class ServingRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <ServingRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerServingRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new ServingRelationshipLinkWidgetFactory());
}

