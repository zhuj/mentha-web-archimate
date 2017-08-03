import React from 'react'

import { ViewLinkWidget } from '../BaseLinkWidget'

export const TYPE='viewPrototypeLink';
export class ViewPrototypeLinkWidget extends ViewLinkWidget {
  getClassName(link) { return TYPE; }
}

