import React from 'react'

import { ViewLinkWidget } from '../BaseLinkWidget'

export const TYPE='viewConnection';
export class ViewConnectionWidget extends ViewLinkWidget {
  getClassName(link) { return 'viewConnection'; }
}

