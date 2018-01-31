import React from 'react'
import { shape } from 'svg-intersections'
import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='andJunction';

export class AndJunctionWidget extends ModelNodeWidget {
  
  getClassName(node) { return 'a-node model_x andJunction'; }
  renderTitle(node) { return null; }
  borderShape(node) {
    const width = 0.5*(node.width || 0);
    const height = 0.5*(node.height || 0);
    return shape("ellipse", {cx:width, cy:height, rx:width, ry:height});
  }
}

