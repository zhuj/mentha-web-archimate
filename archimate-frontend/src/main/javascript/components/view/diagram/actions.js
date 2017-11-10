import _ from 'lodash'
import {PointModel} from "./models";

/** Base action class */
class BaseAction {
  constructor(diagram, relativeMouse) {
    this.diagram = diagram;
    this.relativeMouse = relativeMouse;
    this.ms = (new Date()).getTime();
  }
}


const MouseDownActionThreshold = 3.0;

/** */
export class MouseDownAction extends BaseAction {
  constructor(diagram, relativeMouse, clientXY, mouseElement, shiftKey) {
    super(diagram, relativeMouse);
    this.clientXY = { clientX: clientXY.clientX, clientY: clientXY.clientY };
    this.mouseElement = mouseElement;
    this.shiftKey = !!shiftKey;
  }

  isSignificant(relativeMouse) {
    return (
      (Math.abs(this.relativeMouse.x - relativeMouse.x) > MouseDownActionThreshold) ||
      (Math.abs(this.relativeMouse.y - relativeMouse.y) > MouseDownActionThreshold)
    );
  }

}

/** */
export class MoveCanvasAction extends BaseAction {
  constructor(diagram, relativeMouse) {
    super(diagram, relativeMouse);
    this.initialOffset = diagram.getOffset();
  }
}

const isLinkLastPointSelection = (selectedItems) => {
  if (selectedItems.length == 1) {
    const item = selectedItems[0];
    if (item instanceof PointModel) {
      return item.link.getLastPoint() === item;
    }
  }
  return false;
};

/** */
export class MoveItemsAction extends BaseAction {
  constructor(diagram, relativeMouse) {
    super(diagram, relativeMouse);
    this.selectedItems = diagram.getDiagramModel().getSelectedItems();
    this.linkLastPointSelection = isLinkLastPointSelection(this.selectedItems);
    this.selectionData = _.map(this.selectedItems, item => ({
       ref: item,
       initialX: item.x,
       initialY: item.y,
    }));
    diagram.enableRepaintEntities(this.selectedItems);
  }
}

/** */
export class ResizeItemAction extends BaseAction {
  constructor(diagram, relativeMouse, kind) {
    super(diagram, relativeMouse);
    this.kind = kind;
    this.selectedItems = diagram.getDiagramModel().getSelectedItems();
    this.selectionData = _.map(this.selectedItems, item => ({
      ref: item,
      initialX: item.x,
      initialY: item.y,
      initialW: item.width || 0,
      initialH: item.height || 0
    }));
    diagram.enableRepaintEntities(this.selectedItems);
  }
}

/** */
export class SelectingAction extends BaseAction {
  constructor(diagram, relativeMouse, internalMouse) {
    super(diagram, relativeMouse);
    this.internalMouse1 = internalMouse;
    this.internalMouse2 = internalMouse;
  }

  containsElement(x, y) {
    const { x:mouseX1, y:mouseY1 } = this.internalMouse1;
    const { x:mouseX2, y:mouseY2 } = this.internalMouse2;

    const x1 = Math.min(mouseX1, mouseX2), x2 = Math.max(mouseX1, mouseX2);
    const y1 = Math.min(mouseY1, mouseY2), y2 = Math.max(mouseY1, mouseY2);
    return ((x1 <= x) && (x <= x2) && (y1 <= y) && (y <= y2));
  }
}

