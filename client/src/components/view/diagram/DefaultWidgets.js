import React from 'react'
import _ from 'lodash'

import { intersect, shape } from 'svg-intersections'

export class PortWidget extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: false
    };
  }
  render() {
    const { selected } = this.state;
    const { node } = this.props;
    return (
      <div
        className={`port${(selected ? ' selected' : '')}`}
        onMouseEnter={() => this.setState({ selected: true })}
        onMouseLeave={() => this.setState({ selected: false })}
        data-id={node.id}
        data-nodeid={node.id}
      />
    );
  }
}

export class DefaultNodeWidget extends React.Component {

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

  renderPort(node) {
    return (
      <div className='ports'>
        <div className='center'>
          {/*<PortWidget node={node} key={`port-${node.id}`} />*/}
        </div>
      </div>
    )
  }

  render() {
    const { node } = this.props;
    return (
      <div className={this.getClassName(node)}>
        { this.renderTitle(node) }
        { this.renderPort(node) }
      </div>
    );
  }

}

export class DefaultLinkWidget extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      selected: false
    };
  }

  generatePoint(link, index) {
    const point = link.points[index];
    const uiCircleProps = {
      className: `point pointui${(point.isSelected() ? ' selected' : '')}`,
      cx: point.x,
      cy: point.y,
      r: 5,
    };
    const circleProps = {
      className: 'point',
      'data-linkid': link.id,
      'data-index': index,
      cx: point.x,
      cy: point.y,
      r: 15,
      opacity: 0,
      onMouseLeave: () => this.setState({ selected: false }),
      onMouseEnter: () => this.setState({ selected: true }),
    };

    return (
      <g key={`point-${index}`}>
        <circle {...uiCircleProps}/>
        <circle {...circleProps}/>
      </g>
    );
  }

  generateLink(first, last, extraProps) {
    const {link} = this.props;
    const {selected} = this.state;
    const bottom = (
      <path
        className={((selected || link.isSelected()) ? 'selected' : '')}
        {...extraProps}
      />
    );

    const top = (
      <path
        strokeLinecap={'round'}
        data-linkid={link.id}
        strokeOpacity={selected ? 0.1 : 0}
        strokeWidth={20}
        onMouseLeave={() => this.setState({selected: false})}
        onMouseEnter={() => this.setState({selected: true})}
        onContextMenu={event => {
          event.preventDefault();
          // TODO:
        }}
        {...extraProps}
      />
    );

    const className = ((first ? 'first' : '') + ' ' + (last ? 'last': '')).trim();
    return (
      <g key={`link-${extraProps.id}`} className={className}>
        {bottom}
        {top}
      </g>
    );
  }

  drawAdvancedLine() {
    const smooth = false;
    const { link, diagram } = this.props;

    const { points } = link;
    const ds = [];

    if (smooth) {
      ds.push(
        ` M${points[0].x} ${points[0].y} C ${points[0].x + 50} ${points[0].y} ${points[1].x} ${points[1].y} ${points[1].x} ${points[1].y}` // eslint-disable-line
      );

      let i;
      for (i = 1; i < points.length - 2; i++) {
        ds.push(` M ${points[i].x} ${points[i].y} L ${points[i + 1].x} ${points[i + 1].y}`);
      }

      ds.push(
        ` M${points[i].x} ${points[i].y} C ${points[i].x} ${points[i].y} ${points[i + 1].x - 50} ${points[i + 1].y} ${points[i + 1].x} ${points[i + 1].y}` // eslint-disable-line
      );
    } else {
      for (let i = 0; i < points.length - 1; i++) {
        ds.push(` M ${points[i].x} ${points[i].y} L ${points[i + 1].x} ${points[i + 1].y}`);
      }
    }

    const dsFirst = 0, dsLast = ds.length-1;
    const paths = ds.map((data, index) => this.generateLink((dsFirst == index), (dsLast == index), {
      id: index,
      d: data,
      'data-linkid': link.id,
      'data-index': index,
      onMouseDown: event => {
        if (!event.shiftKey) {
          const point = diagram.addPointIntoLink.bind(diagram)(event, link, index);
          point.setSelected(true);
          this.forceUpdate();
        }
      }
    }));

    // Render the circles
    for (let i=1, l=points.length-1; i < l; i++) {
      paths.push(this.generatePoint(link, i));
    }

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
        {this.drawAdvancedLine()}
      </g>
    );
  }

}

