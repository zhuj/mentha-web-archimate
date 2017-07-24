import React from 'react'
import _ from 'lodash'

import { MoveCanvasAction, MoveItemsAction, ResizeItemAction, SelectingAction } from './actions'
import { NodeModel, PortModel, LinkModel, PointModel } from './models'
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
    const { node, wide } = this.props;
    const width = wide ? node.width : 16;
    const height = wide ? node.height : 16;
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

    const relativeMouse = this.getInternalMousePoint(event);
    const { zoom: initialZoom, offset: initialOffset } = this.state;
    const zoom = Math.max(0.1, initialZoom - (event.deltaY * initialZoom * 0.001));
    const offset = {
      x: (relativeMouse.x + initialOffset.x) * (initialZoom / zoom) - relativeMouse.x,
      y: (relativeMouse.y + initialOffset.y) * (initialZoom / zoom) - relativeMouse.y
    };

    this.enableRepaintEntities([]);
    this.setState({ zoom, offset });
  }

  onMouseMove(event) {
    const { action, actionType: currentActionType } = this.state;
    const relativeMouse = this.getRelativeMousePoint(event);

    // Select items so draw a bounding box
    if (action instanceof SelectingAction) {
      this.getDiagramModel().setSelection((ref) => action.containsElement(ref.x, ref.y));
      action.mouseX2 = relativeMouse.x;
      action.mouseY2 = relativeMouse.y;
      this.setState({ action, actionType: 'items-drag-selected' });

    } else if (action instanceof ResizeItemAction) {

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
      if (action.selectionData.length === 1 && action.selectionData[0].ref instanceof NodeModel) {
        actionType = 'node-sized';
      }

      // this.enableRepaintEntities(action.selectedItems);
      this.setState({ actionType });

    } else if (action instanceof MoveItemsAction) {

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
      if (action.selectionData.length === 1 && action.selectionData[0].ref instanceof NodeModel) {
        actionType = 'node-moved';
      }

      // this.enableRepaintEntities(action.selectedItems);
      this.setState({ actionType });

    } else if (this.state.action instanceof MoveCanvasAction) {

      const { zoom } = this.state;
      const offset = {
        x: action.initialOffset.x + ((relativeMouse.x - action.relativeMouse.x) / zoom),
        y: action.initialOffset.y + ((relativeMouse.y - action.relativeMouse.y) / zoom)
      };

      this.setState({ action, actionType: 'canvas-drag', offset });

    }
  }

  onMouseDown(event) {

    // TODO: make it better
    if (event.buttons !== 1) { return; } // only a single button
    if (event.button !== 0) { return; } // only the left button

    const diagramModel = this.getDiagramModel();
    const relativeMouse = this.getRelativeMousePoint(event);
    const mouseElement = this.getMouseElement(event);

    this.clearRepaintEntities();

    // Check if this is the canvas
    if (mouseElement === null) {

      // Check for a multiple selection
      if (event.shiftKey) {
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

    } else if (mouseElement.type === ELTP.NODE_PORT) {

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

    } else if (mouseElement.type === ELTP.NODE_RESIZE) {

      mouseElement.ref.setSelected(true);

      this.setState({
        action: new ResizeItemAction(this, relativeMouse, mouseElement.kind),
        actionType: 'node-selected'
      });

    } else {

      // It's a direct click selection
      let deselect = false;
      const isSelected = mouseElement.ref.isSelected();

      // Modify selection
      diagramModel.setSelection((m) => m === mouseElement.ref);
      // if (event.shiftKey) {
      //   if (isSelected) {
      //     mouseElement.ref.setSelected(false);
      //     deselect = true;
      //   } else {
      //     mouseElement.ref.setSelected(true);
      //   }
      // } else {
      //   diagramModel.setSelection((m) => m === mouseElement.ref);
      // }

      // Get the selected items and filter out point mouseElement
      const isLink = mouseElement.type === ELTP.LINK;
      const isNode = mouseElement.type === ELTP.NODE;
      const isPoint = mouseElement.type === ELTP.LINK_POINT;

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

    } else if (action instanceof ResizeItemAction) {

      // Add the node model to the output
      actionOutput.model = mouseElement.ref;

    } else if (action instanceof MoveItemsAction) {

      // Add the node model to the output
      actionOutput.model = mouseElement.ref;

      // Check if we going to connect a link to something
      if (action.linkLastPointSelection) {
        const link = action.selectedItems[0].link;

        // Check if a point was created
        if (mouseElement.element.tagName === 'circle' && actionOutput.type !== 'link-created') {
          actionOutput.type = 'point-created';
        }

        if (mouseElement.ref instanceof PortModel) {

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
    if (attachItems.indexOf(actionType) !== -1) {
      actionOutput.items = _.filter(diagramModel.getSelectedItems(), item => !(item instanceof PointModel));
    }
    if (actionType === 'items-moved' || actionType === 'items-sized') {
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

  generatePorts(mode) {
    const { action } = this.state;
    const wide = (action instanceof MoveItemsAction) && (action.linkLastPointSelection);
    return _.map(this.getDiagramModel().getNodes(), node => (
      <PortWrapper
        key={node.id}
        node={node}
        diagram={this}
        wide={wide}
      />
    ));
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
        { /*this.generatePorts('svg')*/ }
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
        <div className="diagram-center">
        {this.renderNodeLayerWidget()}
        {this.renderLinkLayerWidget()}
        {/*{this.renderSelector()}*/}
        </div>
      </div>
    );
  }

}
