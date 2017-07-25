import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='andJunction';

export class AndJunctionWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_x andJunction'; }
  renderTitle(node) { return null; }
  borderShape(node) {
    const width = node.width || 0;
    const height = node.height || 0;
    return shape("ellipse", {cx:width/2, cy:height/2, rx:width/2, ry:height/2});
  }
}

