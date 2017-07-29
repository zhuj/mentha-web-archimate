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

import './ViewDiagram.sass.scss'

const nodesTarget = {
  drop(props, monitor, component) {
    const { tp, kind } = monitor.getItem();

    const srcClientOffset = monitor.getSourceClientOffset();
    const internal = component.getInternalMousePoint({
      clientX: srcClientOffset.x,
      clientY: srcClientOffset.y
    });


    // concept elements
    if (kind === 'element') {
      const width = 120, height = 40, x = internal.x+width/2, y = internal.y+height/2;
      return component.props.sendModelCommands([
        api.addViewNodeConcept(
          component.props.id,
          api.addElement({ _tp: tp }),
          {x, y},
          {width, height}
        )
      ]);
    }

    // notes
    if (kind === 'notes') {
      const width = 120, height = 40, x = internal.x+width/2, y = internal.y+height/2;
      return component.props.sendModelCommands([
        api.addViewNotes(
          component.props.id,
          {x, y},
          {width, height}
        )
      ]);
    }

    if (kind === 'connector') {
      // lazy: TODO
      const width = 10, height = 10, x = internal.x+width/2, y = internal.y+height/2;
      const diagramModel = component.getDiagramModel();
      const conceptInfo = { _tp: tp };
      const viewObject = { _tp: 'viewNodeConcept', name: '', conceptInfo };
      const node = diagramModel.addNode(
        Object.assign(new models.NodeModel(models.generateId()), {
          x: x, y: y, width: width, height: height,
          zIndex: 999, // place above all the nodes
          viewObject
        })
      );
      console.log(node);
    }

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
  buildWindowListener() {
    return event => {
      const viewId = this.props.id;
      const diagramModel = this.getDiagramModel();
      // const ctrl = (event.metaKey || event.ctrlKey);

      // Delete all selected
      if ([8, 46].indexOf(event.keyCode) !== -1) {
        const selectedItems = diagramModel.getSelectedItems();
        if (selectedItems.length > 0) {
          this.props.sendModelCommands(
            _.chain(selectedItems)
              .filter((vo) => (vo instanceof models.NodeModel) || (vo instanceof models.LinkModel))
              .map((vo) => api.deleteViewObject(viewId, vo.id))
              .value()
          );
        }
      }
    };
  }

  /* @overide: DiagramWidget */
  onChange(action) {
    // update the rest
    switch (action.type) {
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
      case 'items-selected-2': {
        // TODO: make it editable
        console.log(action);
        break;
      }
    }

    if (action.type.indexOf("selected") >= 0) {
      const viewId = this.props.id;
      const selectedItems = action.items;
      // TODO: check if state has been changed
      this.props.selectViewObjects(viewId, selectedItems);
    }

  }

  render() {
    const { connectDropTarget } = this.props;
    return connectDropTarget(
      <div className='diagram-root'>
        { super.render() }
      </div>
    );
  }
}


const createDiagramModel = (model, id) => {

  const view = model.views[id];
  const zIndexMap = ((view) => {
    return _.chain(view.nodes)
      .entries(view.nodes)
      .sortBy((e) => {
        const { width: w, height: h } = e[1].size;
        return -(w*h);
      })
      .reduce((o, e, idx) => { o[e[0]] = idx; return o; }, {})
      .value();
  })(view);

  const diagramModel = new models.DiagramModel(id);

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

  return diagramModel;
};

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id;
  const model = state.model;
  return { id, diagramModel: createDiagramModel(model, id) };
};

const mapDispatchToProps = (dispatch) => ({
  sendModelCommands: (commands) => dispatch(actions.sendModelMessage(api.composite(commands))),
  selectViewObjects: (viewId, selectedObjects) => dispatch(actions.selectViewObjects(viewId, selectedObjects)),
  //updateViewNodePosAndSize: (viewId) => (voId, pos, size) => dispatch(actions.updateViewNodePosAndSize(viewId, voId, { x:pos.x, y:pos.y }, { width: size.width, height: size.height })),
  //updateViewEdgePoints: (viewId) => (voId, points) => dispatch(actions.updateViewEdgePoints(viewId, voId, _.chain(points).slice(1, points.length-1).map(p=>({ x:p.x, y:p.y })).value()))
});


export default connect(mapStateToProps, mapDispatchToProps)(ViewDiagram)
