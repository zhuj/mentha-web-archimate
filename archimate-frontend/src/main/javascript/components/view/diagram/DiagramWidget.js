import React from 'react'
import _ from 'lodash'

import * as actions from './actions'
import * as models from './models'
import { DefaultNodeWidget, DefaultLinkWidget } from './DefaultWidgets'

import './DiagramWidget.sass.scss'

class LinkWrapper extends React.Component {
  shouldComponentUpdate() {
    const { diagram, link } = this.props;
    return diagram.canEntityRepaint.call(diagram,link);
  }

  render() {
    const { diagram, link } = this.props;
    return (
      <g key={link.id} data-linkid={link.id}>
        {diagram.generateWidgetForLink.call(diagram, {link})}
      </g>
    );
  }
}

class NodeWrapper extends React.Component {
  shouldComponentUpdate() {
    const { diagram, node } = this.props;
    return diagram.canEntityRepaint.call(diagram,node);
  }

  render() {
    const { diagram, node } = this.props;
    const width = node.width || 0;
    const height = node.height || 0;
    const selected = node.isSelected();

    const props = {
      className: `x-node${(selected ? ' selected' : '')}`,
      style:{
        left: node.x - 0.5*width,
        top: node.y - 0.5*height,
        width: width,
        height: height,
        zIndex: node.zIndex || 0
      },
      onMouseEnter: (event)=>{diagram.onMouseEnterElement.call(diagram, event, node)},
      onMouseLeave: (event)=>{diagram.onMouseLeaveElement.call(diagram, event, node)}
  };

    if (selected) {
      return (
        <div key={node.id} data-nodeid={node.id} {...props}>
          {diagram.generateWidgetForNode.call(diagram, {node})}
          <div className="resize lt"/>
          <div className="resize ct"/>
          <div className="resize rt"/>
          <div className="resize rc"/>
          <div className="resize rb"/>
          <div className="resize cb"/>
          <div className="resize lb"/>
          <div className="resize lc"/>
        </div>
      );
    }

    return (
      <div key={node.id} data-nodeid={node.id} {...props}>
        {diagram.generateWidgetForNode.call(diagram, {node})}
      </div>
    );
  }
}


class PortWrapper extends React.Component {
  // shouldComponentUpdate() {
  //   const { diagram, node } = this.props;
  //   return diagram.canEntityRepaint.call(diagram,node);
  // }
  render() {
    const { node } = this.props;
    const width = node.width || 0;
    const height = node.height || 0;
    const x = node.x - 0.5*width;
    const y = node.y - 0.5*height;
    return (
      <rect x={x} y={y} width={width} height={height}
        className="x-port"
        data-id={node.id}
        data-nodeid={node.id}
      />
    );
  }
}

class DiagramAreaWrapper extends React.Component {
  shouldComponentUpdate() {
    const { diagram } = this.props;
    return diagram.canAreaRepaint.call(diagram);
  }
  render() {
    const { diagram } = this.props;
    return (
      <div>
        {diagram.renderNodeLayerWidget.call(diagram)}
        {diagram.renderLinkLayerWidget.call(diagram)}
        {diagram.renderSelector.call(diagram)}
      </div>
    )
  }
}

const ELTP = {
  LINK_POINT: 'link-point',
  LINK: 'link',
  NODE_PORT: 'node-port',
  NODE_RESIZE: 'node-resize',
  NODE: 'node'
};

export class DiagramWidget extends React.Component {

  constructor(props, context) {
    super(props, context);
    this.canvas = null;
    this.paintableWidgets = null;
    this.state = {
      diagramModel: new models.DiagramModel(),
      action: null,
      actionType: 'unknown',
      windowListener: null,
      clipboard: null,
      offset: {x: 0, y: 0},
      zoom: 1.0
    };
  }

  onChange(action) {
    return false;
  }

  getDiagramModel() {
    return this.state['diagramModel'];
  }

  componentWillUnmount() {
    this.canvas = null;
    window.removeEventListener('keydown', this.state.windowListener);
  }

  componentDidMount() {
    this.canvas = this.refs['canvas'];
    this.setState({ windowListener: window.addEventListener('keydown', this.buildWindowListener()) });
    window.focus();
  }

  componentDidUpdate() {
    this.canvas = this.refs['canvas'];
  }

