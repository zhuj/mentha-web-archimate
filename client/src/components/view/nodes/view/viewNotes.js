import React from 'react'
import _ from 'lodash'

import { ViewNodeWidget } from '../BaseNodeWidget'

export const TYPE='viewNotes';

export class ViewNotesWidget extends ViewNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node  viewNotes'; }
  getName(node) { return node.viewObject.text; }
}

