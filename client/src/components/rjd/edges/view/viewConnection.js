import React from 'react'
import _ from 'lodash'

import { BaseLinkModel } from '../../base/BaseLinkModel'
import { BaseLinkWidget } from '../../base/BaseLinkWidget'
import * as RJD from '../../rjd'

export const TYPE='viewConnection';
export class ViewConnectionLinkModel extends BaseLinkModel {
  constructor(linkType = TYPE) { super(linkType); }
  getLinkType() { return TYPE; }
}

export class ViewConnectionLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() { super(TYPE); }
  getInstance() { return new ViewConnectionLinkModel(); }
}

export class ViewConnectionLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
}

export class ViewConnectionLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super(TYPE); }
  generateReactWidget(diagramEngine, link) {
    return (
        <ViewConnectionLinkWidget link={link} diagramEngine={diagramEngine} />
      );
  }
}

export const registerViewConnectionLink = (diagramEngine) => {
  diagramEngine.registerLinkFactory(new ViewConnectionLinkWidgetFactory());
  diagramEngine.registerInstanceFactory(new ViewConnectionLinkInstanceFactory());
}