  componentWillReceiveProps(nextProps, nextContext) {
    this.clearRepaintEntities();
  }

  buildWindowListener() {
    return event => {};
  }

  getOffset() {
    return this.state.offset;
  }

  toInternalCoordinates(clientX, clientY) {
    const { left, top, width, height } = this.canvas.getBoundingClientRect();
    const { zoom, offset: { x: offsetX, y: offsetY } } = this.state;
    return {
      x: ((clientX - left - 0.5*width) / zoom) - offsetX,
      y: ((clientY - top - 0.5*height) / zoom) - offsetY
    };
  }

  getRelativeMousePoint(event) {
    const { left, top } = this.canvas.getBoundingClientRect();
    return { x: event.clientX - left, y: event.clientY - top };
  }

  getInternalMousePoint(event) {
    return this.toInternalCoordinates(event.clientX, event.clientY);
  }


  clearRepaintEntities() {
    this.paintableWidgets = null;
  }

  enableRepaintEntities(entities) {
    this.paintableWidgets = this.getDiagramModel().getRepaintEntities(entities);
  }

  canEntityRepaint(model) {
    if (this.paintableWidgets === null) {
      return true;// No rules applied, allow repaint
    }
    return this.paintableWidgets[model.id] !== undefined;
  }

  canAreaRepaint() {
    return ((this.paintableWidgets === null) || (Object.keys(this.paintableWidgets).length > 0));
  }

  /**
   * Gets an element meta under the mouse cursor
   */
  getMouseElement(event) {
    const { target } = event;
    const model = this.getDiagramModel();

    let element;

    // Look for a point
    element = target.closest('.x-point[data-index]');
    if (element) {
      const link = model.getLink(element.getAttribute('data-linkid'));
      const point = link.getPoint(element.getAttribute('data-index'));

      return {
        type: ELTP.LINK_POINT,
        link: link,
        ref: point,
        element
      };
    }

    // Look for a link
    element = target.closest('.x-link[data-linkid]');
    if (element) {
      const link = model.getLink(element.getAttribute('data-linkid'));
      return {
        type: ELTP.LINK,
        ref: link,
        element
      };
    }

    // Look for a resize point
    element = target.closest('.resize');
    if (element) {
      const kind = ((cl) => {
        if (cl.contains('lt')) { return [ -1, -1 ]; }
        if (cl.contains('ct')) { return [  0, -1 ]; }
        if (cl.contains('rt')) { return [  1, -1 ]; }
        if (cl.contains('rc')) { return [  1,  0 ]; }
        if (cl.contains('rb')) { return [  1,  1 ]; }
        if (cl.contains('cb')) { return [  0,  1 ]; }
        if (cl.contains('lb')) { return [ -1,  1 ]; }
        if (cl.contains('lc')) { return [ -1,  0 ]; }
        return [ 0, 0 ];
      })(element.classList);

      const node = model.getNode(element.closest('.x-node[data-nodeid]').getAttribute('data-nodeid'));
      return {
        type: ELTP.NODE_RESIZE,
        kind: kind,
        ref: node,
        element
      };
    }

    // Look for a port
    element = target.closest('.x-port[data-nodeid]');
    if (element) {
      const node = model.getNode(element.getAttribute('data-nodeid'));
      return {
        type: ELTP.NODE_PORT,
        ref: node.asPort(),
        element
      };
    }

    // Look for a node
    element = target.closest('.x-node[data-nodeid]');
    if (element) {
      const node = model.getNode(element.getAttribute('data-nodeid'));
      return {
        type: ELTP.NODE,
        ref: node,
        element
      };
    }

    return null;
  }

  onMouseEnterElement(event, model) {

  }

  onMouseLeaveElement(event, model) {

  }

  onWheel(event) {

    event.preventDefault();
    event.stopPropagation();

    const internalMouse = this.getInternalMousePoint(event);
    const { zoom: initialZoom, offset: initialOffset } = this.state;
    const zoom = Math.max(0.1, initialZoom - (event.deltaY * initialZoom * 0.001));
    const offset = {
      x: (internalMouse.x + initialOffset.x) * (initialZoom / zoom) - internalMouse.x,
      y: (internalMouse.y + initialOffset.y) * (initialZoom / zoom) - internalMouse.y
    };

    this.enableRepaintEntities([]);
    this.setState({ zoom, offset });
  }

