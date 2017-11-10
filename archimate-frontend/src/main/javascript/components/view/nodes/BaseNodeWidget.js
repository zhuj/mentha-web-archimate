import React from 'react'
import _ from 'lodash'

import { allMeta } from '../../../meta/index'
import { DefaultNodeWidget } from '../diagram/DefaultWidgets'

class BaseNodeWidget extends DefaultNodeWidget {
  constructor(props) { super(props); }
  mkSetTitleCommand(node, title) {}
  renderTitle(node) {
    if (node.selected === 2) {
      const select = (input) => {
        if (input) {
          input.select();
          input.focus();
        }
      };
      const keyPress = (event) => {
        const keyCode = event.keyCode;
        if (keyCode === 27) {
          event.stopPropagation();
          node.setSelected(true);
          this.forceUpdate();
        } else if (keyCode === 13) {
          const title = event.target.value;
          this.props.diagram.onChange({
            type: 'title-changed',
            title: title,
            items: [ node ],
            command: this.mkSetTitleCommand(node, title)
          });
          event.stopPropagation();
          node.setSelected(true);
          this.forceUpdate();
        }
      };

      return (
        <div key="title-edit" className="title-edit">
          <textarea
            defaultValue={this.getTitle(node)}
            ref={select} onKeyDown={keyPress}
          />
        </div>
      );
    }
    return super.renderTitle(node);
  }
}

import * as api from '../../../actions/model.api'
export class ModelNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
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
  constructor(props) { super(props); }
}