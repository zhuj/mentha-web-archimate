import React from 'react'
import _ from 'lodash'

import { ViewNodeWidget } from '../BaseNodeWidget'

export const TYPE='viewNotes';

import * as api from '../../../../actions/model.api'
export class ViewNotesWidget extends ViewNodeWidget {
  
  getClassName(node) { return 'a-node viewNotes'; }
  getTitle(node) { return node.viewObject.text; }
  mkSetTitleCommand(node, title) {
    return (viewId) => api.modViewObject(viewId, node.id, {
      "text": title
    });
  }
}

