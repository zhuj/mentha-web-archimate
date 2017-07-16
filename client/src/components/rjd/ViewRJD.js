import React from 'react';
import {connect} from 'react-redux';
import _ from 'lodash';
import * as RJD from 'react-js-diagrams';

import {DropTarget} from 'react-dnd';

import diagramEngineBuilder from './engine';
import * as actions from '../../actions/index';

import './rjd.sass.scss';


const nodesTarget = {
  drop(props, monitor, component) {
    // const {x: pageX, y: pageY} = monitor.getSourceClientOffset();
    // const {left = 0, top = 0} = diagramEngine.canvas.getBoundingClientRect();
    // const diagramModel = diagramEngine.diagramModel;
    // const {offsetX, offsetY} = diagramModel;
    // const x = pageX - left - offsetX;
    // const y = pageY - top - offsetY;
    // const item = monitor.getItem();
    //
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
    //
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
class ViewRJD extends React.Component {

  // constructor(props) {
  //   super(props);
  //   this.setupView({});
  // }

  componentWillMount() {
    const { view } = this.props;
    this.setupView(view);
  }

  componentWillReceiveProps(nextProps) {
    if (!_.isEqual(this.props['view'], nextProps['view'])) {
      this.setupView(nextProps['view']);
    }
  }

  setupView(view) {
    this.diagramEngine = diagramEngineBuilder()
    this.diagramModel = this.diagramEngine.diagramModel;

    const deserialize = function(view, diagramEngine) {
      this.deSerialize({id: view.id});

      // Deserialize nodes
      Object.getOwnPropertyNames(view.nodes).map((id) => {

        const node = view.nodes[id];
        const nodeOb = diagramEngine.getInstanceFactory(/*node._tp*/'BaseNodeModel').getInstance();
        nodeOb.deSerializeViewNode(id, node);

        const portOb = diagramEngine.getInstanceFactory('BasePortModel').getInstance();
        portOb.deSerializeViewNode(id, node);

        this.addNode(nodeOb);
      });

      // Attach ports
      Object.getOwnPropertyNames(view.edges).map((id) => {
        const edge = view.edges[id];
        const linkOb = diagramEngine.getInstanceFactory(/*edge._tp*/'default').getInstance();
        linkOb.deSerializeViewEdge(id, edge);

        if (edge.target) {
          linkOb.setTargetPort(this.getNode(edge.target).getPortFromID(edge.target+'@port'));
        }

        if (edge.source) {
          linkOb.setSourcePort(this.getNode(edge.source).getPortFromID(edge.target+'@port'));
        }

        this.addLink(linkOb);
      });
    };

    deserialize.bind(this.diagramModel)(view, this.diagramEngine);
    this.diagramEngine.setDiagramModel(this.diagramModel);
  }

  onChange(model, action) {

    const updateModel = this.props.updateModel(this.props.id, model);

    // Ignore some events
    if (['items-copied'].indexOf(action.type) !== -1) {
      return;
    }

    // Check for single selected items
    if (['node-selected', 'node-moved'].indexOf(action.type) !== -1) {
      //FIXME: return updateModel({selectedNode: action.model});
    }

    // Check for canvas events
    const deselectEvts = ['canvas-click', 'canvas-drag', 'items-selected', 'items-drag-selected', 'items-moved'];
    if (deselectEvts.indexOf(action.type) !== -1) {
      //FIXME: return updateModel({selectedNode: null});
    }

    // Check if this is a deselection and a single node exists
    const isDeselect = ['node-deselected', 'link-deselected'].indexOf(action.type) !== -1;
    if (isDeselect && action.items.length < 1 && action.model.nodeType) {
      //FIXME: return updateModel({selectedNode: action.model});
    }

    updateModel();
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
  const view = (state.model.views[id] || {});
  const rjd = (state.rjd[id] || {});
  return {id, view /*, ...rjd*/}
};

const mapDispatchToProps = (dispatch) => ({
  updateModel: (id, model) => (props = {}) => dispatch(actions.rjdUpdateModel(id, model, props))
});

export default connect(mapStateToProps, mapDispatchToProps)(ViewRJD);
