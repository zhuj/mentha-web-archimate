import React from 'react'
import _ from 'lodash'

import { ViewLinkWidget } from '../BaseLinkWidget'

export const TYPE='viewPrototypeLink';

export class ViewPrototypeLinkWidget extends ViewLinkWidget {
  getClassName(link) { return TYPE; }
}

