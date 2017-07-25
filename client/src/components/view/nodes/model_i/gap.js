import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='gap';

export class GapWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_i gap'; }

  borderPath(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    // special thx to https://codepen.io/anthonydugois/pen/mewdyZ
    return (
      `M 1 1 `+
      `L ${(w-1).toFixed(0)} 1 `+
      `L ${(w-1).toFixed(0)} ${(h-5).toFixed(0)} `+
      `Q ${(w*0.75).toFixed(0)} ${(h-9).toFixed(0)} ${(w*0.50).toFixed(0)} ${(h-5).toFixed(0)} `+
      `Q ${(w*0.25).toFixed(0)} ${(h).toFixed(0)} 1 ${(h-5).toFixed(0)} `+
      `L 1 1 `
    );
  }

  // TODO: make 'svg-intersections' work with Q-paths
  // borderShape(node) {
  //   return shape("path", {d: this.borderPath(node) });
  // }

  renderBorder(node) {
    return (
      <svg className="border">
        <path d={this.borderPath(node)} stroke="black" strokeWidth={1}/>
      </svg>
    );
  }
}


