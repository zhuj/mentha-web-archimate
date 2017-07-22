import React from 'react'
import _ from 'lodash'

import { MoveCanvasAction, MoveItemsAction, SelectingAction } from './actions'
import { NodeModel, PortModel, LinkModel, PointModel } from './models'
import { DefaultNodeWidget, DefaultLinkWidget } from './DefaultWidgets'

class LinkWrapper extends React.Component {
  shouldComponentUpdate() {
    let diagram = this.props.diagram;
    return diagram.canEntityRepaint.bind(diagram)(this.props.link);
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
    let diagram = this.props.diagram;
    return diagram.canEntityRepaint.bind(diagram)(this.props.node);
  }

  render() {
    const { node, children } = this.props;
    const width = node.width || 0;
    const height = node.height || 0;

    const props = {
      className: `node${(node.isSelected() ? ' selected' : '')}`,
      style:{
        left: node.x - width/2,
        top: node.y - height/2,
        width: width,
        height: height,
        zIndex: node.zIndex || 0
      }
    };
    return (
      <div data-nodeid={node.id} {...props}>
        {children}
      </div>
    );
  }
}

const DEFAULT_ACTIONS = {
  deleteItems: true,
  selectItems: true,
  moveItems: true,
  multiselect: true,
  multiselectDrag: true,
  canvasDrag: true,
  zoom: true,
  copy: true,
  paste: true,
  selectAll: true,
  deselectAll: true
};

export class DiagramWidget extends React.Component {

  static defaultProps = {
    actions: DEFAULT_ACTIONS
  };

  getActions() {
    if (this.props.actions === null) { return {}; }
    return { ...DEFAULT_ACTIONS, ...(this.props.actions || {}) };
  }

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

  componentWillReceiveProps() {
    this.clearRepaintEntities();
  }

  buildWindowListener() {
    const { selectAll, deselectAll, copy, paste, deleteItems } = this.getActions();

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
    const { left, top } = this.canvas.getBoundingClientRect();
    const { zoom, offset: { x: offsetX, y: offsetY } } = this.state;
    return {
      x: ((clientX - left) / zoom) - offsetX,
      y: ((clientY - top) / zoom) - offsetY
    };
  }

  toRelativeCoordinates(intX, intY) {
    const { zoom, offset: { x: offsetX, y: offsetY } } = this.state;
    return {
      x: (offsetX + intX) * zoom,
      y: (offsetY + intY) * zoom
    };
  }

  getRelativePoint(clientX, clientY) {
    const { left, top } = this.canvas.getBoundingClientRect();
    return { x: clientX - left, y: clientY - top };
  }

