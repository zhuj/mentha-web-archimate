import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='servingRelationship';
export class ServingRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class ServingRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ServingRelationshipLinkModel(); }
}

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
  diagramEngine.registerInstanceFactory(new ServingRelationshipLinkInstanceFactory());
}

