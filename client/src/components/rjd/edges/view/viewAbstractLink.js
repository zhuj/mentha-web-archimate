import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='viewAbstractLink';

export class ViewAbstractLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  defaultLinkType() { return TYPE; }
}

export class ViewAbstractLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ViewAbstractLinkModel(); }
}

export class ViewAbstractLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class ViewAbstractLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
      <ViewAbstractLinkWidget link={link} diagramEngine={diagramEngine} />
    );
  }
}

export const registerViewAbstractLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new ViewAbstractLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new ViewAbstractLinkInstanceFactory());
}

