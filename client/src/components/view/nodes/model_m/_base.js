import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export class BaseMotivationWidget extends ModelNodeWidget {
  constructor(props) { super(props); }

  borderPath(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    // special thx to https://codepen.io/anthonydugois/pen/mewdyZ
    return (
      `M ${(1).toFixed(0)} ${(11).toFixed(0)} `+
      `L ${(11).toFixed(0)} ${(1).toFixed(0)} `+
      `L ${(w-11).toFixed(0)} ${(1).toFixed(0)} `+
      `L ${(w-1).toFixed(0)} ${(11).toFixed(0)} `+
      `L ${(w-1).toFixed(0)} ${(h-11).toFixed(0)} `+
      `L ${(w-11).toFixed(0)} ${(h-1).toFixed(0)} `+
      `L ${(11).toFixed(0)} ${(h-1).toFixed(0)} `+
      `L ${(1).toFixed(0)} ${(h-11).toFixed(0)} `+
      `L ${(1).toFixed(0)} ${(11).toFixed(0)} `
    );
  }

  borderShape(node) {
    return shape("path", {d: this.borderPath(node) });
  }

  renderBorder(node) {
    return (
      <svg className="border">
        <path d={this.borderPath(node)} stroke="black" strokeWidth={1}/>
      </svg>
    );
  }
}
