import React from 'react'
import _ from 'lodash'

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
}

export class ViewNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
}