import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='meaning';

export class MeaningWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_m meaning'; }

  borderPath(node) {
    const w = (node.width || 0)/800.0;
    const h = (node.height || 0)/600.0;
    // special thx to https://codepen.io/anthonydugois/pen/mewdyZ
    return (
      `M ${w*50} ${h*250} `+
      `Q ${-w*50} ${-h*50} ${w*200} ${h*50} `+
      `Q ${w*300} ${-h*50} ${w*500} ${h*50} `+
      `Q ${w*900} ${-h*50} ${w*750} ${h*350} `+
      `Q ${w*850} ${h*600} ${w*600} ${h*550} `+
      `Q ${w*500} ${h*650} ${w*250} ${h*550} `+
      `Q ${-w*100} ${h*650} ${w*50} ${h*250}`
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

