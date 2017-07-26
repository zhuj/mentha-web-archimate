import React from 'react'
import { connect } from 'react-redux'
import { DropTarget } from 'react-dnd'

import reactLS from 'react-localstorage'

import _ from 'lodash'

import * as actions from '../../actions/index'

import { DiagramWidget } from './diagram/DiagramWidget'
import * as models from './diagram/models'

import { viewNodeWidget } from './nodes/ViewNodeWidget'
import { viewEdgeWidget } from './edges/ViewEdgeWidget'

import './diagram.sass.scss'


const nodesTarget = {
  drop(props, monitor, component) {
    console.log(monitor.getItem());
    // const {x: pageX, y: pageY} = monitor.getSourceClientOffset();
    // const {left = 0, top = 0} = component.diagramEngine.canvas.getBoundingClientRect();
    // const diagramModel = component.diagramEngine.getDiagramModel();
    // const {offsetX, offsetY} = diagramModel;
    // const x = pageX - left - offsetX;
    // const y = pageY - top - offsetY;
    // const item = monitor.getItem();

    // let node;
    // if (item.type === 'output') {
    //   node = new OutputNodeModel('Output Node');
    // }
    // if (item.type === 'input') {
    //   node = new InputNodeModel('Input Node');
    // }
    // if (item.type === 'connection') {
    //   node = new ConnectionNodeModel('Connection Node', item.color);
    // }

    // node.x = x;
    // node.y = y;
    // diagramModel.addNode(node);
    // props.updateModel(diagramModel.serializeDiagram());
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
      case 'node-moved': {
        let vo = action.model;
        this.props.updateViewNodePosAndSize(this.props.id)(vo.id, vo, vo);
        break;
      }
      case 'node-sized': {
        let vo = action.model;
        this.props.updateViewNodePosAndSize(this.props.id)(vo.id, vo, vo);
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
    linkModel.setPoints([ sourceNode, ...edge.points, targetNode]);
  });

  return { id, diagramModel  }
};

const mapDispatchToProps = (dispatch) => ({
  updateViewNodePosAndSize: (viewId) => (voId, pos, size) => dispatch(actions.updateViewNodePosAndSize(viewId, voId, { x:pos.x, y:pos.y }, { width: size.width, height: size.height })),
  updateViewEdgePoints: (viewId) => (voId, points) => dispatch(actions.updateViewEdgePoints(viewId, voId, points))
});


export default connect(mapStateToProps, mapDispatchToProps)(ViewDiagram)