  transformMouseDownAction(event) {

    const { action } = this.state;
    if (!(action instanceof actions.MouseDownAction)) { return this.state; }
    if (!action.isSignificant(this.getRelativeMousePoint(event))) { return this.state; }

    const _setState = (state) => {
      this.setState(state);
      return state;
    };

    const diagramModel = this.getDiagramModel();
    const { relativeMouse, mouseElement, shiftKey, clientXY } = action;

    if (mouseElement === null) {
      if (shiftKey) {
        return _setState({
          action: new actions.SelectingAction(this, relativeMouse, this.getInternalMousePoint(clientXY)),
          actionType: 'canvas-shift-select'
        });
      }
      this.enableRepaintEntities([]);
      return _setState({
        action: new actions.MoveCanvasAction(this, relativeMouse),
        actionType: 'canvas-click'
      });
    }

    if (mouseElement.type === ELTP.NODE_RESIZE) {
      mouseElement.ref.setSelected(true);
      return _setState({
        action: new actions.ResizeItemAction(this, relativeMouse, mouseElement.kind),
        actionType: 'node-selected'
      });
    }

    if (mouseElement.type === ELTP.LINK_POINT) {
      if (!mouseElement.ref.isSelected()) {
        diagramModel.setSelection((el) => el === mouseElement.ref);
      }
      return _setState({
        action: new actions.MoveItemsAction(this, relativeMouse),
        actionType: 'point-selected'
      });
    }

    if ((mouseElement.type === ELTP.NODE) && !mouseElement.ref.isSelected()) {
      if (diagramModel.getSelectedItems().length > 0) {
        return this.state;
      }

      const internalMouse = this.getInternalMousePoint(clientXY);
      diagramModel.setSelection(() => false);

      const link = this.createDefaultLink(mouseElement.ref);
      diagramModel.getLinks()[link.id] = link;
      link.setSourceNode(mouseElement.ref);
      link.getFirstPoint().updateLocation(internalMouse);
      link.getLastPoint().updateLocation(internalMouse);
      link.getLastPoint().setSelected(true);

      return _setState({
        action: new actions.MoveItemsAction(this, relativeMouse),
        actionType: 'link-created'
      });
    }

    // Get the selected items and filter out point mouseElement
    const selected = diagramModel.getSelectedItems();
    if (selected.length <= 0) { return this.state; }
    if (!mouseElement.ref.isSelected()) { return this.state; }

    // Determine action type
    let actionType = 'items-selected';
    if (selected.length === 1 && (selected[0] instanceof models.NodeModel)) {
      actionType = 'node-selected';
    } else if (selected.length === 1 && (selected[0] instanceof models.PointModel)) {
      actionType = 'point-selected';
    } else {
      const filtered = _.filter(selected, item => !(item instanceof models.PointModel));
      if ((filtered.length === 1) && (filtered[0] instanceof models.LinkModel)) {
        actionType = 'link-selected';
      }
    }

    return _setState({
      action: new actions.MoveItemsAction(this, relativeMouse),
      actionType
    });
  }

