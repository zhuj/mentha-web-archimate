import React from 'react'
import _ from 'lodash'

import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='compositionRelationship';

export class CompositionRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class CompositionRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <CompositionRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerCompositionRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new CompositionRelationshipLinkWidgetFactory());
}

