import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from './BaseNodeWidget'


export class BaseNodeLikeWidget extends ModelNodeWidget {


  borderPath(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    // special thx to https://codepen.io/anthonydugois/pen/mewdyZ
    return (
      `M 1 7 `+
      `L 7 1 `+
      `L ${(w-1).toFixed(0)} 1 `+
      `L ${(w-1).toFixed(0)} ${(h-7).toFixed(0)} `+
      `L ${(w-7).toFixed(0)} ${(h-1).toFixed(0)} `+
      `L 1 ${(h-1).toFixed(0)} `+
      `L 1 7 `
    );
  }

  borderShape(node) {
    return shape("path", {d: this.borderPath(node) });
  }

  renderBorder(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    return (
      <svg className="border">
        <path d={this.borderPath(node)} stroke="black" strokeWidth={1}/>
        <path d={`M ${(w-7).toFixed(0)} 7 L 1 7`} stroke="black" strokeWidth={1}/>
        <path d={`M ${(w-7).toFixed(0)} 7 L ${(w-7).toFixed(0)} ${(h-1).toFixed(0)}`} stroke="black" strokeWidth={1}/>
        <path d={`M ${(w-7).toFixed(0)} 7 L ${(w-1).toFixed(0)} ${1}`} stroke="black" strokeWidth={1}/>
      </svg>
    );
  }
}


export class BaseRepresentationWidget extends ModelNodeWidget {


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

  // simplified version of borderPath for svg-intersection
  borderPath2(node) {
    const w = (node.width || 0);
    const h = (node.height || 0);
    return (
      `M 1 1 `+
      `L ${(w-1).toFixed(0)} 1 `+
      `L ${(w-1).toFixed(0)} ${(h-5).toFixed(0)} `+
      `L ${(w*0.75).toFixed(0)} ${(h-7).toFixed(0)} `+
      `L ${(w*0.50).toFixed(0)} ${(h-5).toFixed(0)} `+
      `L ${(w*0.25).toFixed(0)} ${(h-2).toFixed(0)} `+
      `L 1 ${(h-5).toFixed(0)} `+
      `L 1 1 `
    );
  }

  borderShape(node) {
    return shape("path", {d: this.borderPath2(node) });
  }

  renderBorder(node) {
    return (
      <svg className="border">
        <path d={this.borderPath(node)} stroke="black" strokeWidth={1}/>
      </svg>
    );
  }
}