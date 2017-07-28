import React from 'react'
import { connect } from 'react-redux'
import { DropTarget } from 'react-dnd'

import reactLS from 'react-localstorage'

import _ from 'lodash'

import * as actions from '../../actions/index'
import * as api from '../../actions/model.api'

import { DiagramWidget } from './diagram/DiagramWidget'
import * as models from './diagram/models'

import { viewNodeWidget } from './nodes/ViewNodeWidget'
import { viewEdgeWidget } from './edges/ViewEdgeWidget'

import './diagram.sass.scss'

const nodesTarget = {
  drop(props, monitor, component) {
    const item = monitor.getItem();

    const srcClientOffset = monitor.getSourceClientOffset();
    const internal = component.getInternalMousePoint({
      clientX: srcClientOffset.x,
      clientY: srcClientOffset.y
    });

    const diagramModel = component.getDiagramModel();

    const conceptInfo = { _tp: item['tp'], name: '' };
    const viewObject = {  _tp: 'viewNodeConcept', name: '', conceptInfo };

    const W = 120, H = 40;
    const node = diagramModel.addNode(
      Object.assign(new models.NodeModel(models.generateId()), {
        x:internal.x+W/2, y:internal.y+H/2, width: W, height: H,
        zIndex: 999, // place above all the nodes
        viewObject
      })
    );

    console.log(node);
  }
};

@DropTarget('node-source', nodesTarget, (connect, monitor) => ({
  connectDropTarget: connect.dropTarget(),
  isOver: monitor.isOver(),
  canDrop: monitor.canDrop()
}))
class ViewDiagram extends DiagramWidget {

  /* @override: react-localstorage */
  getLocalStorageKey() {
    const { id } = this.props;
    return `view-diagram-${id}`;
  }

  /* @override: react-localstorage */
  getStateFilterKeys() {
    return ["zoom", "offset"];
  }

  componentWillUpdate(nextProps, nextState) {
    reactLS.componentWillUpdate.bind(this)(nextProps, nextState);
    if (!!super.componentWillUpdate) { super.componentWillUpdate(nextProps, nextState); }
  }

  componentDidMount() {
    reactLS.componentDidMount.bind(this)();
    if (!!super.componentDidMount) { return super.componentDidMount(); }
  }

  generateWidgetForNode(props) {
    return viewNodeWidget({ diagram: this, ...props });
  }

  generateWidgetForLink(props) {
    return viewEdgeWidget({ diagram: this, ...props});
  }

  /* @overide: DiagramWidget */
  onChange(action) {
    // update the rest
    switch (action.type) {
      case 'node-sized':
      case 'node-moved':
      case 'items-sized':
      case 'items-moved': {
        const viewId = this.props.id;
        this.props.sendModelCommands(
          _.chain(action.items)
            .map((vo) => {
              if (vo instanceof models.NodeModel) {
                return api.moveViewNode(viewId, vo.id, vo, vo);
              }
              if (vo instanceof models.LinkModel) {
                return api.moveViewEdge(viewId, vo.id, _.slice(vo.points, 1, vo.points.length-1));
              }
            })
            .filter((command) => command !== null)
            .value()
        );
        break;
      }
    }
  }

  render() {
    const { connectDropTarget } = this.props;
    return connectDropTarget(
      <div className='diagram-root'>
        <div className='parent-container'>
          <div className='diagram-drop-container'>
            { super.render() }
          </div>
        </div>
      </div>
    );
  }
}

const buildNodeZIndexMap = (view) => {
  return _.chain(view.nodes)
    .entries(view.nodes)
    .sortBy((e) => {
      const { width: w, height: h } = e[1].size;
      return -(w*h);
    })
    .reduce((o, e, idx) => { o[e[0]] = idx; return o; }, {})
    .value();
};

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id;
  const model = state.model;
  const view = model.views[id];

  // sort nodes by z-index...
  const zIndexMap = buildNodeZIndexMap(view);

  const diagramModel = new models.DiagramModel(view.id);

  // nodes
  _.forEach(view.nodes, (node, id) => {
    diagramModel.addNode(
      Object.assign(new models.NodeModel(id), {
        nodeType: node['_tp'],
        x: node.pos.x,
        y: node.pos.y,
        width: node.size.width,
        height: node.size.height,
        zIndex: zIndexMap[id] || 0,
        viewObject: node
      })
    );
  });

  // edges
  _.forEach(view.edges, (edge, id) => {
    const sourceNode = diagramModel.getNode(edge.src);
    const targetNode = diagramModel.getNode(edge.dst);
    const linkModel = diagramModel.addLink(
      Object.assign(new models.LinkModel(id), {
        linkType: edge['_tp'],
        viewObject: edge
      })
    );
    linkModel.setSourceNode(sourceNode);
    linkModel.setTargetNode(targetNode);
    linkModel.setPoints([sourceNode, ...edge.points, targetNode]);
  });

  // loops
  _.forEach(diagramModel.getNodes(), (node) => {
    let d = 1;
    _.forEach(node.getLinks(), (link) => {
      if ((link.sourceNode === link.targetNode) && link.points.length <= 2) {
        const { x, y, height } = link.sourceNode;
        const deep = 1 + (0.25 * d++);
        const points = [
          { x: x - 0.45*height*deep**2, y: y + 0.75*height*deep**0.5 },
          { x: x,                       y: y + 0.95*height*deep },
          { x: x + 0.45*height*deep**2, y: y + 0.75*height*deep**0.5 },
        ];
        link.setPoints([link.sourceNode, ...points, link.targetNode]);
      }
    });
  });

  return { id, diagramModel };
};

const mapDispatchToProps = (dispatch) => ({
  sendModelCommands: (commands) => dispatch(actions.sendModelMessage(api.composite(commands)))
  //updateViewNodePosAndSize: (viewId) => (voId, pos, size) => dispatch(actions.updateViewNodePosAndSize(viewId, voId, { x:pos.x, y:pos.y }, { width: size.width, height: size.height })),
  //updateViewEdgePoints: (viewId) => (voId, points) => dispatch(actions.updateViewEdgePoints(viewId, voId, _.chain(points).slice(1, points.length-1).map(p=>({ x:p.x, y:p.y })).value()))
});


export default connect(mapStateToProps, mapDispatchToProps)(ViewDiagram)
