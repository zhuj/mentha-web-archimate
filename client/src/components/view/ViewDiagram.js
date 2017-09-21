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

import NewLinkMenu from './NewLinkMenu'

import './ViewDiagram.sass.scss'

const updateDiagramModel = (view, diagramModel) => {

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

  const sync = (obj, source, set) => {
    _.forEach(source, (item, id) => {
      obj[id] = set(id, item, obj[id]);
    });
    _.forEach(obj, (item, id) => {
      if (!source[id]) {
        delete obj[id];
      }
    })
  };

  // nodes
  sync(
    diagramModel.nodes,
    view.nodes,
    (id, node, prev) => {
      if (!prev) { prev = new models.NodeModel(id); }
      prev = Object.assign(prev, {
        nodeType: node['_tp'],
        x: node.pos.x,
        y: node.pos.y,
        width: node.size.width,
        height: node.size.height,
        zIndex: zIndexMap[id] || 0,
        viewObject: node
      });
      return prev;
    }
  );

  // edges
  sync(
    diagramModel.links,
    view.edges,
    (id, edge, prev) => {
      if (!prev) { prev = new models.LinkModel(id); }
      prev = Object.assign(prev, {
        linkType: edge['_tp'],
        viewObject: edge
      });

      const sourceNode = diagramModel.getNode(edge.src);
      const targetNode = diagramModel.getNode(edge.dst);
      prev.setSourceNode(sourceNode);
      prev.setTargetNode(targetNode);

      const l = edge.points.length;
      if (l > 0) {
        if (prev.points.length === (l + 2)) {
          for (let i=0; i<l; i++) {
            prev.points[1+i].updateLocation(edge.points[i]);
          }
        } else {
          prev.setMiddlePoints(edge.points);
        }
      } else {
        prev.setMiddlePoints([]);
      }
      return prev;
    }
  );

  // loops
  /*if (true)*/ {
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
  }

  // overlaps
  /*if (true)*/ {
    const sorted = _.sortBy(diagramModel.getNodes(), (n) => n.zIndex);
    for (let i=0,l=sorted.length;i<l;i++) {
      const node = sorted[i];
      let overlapped = false;
      for (let j=1+i;j<l;j++) {
        const n = sorted[j];
        if (
          (Math.abs(node.x - n.x) < 0.5*(node.width + n.width)) &&
          (Math.abs(node.y - n.y) < 0.5*(node.height + n.height))
        ) {
          overlapped = true;
          break;
        }
      }
      node.overlapped = overlapped;
    }
  }

  // size
  /*if (true)*/ {
    let x0=Number.POSITIVE_INFINITY, y0=Number.POSITIVE_INFINITY, x1=Number.NEGATIVE_INFINITY, y1=Number.NEGATIVE_INFINITY;
    _.forEach(diagramModel.getNodes(), (node) => {
      const w2 = 0.5*node.width;
      const h2 = 0.5*node.height;
      x0 = Math.min(x0, node.x - w2);
      x1 = Math.max(x1, node.x + w2);
      y0 = Math.min(y0, node.y - h2);
      y1 = Math.max(y1, node.y + h2);
    });
    diagramModel.rect = {
      x0, y0, x1, y1,
      xc: 0.5*(x0+x1),
      yc: 0.5*(y0+y1),
      width: (x1 - x0),
      height: (y1 - y0)
    };
  }

  return diagramModel;
};

