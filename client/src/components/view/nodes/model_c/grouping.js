import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='grouping';

export class GroupingWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_c grouping'; }

  borderPath(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    // special thx to https://codepen.io/anthonydugois/pen/mewdyZ
    return (
      `M 1 1 `+
      `L ${(0.75*w).toFixed(0)} 1 `+
      `L ${(0.75*w).toFixed(0)} 18 `+
      `L ${(w-1).toFixed(0)} 18 `+
      `L ${(w-1).toFixed(0)} ${(h-1).toFixed(0)} `+
      `L 1 ${(h-1).toFixed(0)} `+
      `L 1 1 `
    );
  }

  borderShape(node) {
    return shape("path", {d: this.borderPath(node) });
  }

  render() {
    const { node } = this.props;
    const w = (node.width || 0);
    const h = (node.height || 0);
    return (
      <div className={this.getClassName(node)}>
        <svg className="border">
          <path d={this.borderPath(node)} stroke="black" strokeWidth={1}/>
          <path d={`M 1 18 L ${(0.75*w).toFixed(0)} 18`} stroke="black" strokeWidth={1}/>
        </svg>
        { this.renderTitle(node) }
        { this.renderPort(node) }
      </div>
    );
  }


}

