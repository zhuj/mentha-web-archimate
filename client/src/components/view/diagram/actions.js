import _ from 'lodash'
import {PointModel} from "./models";

class BaseAction {
  constructor(diagram, relativeMouse) {
    this.diagram = diagram;
    this.relativeMouse = relativeMouse;
    this.ms = (new Date()).getTime();
  }
}

export class MoveCanvasAction extends BaseAction {
  constructor(diagram, mouseX, mouseY) {
    super(diagram, mouseX, mouseY);
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

export class SelectingAction extends BaseAction {
  constructor(diagram, relativeMouse) {
    super(diagram, relativeMouse);
    this.relativeMouse2 = relativeMouse;
  }

  containsElement(x, y) {
    const { x: elX, y: elY } = this.diagram.toRelativeCoordinates(x, y);
    const { mouseX1, mouseY1 } = this.relativeMouse;
    const { mouseX2, mouseY2 } = this.relativeMouse2;
    return (
      ((mouseX2 < mouseX1) ? elX < mouseX1 : elX > mouseX1) &&
      ((mouseX2 < mouseX1) ? elX > mouseX2 : elX < mouseX2) &&
      ((mouseY2 < mouseY1) ? elY < mouseY1 : elY > mouseY1) &&
      ((mouseY2 < mouseY1) ? elY > mouseY2 : elY < mouseY2)
    );
  }
}

