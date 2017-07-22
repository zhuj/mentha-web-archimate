import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='applicationComponent';

export class ApplicationComponentWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_a applicationComponent'; }

  render() {
    const { node } = this.props;
    return (
      <div className={this.getClassName(node)}>
        <div className="x1"/>
        <div className="x2"/>
        { this.renderTitle(node) }
        { this.renderPort(node) }
      </div>
    );
  }
}