  getRelativeMousePoint(event) {
    return this.getRelativePoint(event.clientX, event.clientY);
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
        type: 'point',
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
        type: 'link',
        ref: link,
        element
      };
    }

    // Look for a port
    element = target.closest('.port[data-nodeid]');
    if (element) {
      const node = model.getNode(element.getAttribute('data-nodeid'));
      return {
        type: 'port',
        ref: node.asPort(),
        element
      };
    }

    // Look for a node
    element = target.closest('.node[data-nodeid]');
    if (element) {
      const node = model.getNode(element.getAttribute('data-nodeid'));
      return {
        type: 'node',
        ref: node,
        element
      };
    }

    return null;
  }

  onWheel(event) {
    const actions = this.getActions();
    if (!actions.zoom) { return; }

    event.preventDefault();
    event.stopPropagation();

    const relativeMouse = this.getInternalMousePoint(event);
    const { zoom: initialZoom, offset: initialOffset } = this.state;
    const zoom = Math.max(0.1, initialZoom + (event.deltaY * initialZoom * 0.001));
    const offset = {
      x: (relativeMouse.x + initialOffset.x) * (initialZoom / zoom) - relativeMouse.x,
      y: (relativeMouse.y + initialOffset.y) * (initialZoom / zoom) - relativeMouse.y
    };

    this.enableRepaintEntities([]);
    this.setState({ zoom, offset });
  }

  onMouseMove(event) {
    const { multiselectDrag, canvasDrag, moveItems } = this.getActions();
    const { action, actionType: currentActionType } = this.state;
    const relativeMouse = this.getRelativeMousePoint(event);

    // Select items so draw a bounding box
    if (multiselectDrag && (action instanceof SelectingAction)) {
      this.getDiagramModel().setSelection((ref) => action.containsElement(ref.x, ref.y));
      action.mouseX2 = relativeMouse.x;
      action.mouseY2 = relativeMouse.y;
      this.setState({ action, actionType: 'items-drag-selected' });

    } else if (moveItems && (action instanceof MoveItemsAction)) {

      const { zoom } = this.state;

      // Translate the items on the canvas
      action.selectionData.forEach(model => {
        model.ref.x = model.initialX + ((relativeMouse.x - action.relativeMouse.x) / zoom);
        model.ref.y = model.initialY + ((relativeMouse.y - action.relativeMouse.y) / zoom);
      });

      // Determine actionType, do not override some mouse down
      const disallowed = ['link-created'];
      let actionType = disallowed.indexOf(currentActionType) === -1 ? 'items-moved' : currentActionType;
      if (action.selectionData.length === 1 && action.selectionData[0].ref instanceof NodeModel) {
        actionType = 'node-moved';
      }

      // this.enableRepaintEntities(action.selectedItems);
      this.setState({ actionType });

    } else if (canvasDrag && (this.state.action instanceof MoveCanvasAction)) {

      const { zoom } = this.state;
      const offset = {
        x: action.initialOffset.x + ((relativeMouse.x - action.relativeMouse.x) / zoom),
        y: action.initialOffset.y + ((relativeMouse.y - action.relativeMouse.y) / zoom)
      };

      this.setState({ action, actionType: 'canvas-drag', offset });

    }
  }

  onMouseDown(event) {
    const diagramModel = this.getDiagramModel();
    const { selectItems, multiselect, multiselectDrag } = this.getActions();
    const relativeMouse = this.getRelativeMousePoint(event);
    const mouseElement = this.getMouseElement(event);

    this.clearRepaintEntities();

    // Check if this is the canvas
    if (mouseElement === null) {

      // Check for a multiple selection
      if (multiselectDrag && event.shiftKey) {
        this.setState({
          action: new SelectingAction(this, relativeMouse),
          actionType: 'canvas-shift-select'
        });
      } else {
        // This is a drag canvas event
        diagramModel.setSelection(() => false);
        this.enableRepaintEntities([]);
        this.setState({
          action: new MoveCanvasAction(this, relativeMouse),
          actionType: 'canvas-click'
        });
      }

    } else if (mouseElement.ref instanceof PortModel) {

      const internalMouse = this.getInternalMousePoint(event);
      diagramModel.setSelection(() => false);

      const link = this.createDefaultLink(mouseElement.ref);
      diagramModel.getLinks()[link.id] = link;
      link.setSourceNode(mouseElement.ref.parentNode);
      link.getFirstPoint().updateLocation(internalMouse);
      link.getLastPoint().updateLocation(internalMouse);
      link.getLastPoint().setSelected(true);

      this.setState({
        action: new MoveItemsAction(this, relativeMouse),
        actionType: 'link-created'
      });

    } else if (selectItems) {

      // It's a direct click selection
      let deselect = false;
      const isSelected = mouseElement.ref.isSelected();

      // Clear selections if this wasn't a shift key or a click on a selected element
      if (!event.shiftKey && !isSelected || !multiselect && !isSelected) {
        diagramModel.setSelection(() => false);
      }

      // Is this a deselect or select?
      if (event.shiftKey && mouseElement.ref.isSelected()) {
        mouseElement.ref.setSelected(false);
        deselect = true;
      } else {
        mouseElement.ref.setSelected(true);
      }

      // Get the selected items and filter out point mouseElement
      const isLink = mouseElement.ref instanceof LinkModel;
      const isNode = mouseElement.ref instanceof NodeModel;
      const isPoint = mouseElement.ref instanceof PointModel;
      const selected = diagramModel.getSelectedItems();
      const filtered = _.filter(selected, item => !(item instanceof PointModel));

      // Determine action type
      let actionType = 'items-selected';
      if (deselect && isLink) {
        actionType = 'link-deselected';
      } else if (deselect && isNode) {
        actionType = 'node-deselected';
      } else if (deselect && isPoint) {
        actionType = 'point-deselected';
      } else if ((selected.length === 1 || selected.length === 2 && filtered.length === 1) && isLink) {
        actionType = 'link-selected';
      } else if (selected.length === 1 && isNode) {
        actionType = 'node-selected';
      } else if (selected.length === 1 && isPoint) {
        actionType = 'point-selected';
      }

      this.setState({
        action: new MoveItemsAction(this, relativeMouse),
        actionType
      });
    }
  }

  onMouseUp(event) {
    const diagramModel = this.getDiagramModel();
    const { action, actionType } = this.state;

    const mouseElement = this.getMouseElement(event);

    const actionOutput = {
      type: actionType
    };

    if (mouseElement === null) {
      // No element, this is a canvas event
      // actionOutput.type = 'canvas-event';
      actionOutput.event = event;

    } else if (action instanceof MoveItemsAction) {

      // Add the node model to the output
      actionOutput.model = mouseElement.ref;

      // Check if we going to connect a link to something
      action.selectionData.forEach(model => {
        // Only care about points connecting to things or being created
        if (model.ref instanceof PointModel) {

          // Check if a point was created
          if (mouseElement.element.tagName === 'circle' && actionOutput.type !== 'link-created') {
            actionOutput.type = 'point-created';
          }

          if (mouseElement.ref instanceof NodeModel) {

            // Connect the link
            model.ref.link.setTargetNode(mouseElement.ref);

            // Link was connected to a node, update the output
            actionOutput.type = 'link-connected';
            delete actionOutput.model;
            actionOutput.linkModel = model.ref.getLink();
            actionOutput.nodeModel = mouseElement.ref;
          }

        }
      });
    }

    const attachItems = ['items-selected', 'items-drag-selected', 'items-moved', 'node-deselected', 'link-deselected'];
    if (attachItems.indexOf(actionType) !== -1) {
      actionOutput.items = _.filter(diagramModel.getSelectedItems(), item => !(item instanceof PointModel));
    }
    if (actionType === 'items-moved') {
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
      action: new MoveItemsAction(this, this.getRelativeMousePoint(event))
    });
    return point;
  }

  createDefaultLink(port) {
    const link = new LinkModel();
    link.id =  'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
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


  renderNodeLayerWidget() {
    const { zoom, offset } = this.state;
    const style = {
      transform: `scale(${zoom}) translate(${offset.x}px, ${offset.y}px)`, // eslint-disable-line
        width: '100%',
        height: '100%'
    };

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
      </svg>
    );
  }

  // renderSelector() {
  //   const { action } = this.state;
  //   const offsetWidth = this.refs.canvas && this.refs.canvas.offsetWidth || window.innerWidth;
  //   const offsetHeight = this.refs.canvas && this.refs.canvas.offsetHeight || window.innerHeight;
  //
  //   if (!(action instanceof SelectingAction)) {
  //     return null;
  //   }
  //
  //   const style = {
  //     width: Math.abs(action.mouseX2 - action.mouseX),
  //     height: Math.abs(action.mouseY2 - action.mouseY),
  //   };
  //
  //   if ((action.mouseX2 - action.mouseX) < 0) {
  //     style.right = offsetWidth - action.mouseX;
  //   } else {
  //     style.left = action.mouseX;
  //   }
  //
  //   if ((action.mouseY2 - action.mouseY) < 0) {
  //     style.bottom = offsetHeight - action.mouseY;
  //   } else {
  //     style.top = action.mouseY;
  //   }
  //
  //   return (
  //     <div
  //       className='selector'
  //       style={style}
  //     />
  //   );
  // }


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
        {this.renderNodeLayerWidget()}
        {this.renderLinkLayerWidget()}
        {/*{this.renderSelector()}*/}
      </div>
    );
  }

}