  onMouseMove(event) {
    this.mouse = { clientX: event.clientX, clientY: event.clientY };

    if (!this.state['action']) { return; }

    const { action, actionType: currentActionType } = this.transformMouseDownAction(event);
    if (action instanceof actions.MouseDownAction) { return; }

    const relativeMouse = this.getRelativeMousePoint(event);

    // Select items so draw a bounding box
    if (action instanceof actions.SelectingAction) {
      this.getDiagramModel().setSelection((ref) => action.containsElement(ref.x, ref.y));
      action.internalMouse2 = this.getInternalMousePoint(event);
      this.setState({ action, actionType: 'items-drag-selected' });
      return;
    }

    // Resize selected items
    if (action instanceof actions.ResizeItemAction) {

      const { zoom } = this.state;

      // Translate the items on the canvas
      const [ kX, kY ] = action.kind;
      const shX = ((relativeMouse.x - action.relativeMouse.x) / zoom);
      const shY = ((relativeMouse.y - action.relativeMouse.y) / zoom);
      action.selectionData.forEach(model => {
        const { ref } = model;
        ref.x = model.initialX + Math.abs(kX)*0.5*shX;
        ref.y = model.initialY + Math.abs(kY)*0.5*shY;
        ref.width = Math.max(model.initialW + kX*shX, 5);
        ref.height = Math.max(model.initialH + kY*shY, 5);
      });

      // Determine actionType, do not override some mouse down
      let actionType = 'items-sized';

      // this.enableRepaintEntities(action.selectedItems);
      this.setState({ actionType });
      return;
    }

    // Move selected items
    if (action instanceof actions.MoveItemsAction) {

      const { zoom } = this.state;

      // Translate the items on the canvas
      const shX = ((relativeMouse.x - action.relativeMouse.x) / zoom);
      const shY = ((relativeMouse.y - action.relativeMouse.y) / zoom);
      action.selectionData.forEach(model => {
        const { ref } = model;
        ref.x = model.initialX + shX;
        ref.y = model.initialY + shY;
      });

      // Determine actionType, do not override some mouse down
      const disallowed = ['link-created'];
      let actionType = disallowed.indexOf(currentActionType) === -1 ? 'items-moved' : currentActionType;

      // this.enableRepaintEntities(action.selectedItems);
      this.setState({ actionType });
      return;
    }

    // Move the canvas itself
    if (action instanceof actions.MoveCanvasAction) {

      const { zoom } = this.state;
      const offset = {
        x: action.initialOffset.x + ((relativeMouse.x - action.relativeMouse.x) / zoom),
        y: action.initialOffset.y + ((relativeMouse.y - action.relativeMouse.y) / zoom)
      };

      this.setState({ actionType: 'canvas-drag', offset });
      return;
    }
  }

  onMouseDown(event) {

    // TODO: make it better
    if (event.buttons !== 1) { return; } // only the left button

    const relativeMouse = this.getRelativeMousePoint(event);
    const mouseElement = this.getMouseElement(event);

    this.clearRepaintEntities();
    this.setState({
      action: new actions.MouseDownAction(this, relativeMouse, event, mouseElement, event.shiftKey),
      actionType: 'mouse-down'
    });

  }

  onMouseUp(event) {
    const diagramModel = this.getDiagramModel();

    const { action } = this.state;
    const actionOutput = { type: this.state['actionType'], event: event };

    if (action instanceof actions.MouseDownAction) {

      const { mouseElement, shiftKey } = action;

      if (mouseElement === null) {

        const wasSelected = (diagramModel.getSelectedItems().length > 0);
        diagramModel.setSelection((m) => false);
        if (wasSelected) {
          actionOutput.type = 'items-selected';
        } else {
          actionOutput.type = 'canvas-click';
        }

      } else {

        // It's a direct click selection
        let deselect = false;
        const wasSelected = mouseElement.ref.isSelected();

        // Modify selection
        if (shiftKey) {
          if (wasSelected) {
            mouseElement.ref.setSelected(false);
            deselect = true;
          } else {
            mouseElement.ref.setSelected(true);
          }
        } else {
          diagramModel.setSelection((m) => m === mouseElement.ref);
        }

        // Determine action type
        const selected = diagramModel.getSelectedItems();
        if (wasSelected && selected.length === 1) {
          actionOutput.type = 'items-selected-2';
        } else {
          actionOutput.type = 'items-selected';
        }

      }

    } else if (action instanceof actions.ResizeItemAction) {

      // Add the node model to the output
      const mouseElement = this.getMouseElement(event);
      actionOutput.model = !!mouseElement ? mouseElement.ref : null;

    } else if (action instanceof actions.MoveItemsAction) {

      // Add the node model to the output
      const mouseElement = this.getMouseElement(event);
      actionOutput.model = !!mouseElement ? mouseElement.ref : null;

      // Check if we going to connect a link to something
      if (action.linkLastPointSelection) {
        const link = action.selectedItems[0].link;
        actionOutput.linkModel = link;

        // Check if a point was created
        if (mouseElement.element.tagName === 'circle' && actionOutput.type !== 'link-created') {
          actionOutput.type = 'link-created';
        }

        if (mouseElement.ref instanceof models.PortModel) {

          // Connect the link
          diagramModel.setSelection(()=>false);
          link.setTargetNode(mouseElement.ref.parentNode);

          // Link was connected to a node, update the output
          actionOutput.type = 'link-connected';
          actionOutput.nodeModel = mouseElement.ref.parentNode;
        }
      }
    }

    actionOutput.items = _.filter(diagramModel.getSelectedItems(), item => (item instanceof models.NodeModel) || (item instanceof models.LinkModel));
    this.clearRepaintEntities();

    if (actionOutput.type !== 'unknown') {
      try { this.onChange(actionOutput); }
      catch(e) { console.error(e); }
    }

    this.setState({ action: null, actionType: 'unknown' });
  }

