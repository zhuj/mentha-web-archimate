import React from 'react'
import _ from 'lodash'

import { ModelNodeWidget } from '../BaseNodeWidget'

export const TYPE='value';

import { intersect, shape } from 'svg-intersections'
const borderPoint = (node, p) => {
  /*if (false)*/ {
    try {
      const width = (node.width || 0)/2;
      const height = (node.height || 0)/2;

      const i = intersect(
        shape("ellipse", {cx:node.x, cy:node.y, rx:width, ry:height}),
        shape("line", {x1: node.x, y1: node.y, x2: p.x, y2: p.y})
      );

      return _.first(i.points) || node;

    } catch (exc) {
      console.error(exc);
    }
  }
  return node;
};



export class ValueWidget extends ModelNodeWidget {
  constructor(props) { super(props); }
  getClassName(node) { return 'a-node model_m value'; }

  borderPoint(node, p) {
    return borderPoint(node, p);
  }

  render() {
    const { node } = this.props;
    const width = (node.width || 0)/2;
    const height = (node.height || 0)/2;
    return (
      <div className={this.getClassName(node)}>
        {
          /*
          <svg className="border">
            <ellipse cx={width} cy={height} rx={width} ry={height}/>
          </svg>
          */
        }
        { this.renderTitle(node) }
        { this.renderPort(node) }
      </div>
    );
  }

}

