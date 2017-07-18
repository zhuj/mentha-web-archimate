import React from 'react'
import { connect } from 'react-redux'
import { DropTarget } from 'react-dnd'

import _ from 'lodash'
import * as RJD from './rjd'

import diagramEngineBuilder from './engine'
import { PORT_NAME } from './base/BasePortModel'
import * as actions from '../../actions/index'

import './rjd.sass.scss'


const nodesTarget = {
  drop(props, monitor, component) {
    const {x: pageX, y: pageY} = monitor.getSourceClientOffset();
    const {left = 0, top = 0} = component.diagramEngine.canvas.getBoundingClientRect();
    const diagramModel = component.diagramEngine.getDiagramModel();
    const {offsetX, offsetY} = diagramModel;
    const x = pageX - left - offsetX;
    const y = pageY - top - offsetY;
    const item = monitor.getItem();

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

    node.x = x;
    node.y = y;
    diagramModel.addNode(node);
    props.updateModel(diagramModel.serializeDiagram());
  }
};

@DropTarget('node-source', nodesTarget, (connect, monitor) => ({
  connectDropTarget: connect.dropTarget(),
  isOver: monitor.isOver(),
  canDrop: monitor.canDrop()
}))
class ViewRJD extends React.Component {

  constructor(props) {
    super(props);
    this.diagramEngine = diagramEngineBuilder(null);
    this.setupDiagramModel({});
  }

  componentWillMount() {
    const { view } = this.props;
    this.setupDiagramModel(view);
  }

  componentWillReceiveProps(nextProps) {
    if (!_.isEqual(this.props['view'], nextProps['view'])) {
      this.setupDiagramModel(nextProps['view']);
    }
  }

  setupDiagramModel(view) {
    // TODO: merge instead of re-create
    this.diagramEngine.setDiagramModel(new RJD.DiagramModel());

    {
      const { rjd } = this.props;
      if (!!rjd) {
        const diaModel = this.diagramEngine.getDiagramModel();
        diaModel.offsetX = rjd.offsetX || 0;
        diaModel.offsetY = rjd.offsetY || 0;
        diaModel.zoom = rjd.zoom || 100;
      }
    }


    const deserialize = function(view, diagramEngine) {
      this.deSerialize({id: view.id});

      // Deserialize nodes
      _.map(view.nodes, (node, id) => {
        const nodeOb = diagramEngine.getInstanceFactory(node['_tp']).getInstance();
        nodeOb.deSerializeViewNode(id, node);

        const portOb = diagramEngine.getInstanceFactory('BasePortModel').getInstance();
        portOb.deSerializeViewNode(id, node);

        this.addNode(nodeOb);
      });

      // Attach ports
      _.map(view.edges, (edge, id) => {
        const linkOb = diagramEngine.getInstanceFactory(edge['_tp']).getInstance();
        linkOb.deSerializeViewEdge(id, edge);

        if (edge.src) {
          const node = this.getNode(edge.src);
          linkOb.setSourcePort(node.getPort(PORT_NAME));
          linkOb.getFirstPoint().updateLocation({x: node.x, y: node.y});
        }

        if (edge.dst) {
          const node = this.getNode(edge.dst);
          linkOb.setTargetPort(node.getPort(PORT_NAME));
          linkOb.getLastPoint().updateLocation({x: node.x, y: node.y});
        }

        this.addLink(linkOb);
      });
    };

    deserialize.bind(this.diagramEngine.getDiagramModel())(view, this.diagramEngine);
  }

  onChange(model, action) {

    const updateModel = this.props.updateModel(this.props.id, {});
    //
    // // Ignore some events
    // if (['items-copied'].indexOf(action.type) !== -1) {
    //   return;
    // }
    //
    // // Check for single selected items
    // if (['node-selected', 'node-moved'].indexOf(action.type) !== -1) {
    //   //FIXME: return updateModel({selectedNode: action.model});
    // }
    //
    // // Check for canvas events
    // const deselectEvts = ['canvas-click', 'canvas-drag', 'items-selected', 'items-drag-selected', 'items-moved'];
    // if (deselectEvts.indexOf(action.type) !== -1) {
    //   //FIXME: return updateModel({selectedNode: null});
    // }
    //
    // // Check if this is a deselection and a single node exists
    // const isDeselect = ['node-deselected', 'link-deselected'].indexOf(action.type) !== -1;
    // if (isDeselect && action.items.length < 1 && action.model.nodeType) {
    //   //FIXME: return updateModel({selectedNode: action.model});
    // }

    updateModel({
      offsetX: model.offsetX,
      offsetY: model.offsetY,
      zoom: model.zoom
    });

    // update the rest
    switch (action.type) {
      case 'node-moved': {
        let model = action.model;
        this.props.updateViewNodePosition(this.props.id)(model.id, { x: model.x, y: model.y });
        break;
      }
    }

  }

  render() {
    const {connectDropTarget} = this.props;

    // Render the canvas
    return connectDropTarget(
      <div className='diagram-root'>
      <div className='parent-container'>
      <div className='diagram-drop-container'>
        <RJD.DiagramWidget diagramEngine={this.diagramEngine} onChange={this.onChange.bind(this)}/>
      </div>
      </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id;
  const model = state.model || {};
  const view = model.views[id] || {};
  const rjd = (state.rjd[id] || {});
  return { id, view, rjd }
};

const mapDispatchToProps = (dispatch) => ({
  updateModel: (id, model) => (props = {}) => dispatch(actions.rjdUpdateModel(id, model, props)),
  updateViewNodePosition: (id) => (voId, pos) => dispatch(actions.updateViewNodePosition(id, voId, pos)),
  updateViewNodeSize: (id) => (voId, pos) => dispatch(actions.updateViewNodeSize(id, voId, pos)),
  updateViewEdgePoints: (id) => (voId, points) => dispatch(actions.updateViewEdgePoints(id, voId, points))
});

export default connect(mapStateToProps, mapDispatchToProps)(ViewRJD);