import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='associationRelationship';
export class AssociationRelationshipLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class AssociationRelationshipLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new AssociationRelationshipLinkModel(); }
}

export class AssociationRelationshipLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class AssociationRelationshipLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <AssociationRelationshipLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerAssociationRelationshipLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new AssociationRelationshipLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new AssociationRelationshipLinkInstanceFactory());
}

