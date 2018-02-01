import React from 'react'
import _ from 'lodash'

import { inputFocus, buildKeyPress } from '../../../utils/textarea'
import { allMeta } from '../../../meta/index'
import { DefaultNodeWidget } from '../diagram/DefaultWidgets'

class BaseNodeWidget extends DefaultNodeWidget {
  
  mkSetTitleCommand(node, title) {}
  renderTitle(node) {
    if (node.isSelectedForEdit()) { return this.renderEditTitle(node); }
    return super.renderTitle(node);
  }
  renderEditTitle(node) {
    const keyPress = buildKeyPress(
      (title) => {
        this.props.diagram.onChange({
          type: 'title-changed',
          title: title,
          items: [node],
          command: this.mkSetTitleCommand(node, title)
        });
        node.setSelected(true);
        this.forceUpdate();
      },
      () => {
        node.setSelected(true);
        this.forceUpdate();
      }
    );

    return (
      <div key="title-edit" className="title-edit">
          <textarea
            defaultValue={this.getTitle(node)}
            ref={inputFocus} onKeyDown={keyPress}
          />
      </div>
    );
  }
}

import * as api from '../../../actions/model.api'
export class ModelNodeWidget extends BaseNodeWidget {
  
  getTitle(node) { return this.props.conceptInfo.name; }
  mkSetTitleCommand(node, title) {
    return (viewId) => api.modConcept(node.viewObject.concept, {
      "name": title
    });
  }

  getHint(node) {
    if (!node) { return null; }

    let obj = node.viewObject;
    if (!obj) { return null; }

    obj = obj['.conceptInfo'];
    if (!obj) { return null; }

    const meta = allMeta[obj['_tp']];
    if (!!meta) {
      return `${meta['name']}: «${this.getTitle(node)}»\n * ${_.join(meta['help']['summ'], "\n * ")}`;
    }
    return `${obj['_tp']}: «${this.getTitle(node)}»`;
  }

}

export class ViewNodeWidget extends BaseNodeWidget {
  
}