const diagramModelInState = (props, diagramModel) => {
  const timerName = `view-diagram-update-model-${props.id}`;
  console.time(timerName);
  try {
    return {
      diagramModel: updateDiagramModel(props.view, diagramModel),
      localHash: props.localHash,
    };
  } finally {
    console.timeEnd(timerName);
  }
};


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
      const width = 120, height = 40, x = internal.x+0.5*width, y = internal.y+0.5*height;
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
      const width = 120, height = 40, x = internal.x+0.5*width, y = internal.y+0.5*height;
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
      const width = 10, height = 10, x = internal.x+0.5*width, y = internal.y+0.5*height;
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

  constructor(props) {
    super(props);
    this.state = {
      ...this.state,
      ...diagramModelInState(props, new models.DiagramModel(props.id))
    };

    /*if (true)*/ {
      const rect = this.getDiagramModel().rect;
      if (isFinite(rect.xc) && isFinite(rect.yc)) {
        this.state['offset'] = {
          x: -rect.xc,
          y: -rect.yc
        };
      }
    }
  }

  /* @override: react-localstorage */
  getLocalStorageKey() {
    const { id } = this.props;
    return `view-diagram-${id}`;
  }

  /* @override: react-localstorage */
  getStateFilterKeys() {
    return ["zoom", "offset"];
  }

  componentWillReceiveProps(nextProps) {
    super.componentWillReceiveProps(nextProps);
    this.setState(diagramModelInState(nextProps, this.getDiagramModel()));
    this.clearHoverRegion();
  }

  // TODO: shouldComponentUpdate(nextProps, nextState) {
  // TODO:  if (this.state.localHash === nextState.localHash) { return false; }
  // TODO:  return true;
  // TODO: }

  componentWillUpdate(nextProps, nextState) {
    reactLS.componentWillUpdate.call(this, nextProps, nextState);
    if (!!super.componentWillUpdate) { super.componentWillUpdate(nextProps, nextState); }
  }

  componentDidMount() {
    reactLS.componentDidMount.call(this);
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
      if (event.keyCode === 46) {
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

  removeNewLinks() {
    this.setState({ ['new-link']: null });

    // TODO: remove the link manually from model instead
    this.props.selectViewObjects(this.props.id, []); // just refresh the model from the redux state
  }

  /* @overide: DiagramWidget */
  onChange(action) {
    const viewId = this.props.id;

    // <TEMPORARY>
    if (!!this.state['new-link']) {
      console.log(action);
      this.removeNewLinks();
      return;
    }
    // </TEMPORARY>

    switch (action.type) {
      case 'items-sized':
      case 'items-moved': {
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
        return;
      }
      case 'items-selected-2': {
        _.forEach(action.items, (item) => { item.setSelected(2); });
        this.forceUpdate();
        return;
      }
      case 'title-changed': {
        this.props.sendModelCommands(action.command(viewId));
        return;
      }
      case 'link-created': {
        // it's a free links, remove/ignore it...
        this.removeNewLinks();
        return;
      }
      case 'link-connected': {
        this.setState({
          ['new-link']: { link: action.linkModel, ...this.getRelativeMousePoint(action.event) }
        });
        return;
      }
    }

    if (action.type.indexOf("selected") >= 0) {
      // TODO: check if state has been changed
      this.props.selectViewObjects(viewId, action.items);
    }
  }

  onSelectNewLinkType(link, tp) {
    const { id: viewId, model } = this.props;
    const { sourceNode, targetNode } = link;
    try {
      if (tp === 'viewConnection') {
        this.props.sendModelCommands([
          api.addViewConnection(
            viewId,
            sourceNode.id,
            targetNode.id
          )
        ]);
      } else {
        const candidate = _.findKey(
          model.edges,
          (e) => ( ( e.src === sourceNode.viewObject.concept ) && ( e.dst === targetNode.viewObject.concept ) && ( e._tp === tp ))
        );
        this.props.sendModelCommands([
          api.addViewRelationship(
            viewId,
            candidate || api.addRelationship({
              _tp: tp,
              src: sourceNode.viewObject.concept,
              dst: targetNode.viewObject.concept,
            }),
            sourceNode.id,
            targetNode.id
          )
        ]);
      }
    } finally {
      this.removeNewLinks();
    }
  }

  clearHoverRegion() {
    if (!!this.hoverTimeout) {
      clearTimeout(this.hoverTimeout);
      this.hoverTimeout = null;
    }
    const hover = this.refs['hover-region'];
    hover.classList.remove("visible");
    hover.innerHTML = "";
  }

  // onMouseEnterElement(event, model) {
  //   this.clearHoverRegion();
  //   this.hoverTimeout = setTimeout(
  //     () => {
  //       this.hoverTimeout = null;
  //       const { clientX, clientY } = this.mouse;
  //       const hover = this.refs['hover-region'];
  //
  //       // TODO: draw wiki
  //       hover.classList.add("visible");
  //       hover.innerHTML = JSON.stringify({}, null, 2);
  //       hover.style.left = (clientX + 12)+'px';
  //       hover.style.top = (clientY + 12)+'px';
  //     },
  //     2500
  //   );
  // }
  //
  // onMouseLeaveElement(event, model) {
  //   this.clearHoverRegion();
  // }

  renderNewLinkMenu() {
    const { ['new-link']: newLinkData } = this.state;
    if (!newLinkData) { return null; }

    const { link, x: relX, y: relY } = newLinkData;
    const width = 0;
    const height = 0;

    return (
      <div key='link-menu'
           className='link-menu'
           style={{
             marginLeft:relX - 0.5*width,
             marginTop:relY - 0.5*height
           }}>
        <NewLinkMenu
          linkModel={link}
          select={(tp)=>{this.onSelectNewLinkType(link, tp)}}
        />
      </div>
    )
  }

  renderHoverRegion() {
    return (
      <div key='hover-region' className='hover-region' ref='hover-region'>
      </div>
    );
  }

  render() {
    // const timerName = `view-diagram-render-${this.props.id}`;
    // console.time(timerName);
    try {
      const {id,connectDropTarget} = this.props;
      return connectDropTarget(
        <div className='diagram-root' id={`diagrams-canvas-${id}`}>
          {this.renderNewLinkMenu()}
          {this.renderHoverRegion()}
          {super.render()}
        </div>
      );
    } finally {
      // console.timeEnd(timerName);
    }
  }
}


const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id;
  const model = state.model || {};
  return { id, model,
    view: model.views[id],
    localHash: model['.hash-local'],
    diagramModel: null
  };
};

const mapDispatchToProps = (dispatch) => ({
  sendModelCommands: (commands) => dispatch(actions.sendModelMessage(api.composite(commands))),
  selectViewObjects: (viewId, selectedObjects) => dispatch(actions.selectViewObjects(viewId, selectedObjects)),
});


export default connect(mapStateToProps, mapDispatchToProps)(ViewDiagram)
