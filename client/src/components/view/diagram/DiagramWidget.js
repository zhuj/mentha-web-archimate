import React from 'react'
import _ from 'lodash'

import * as actions from './actions'
import * as models from './models'
import { DefaultNodeWidget, DefaultLinkWidget } from './DefaultWidgets'

class LinkWrapper extends React.Component {
  shouldComponentUpdate() {
    const { diagram, link } = this.props;
    return diagram.canEntityRepaint.bind(diagram)(link);
  }

  render() {
    const { link, children } = this.props;
    return (
      <g data-linkid={link.id}>
        {children}
      </g>
    );
  }
}

class NodeWrapper extends React.Component {
  shouldComponentUpdate() {
    const { diagram, node } = this.props;
    return diagram.canEntityRepaint.bind(diagram)(node);
  }

  render() {
    const { node, children } = this.props;
    const width = node.width || 0;
    const height = node.height || 0;
    const selected = node.isSelected();

    const props = {
      className: `node${(selected ? ' selected' : '')}`,
      style:{
        left: node.x - width/2,
        top: node.y - height/2,
        width: width,
        height: height,
        zIndex: node.zIndex || 0
      }
    };

    if (selected) {
      return (
        <div data-nodeid={node.id} {...props}>
          {children}
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
      <div data-nodeid={node.id} {...props}>
        {children}
      </div>
    );
  }
}


class PortWrapper extends React.Component {
  // shouldComponentUpdate() {
  //   const { diagram, node } = this.props;
  //   return diagram.canEntityRepaint.bind(diagram)(node);
  // }
  render() {
    const { node } = this.props;
    const width = node.width || 0;
    const height = node.height || 0;
    const x = node.x - width / 2;
    const y = node.y - height / 2;
    return (
      <rect x={x} y={y} width={width} height={height}
        className="port"
        data-id={node.id}
        data-nodeid={node.id}
      />
    );
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

  constructor(props) {
    super(props);
    this.canvas = null;
    this.paintableWidgets = null;
    this.state = {
      action: null,
      actionType: 'unknown',
      windowListener: null,
      clipboard: null,
      offset: {x: 0, y: 0},
      zoom: 1.0
    };
  }

  onChange(action) {

  }

  getDiagramModel() {
    return this.props['diagramModel'];
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

  componentWillReceiveProps(nextProps) {
    this.clearRepaintEntities();
    const nextDiagramModel = nextProps['diagramModel'];
    const thisDiagramModel = this.getDiagramModel();
    if (!!thisDiagramModel && !!thisDiagramModel && (nextDiagramModel.id === thisDiagramModel.id)) {
      nextDiagramModel.acquireRuntimeState(thisDiagramModel);
    }
  }

  buildWindowListener() {

    return event => {
      // XXX: const selectedItems = diagramEngine.getDiagramModel().getSelectedItems();
      const ctrl = (event.metaKey || event.ctrlKey);

      // XXX: // Select all
      // XXX: if (event.keyCode === 65 && ctrl && selectAll) {
      // XXX:  this.selectAll(true);
      // XXX:  event.preventDefault();
      // XXX:  event.stopPropagation();
      // XXX: }

      // XXX: // Deselect all
      // XXX: if (event.keyCode === 68 && ctrl && deselectAll) {
      // XXX:  this.selectAll(false);
      // XXX:  event.preventDefault();
      // XXX:  event.stopPropagation();
      // XXX: }

      // XXX: // Copy selected
      // XXX: if (event.keyCode === 67 && ctrl && selectedItems.length && copy) {
      // XXX:   this.copySelectedItems(selectedItems);
      // XXX: }

      // XXX: // Paste from clipboard
      // XXX: if (event.keyCode === 86 && ctrl && this.state.clipboard && paste) {
      // XXX:   this.pasteSelectedItems(selectedItems);
      // XXX: }

      // XXX: // Delete all selected
      // XXX: if ([8, 46].indexOf(event.keyCode) !== -1 && selectedItems.length && deleteItems) {
      // XXX:  selectedItems.forEach(element => {
      // XXX:    element.remove();
      // XXX:  });
      // XXX:
      // XXX:  this.onChange({ type: 'items-deleted', items: selectedItems });
      // XXX:  this.forceUpdate();
      // XXX: }

    };
  }

  getOffset() {
    return this.state.offset;
  }

  toInternalCoordinates(clientX, clientY) {
    const { left, top, width, height } = this.canvas.getBoundingClientRect();
    const { zoom, offset: { x: offsetX, y: offsetY } } = this.state;
    return {
      x: ((clientX - left - width/2) / zoom) - offsetX,
      y: ((clientY - top - height/2) / zoom) - offsetY
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


  /**
   * Gets an element meta under the mouse cursor
   */
  getMouseElement(event) {
    const { target } = event;
    const model = this.getDiagramModel();

    let element;

    // Look for a point
    element = target.closest('.point[data-index]');
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
    element = target.closest('.link[data-linkid]');
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

      const node = model.getNode(element.closest('.node[data-nodeid]').getAttribute('data-nodeid'));
      return {
        type: ELTP.NODE_RESIZE,
        kind: kind,
        ref: node,
        element
      };
    }

    // Look for a port
    element = target.closest('.port[data-nodeid]');
    if (element) {
      const node = model.getNode(element.getAttribute('data-nodeid'));
      return {
        type: ELTP.NODE_PORT,
        ref: node.asPort(),
        element
      };
    }

    // Look for a node
    element = target.closest('.node[data-nodeid]');
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

      const internalMouse = this.getInternalMousePoint(event);
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
        ref.x = model.initialX + Math.abs(kX)*shX/2;
        ref.y = model.initialY + Math.abs(kY)*shY/2;
        ref.width = Math.max(model.initialW + kX*shX, 5);
        ref.height = Math.max(model.initialH + kY*shY, 5);
      });

      // Determine actionType, do not override some mouse down
      let actionType = 'items-sized';
      if (action.selectionData.length === 1 && action.selectionData[0].ref instanceof models.NodeModel) {
        actionType = 'node-sized';
      }

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
      if (action.selectionData.length === 1 && action.selectionData[0].ref instanceof models.NodeModel) {
        actionType = 'node-moved';
      }

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
    const actionOutput = { type: this.state['actionType'] };

    if (action instanceof actions.MouseDownAction) {

      const { mouseElement, shiftKey } = action;

      if (mouseElement === null) {

        diagramModel.setSelection((m) => false);
        actionOutput.type = 'canvas-click';

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

        // Get the selected items and filter out point mouseElement
        const isLink = mouseElement.type === ELTP.LINK;
        const isNode = mouseElement.type === ELTP.NODE;
        const isPoint = mouseElement.type === ELTP.LINK_POINT;

        const selected = diagramModel.getSelectedItems();

        // Determine action type
        if (deselect && isLink) {
          actionOutput.type = 'link-deselected';
        } else if (deselect && isNode) {
          actionOutput.type = 'node-deselected';
        } else if (deselect && isPoint) {
          actionOutput.type = 'point-deselected';
        } else if ((selected.length === 1 || selected.length === 2 && _.filter(selected, item => !(item instanceof models.PointModel)).length === 1) && isLink) {
          actionOutput.type = 'link-selected';
        } else if (selected.length === 1 && isNode) {
          actionOutput.type = 'node-selected';
        } else if (selected.length === 1 && isPoint) {
          actionOutput.type = 'point-selected';
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

        // Check if a point was created
        if (mouseElement.element.tagName === 'circle' && actionOutput.type !== 'link-created') {
          actionOutput.type = 'point-created';
        }

        if (mouseElement.ref instanceof models.PortModel) {

          // Connect the link
          link.setTargetNode(mouseElement.ref.parentNode);

          // Link was connected to a node, update the output
          actionOutput.type = 'link-connected';
          delete actionOutput.model;
          actionOutput.linkModel = link;
          actionOutput.nodeModel = mouseElement.ref.node;
        }
      }
    }

    const attachItems = ['items-selected', 'items-drag-selected', 'items-moved', 'items-sized', 'node-deselected', 'link-deselected'];
    if (attachItems.indexOf(actionOutput.type) !== -1) {
      actionOutput.items = _.filter(diagramModel.getSelectedItems(), item => !(item instanceof models.PointModel));
    }
    if (actionOutput.type === 'items-moved' || actionOutput.type === 'items-sized') {
      delete actionOutput.model;
    }

    this.clearRepaintEntities();

    if (actionOutput.type !== 'unknown') {
      this.onChange(actionOutput);
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
    const link = new models.LinkModel();
    link.id =  'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
      var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
    return link;
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
      >
        { this.generateWidgetForNode({node}) }
      </NodeWrapper>
    ));
  }

  generateLinks(mode) {
    return _.map(this.getDiagramModel().getLinks(), link => (
      <LinkWrapper
        key={link.id}
        link={link}
        diagram={this}
      >
        { this.generateWidgetForLink({link}) }
      </LinkWrapper>
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
    const { zoom, offset } = this.state;
    const style = {
      transform: `scale(${zoom}) translate(${offset.x}px, ${offset.y}px)`, // eslint-disable-line
      width: '100%',
      height: '100%'
    };

    // TODO: https://developer.mozilla.org/ru/docs/Web/SVG/Element/foreignObject
    return (
      <div className="node-view" style={style}>
        { this.generateNodes('html') }
      </div>
    );
  }

  renderLinkLayerWidget() {

    const { zoom, offset } = this.state;
    const style = {
      transform: `scale(${zoom}) translate(${offset.x}px, ${offset.y}px)`,
      width: '100%',
      height: '100%'
    };

    return (
      <svg className="link-view" style={style}>
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

    const { zoom, offset } = this.state;
    const wrapperStyle = {
      transform: `scale(${zoom}) translate(${offset.x}px, ${offset.y}px)`, // eslint-disable-line
      width: '100%',
      height: '100%'
    };


    const style = {
      border: '1px solid black',
      width: Math.abs(x2 - x1),
      height: Math.abs(y2 - y1),
      left: Math.min(x1, x2),
      top: Math.min(y1, y2)
    };

    return (
      <div className="node-view" style={wrapperStyle}>
        <div
          className='selector'
          style={style}
        />
      </div>
    );
  }


  render() {
    return (
      <div
        ref='canvas'
        className='diagrams-canvas'
        onWheel={this.onWheel.bind(this)}
        onMouseMove={this.onMouseMove.bind(this)}
        onMouseDown={this.onMouseDown.bind(this)}
        onMouseUp={this.onMouseUp.bind(this)}
      >
        <div className="diagram-center">
        {this.renderNodeLayerWidget()}
        {this.renderLinkLayerWidget()}
        {this.renderSelector()}
        </div>
      </div>
    );
  }

}
