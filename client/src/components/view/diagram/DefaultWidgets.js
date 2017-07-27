import React from 'react'
import _ from 'lodash'

import { intersect, shape } from 'svg-intersections'

export class DefaultNodeWidget extends React.Component {
  constructor(props) { super(props); }

  borderShape(node) {
    const width = node.width || 0;
    const height = node.height || 0;
    return shape("rect", {x:0, y:0, width, height});
  }

  borderPoint(node, border, p) {
    /*if (false)*/ {
      try {
        const width = node.width || 0;
        const height = node.height || 0;
        const x = node.x - width / 2;
        const y = node.y - height / 2;
        const i = intersect(
          border,
          shape("line", {x1: node.x-x, y1: node.y-y, x2: p.x-x, y2: p.y-y})
        );
        let first = _.first(i.points);
        if (!!first) {
          return { x: first.x+x, y: first.y+y };
        }
      } catch (exc) {
        console.error(exc);
      }
    }
    return node;
  }

  updateLinkPoints(node) {
    const border = this.borderShape(node);
    _.forEach(node.getLinks(), (link) => {
      const simple = (link.getPoints().length <= 2);
      if (link.sourceNode === node) {
        const firstPoint = link.getFirstPoint();
        const targetPoint = (simple && !!link.targetNode) ? link.targetNode : link.getPoint(1 /* next to the first*/);
        const point = this.borderPoint(node, border, targetPoint);
        firstPoint.updateLocation(point);
      }
      if (link.targetNode === node) {
        const lastPoint = link.getLastPoint();
        const sourcePoint = (simple && !!link.sourceNode) ? link.sourceNode : link.getPoint(-2 /*one before the last*/);
        const point = this.borderPoint(node, border, sourcePoint);
        lastPoint.updateLocation(point);
      }
    });
  }

  componentWillReceiveProps(nextProps) {
    const { node } = nextProps;
    this.updateLinkPoints(node);
  }

  // componentWillUpdate() {
  //   const { node } = this.props;
  //   this.updateLinkPoints(node);
  // }

  getClassName(node) {
    return "basic-node " + node.nodeType;
  }

  getName(node) {
    return node.name;
  }

  renderTitle(node) {
    return (
      <div className='title'>
        <div className='name'>
          {this.getName(node)}
        </div>
      </div>
    )
  }

  renderBorder(node) {
    return null;
  }

  render() {
    const { node } = this.props;
    return (
      <div className={this.getClassName(node)}>
        { this.renderTitle(node) }
        { this.renderBorder(node) }
      </div>
    );
  }

}

const useSmooth = true;
export class DefaultLinkWidget extends React.Component {

  constructor(props) {
    super(props);
  }

  generatePoint(link, index) {
    const point = link.points[index];
    const uiCircleProps = {
      className: `p${(point.isSelected() ? ' selected' : '')}`,
      cx: point.x,
      cy: point.y,
      r: 5,
    };
    const circleProps = {
      className: 'x-point t',
      'data-linkid': link.id,
      'data-index': index,
      cx: point.x,
      cy: point.y,
      r: 15,
      opacity: 0
    };

    return (
      <g key={`point-${index}`} className="x-point">
        <circle {...uiCircleProps}/>
        <circle {...circleProps}/>
      </g>
    );
  }

  generateLink(first, last, extraProps) {
    const {link} = this.props;
    const selected = link.isSelected();
    const uiPathProps = {
      className: `p${(selected ? ' selected' : '')}`,
      d: extraProps.d
    };
    const pathProps = {
      ... extraProps,
      className: 'x-link t',
      'data-linkid': link.id,
      strokeOpacity: (selected ? 0.1 : 0)
    };

    const className = 'x-link ' + ((first ? 'first' : '') + ' ' + (last ? 'last': '')).trim();
    return (
      <g key={`link-${extraProps.id}`} className={className}>
        <path {...uiPathProps}/>
        <path {...pathProps}/>
      </g>
    );
  }

  drawSmoothPaths() {
    const { link, diagram } = this.props;
    const selected  = link.isSelected();
    const { points } = link;

    let paths = [];

    let path = "";

    path = path + `M ${points[0].x.toFixed(0)} ${points[0].y.toFixed(0)} `;

    let last = points.length - 1;
    for (let i = 0; i < last; i++) {
      const x = (points[i].x + points[i + 1].x) / 2;
      const y = (points[i].y + points[i + 1].y) / 2;

      if (i > 0) {
        path = path + ` Q ${points[i].x.toFixed(0)} ${points[i].y.toFixed(0)} ${x.toFixed(0)} ${y.toFixed(0)}`;
      }

      if (selected) {
        paths.push(
          <g key={`point-j-${i}`} className="x-point-j">
            <circle
              className="p"
              cx={x} cy={y} r={3}
            />
            <circle
              className="t"
              cx={x} cy={y} r={15} opacity={0}
              onMouseDown={(event) => {
                // TODO: make it better
                if (event.buttons !== 1) { return; } // only the left button

                if (!event.shiftKey) {
                  const point = diagram.addPointIntoLink.bind(diagram)(event, link, i);
                  point.setSelected(true);
                  this.forceUpdate();
                }
              }}
            />
          </g>
        );
      }
    }

    path = path + ` L ${points[last].x.toFixed(0)} ${points[last].y.toFixed(0)}`;

    return [
      this.generateLink(true, true, {
        id: `${link.id}-path`,
        d: path,
        'data-linkid': link.id,
        'data-index': 'path'
      }),
      ... paths
    ];
  }

  drawSimplePaths() {
    const { link, diagram } = this.props;
    const { points } = link;

    const ds = [];
    for (let i = 0; i < points.length - 1; i++) {
      ds.push(` M ${points[i].x} ${points[i].y} L ${points[i + 1].x} ${points[i + 1].y}`);
    }

    const dsFirst = 0, dsLast = ds.length-1;
    return ds.map((data, index) => this.generateLink((dsFirst == index), (dsLast == index), {
      id: `${link.id}-${index}`,
      d: data,
      'data-linkid': link.id,
      'data-index': index,
      onMouseDown: event => {
        // TODO: make it better
        if (event.buttons !== 1) { return; } // only the left button

        if (!event.shiftKey) {
          const point = diagram.addPointIntoLink.bind(diagram)(event, link, index);
          point.setSelected(true);
          this.forceUpdate();
        }
      }
    }));
  }

  drawLine() {
    const { link } = this.props;
    const { points } = link;

    // render the line
    const smooth = useSmooth; // && points.length > 2;
    const paths = (smooth ? this.drawSmoothPaths : this.drawSimplePaths).bind(this)();

    // Render the circles for points (except the first and the last)
    if (link.isSelected()) {
      for (let i = 1, l = points.length - 1; i < l; i++) {
        paths.push(this.generatePoint(link, i));
      }
    }

    // render free point (if exists)
    if (link.targetNode === null) {
      paths.push(this.generatePoint(link, points.length - 1));
    }

    return paths;
  }

  getClassName(link) {
    return link.linkType;
  }

  render() {
    const {link} = this.props;
    return (
      <g className={this.getClassName(link)}>
        {this.drawLine()}
      </g>
    );
  }

}