  addPointIntoLink(event, link, index) {
    event.stopPropagation();
    const point = link.addPoint(
      this.getInternalMousePoint(event),
      index + 1
    );
    this.getDiagramModel().setSelection((ref) => ref === point);
    this.setState({
      action: new actions.MoveItemsAction(this, this.getRelativeMousePoint(event))
    });
    return point;
  }

  createDefaultLink(node) {
    return new models.LinkModel(models.generateId());
  }

  generateWidgetForNode(props) {
    return (
      <DefaultNodeWidget diagram={this} {...props}/>
    )
  }

  generateWidgetForLink(props) {
    return (
      <DefaultLinkWidget diagram={this} {...props}/>
    )
  }

  generateNodes(mode) {
    return _.map(this.getDiagramModel().getNodes(), node => (
      <NodeWrapper
        key={node.id}
        node={node}
        diagram={this}
      />
    ));
  }

  generateLinks(mode) {
    return _.map(this.getDiagramModel().getLinks(), link => (
      <LinkWrapper
        key={link.id}
        link={link}
        diagram={this}
      />
    ));
  }

  generatePorts(mode) {
    const { action } = this.state;
    if ((action instanceof actions.MoveItemsAction) && (action.linkLastPointSelection)) {
      const source = action.selectedItems[0].link.sourceNode;
      return _.map(this.getDiagramModel().getNodes(), node => source === node ? null : (
        <PortWrapper
          key={node.id}
          node={node}
          diagram={this}
        />
      ));
    }
    return null;
  }

  renderNodeLayerWidget() {
    // TODO: https://developer.mozilla.org/ru/docs/Web/SVG/Element/foreignObject
    return (
      <div className="node-view">
        { this.generateNodes('html') }
      </div>
    );
  }

  renderLinkLayerDefs() {
    return null;
  }

  renderLinkLayerWidget() {
    return (
      <svg className="link-view">
        { this.renderLinkLayerDefs() }
        { this.generateLinks('svg') }
        { this.generatePorts('svg') }
      </svg>
    );
  }

  renderSelector() {
    const { action } = this.state;
    if (!(action instanceof actions.SelectingAction)) {
      return null;
    }

    const {x:x1, y:y1} = action.internalMouse1;
    const {x:x2, y:y2} = action.internalMouse2;


    const style = {
      border: '1px solid black',
      width: Math.abs(x2 - x1),
      height: Math.abs(y2 - y1),
      left: Math.min(x1, x2),
      top: Math.min(y1, y2)
    };

    return (
      <div className="node-view">
        <div
          className='selector'
          style={style}
        />
      </div>
    );
  }

  renderPrefixLayer() {
    return null;
  }

  render() {
    const { zoom, offset } = this.state;
    const transformStyle = {
      transform: `scale(${zoom}) translate(${offset.x}px, ${offset.y}px)`,
      // transition: 'transform 0ms linear 0s',
      position: 'absolute'
    };

    return (
      <div
        ref='canvas'
        className='diagrams-canvas'
        onWheel={this.onWheel.bind(this)}
        onMouseDown={this.onMouseDown.bind(this)}
        onMouseMove={this.onMouseMove.bind(this)}
        onMouseUp={this.onMouseUp.bind(this)}
        // TODO: onTouchStart={(event)=>this.onMouseDown(...)}
        // TODO: onTouchMove={(event)=>this.onMouseMove(...)}
        // TODO: onTouchEnd={(event)=>this.onMouseUp(...)}
      >
        <div className="diagram-center">
          {this.renderPrefixLayer()}
          <div className="diagram-position" style={transformStyle}>
            <DiagramAreaWrapper diagram={this}/>
          </div>
        </div>
      </div>
    );
  }

}
