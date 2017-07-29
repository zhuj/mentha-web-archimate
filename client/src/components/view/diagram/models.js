import _ from 'lodash';

export const generateId = () => (
  'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
    const r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  })
);

class BaseEntity {
  constructor() {
  }
}

class BaseModel extends BaseEntity {
  constructor() {
    super();
    this.selected = false;
  }

  isSelected() {
    return this.selected;
  }

  setSelected(selected) {
    this.selected = selected;
  }
}

export class PointModel extends BaseModel {

  constructor(link, x, y) {
    super();
    this.x = x;
    this.y = y;
    this.link = link;
  }

  updateLocation(point) {
    this.x = point.x;
    this.y = point.y;
  }

}

export class LinkModel extends BaseModel {
  constructor(id, linkType = 'default') {
    super();
    this.id = id;
    this.linkType = linkType;
    this.points = this.getDefaultPoints();
    this.sourceNode = null;
    this.targetNode = null;
  }

  setSourceNode(node) {
    if (!!this.sourceNode) {
      this.sourceNode.unregisterLink(this);
    }
    if (!!(this.sourceNode = node)) {
      node.registerLink(this);
      this.getFirstPoint().updateLocation(node);
    }
  }

  setTargetNode(node) {
    if (!!this.targetNode) {
      this.targetNode.unregisterLink(this);
    }
    if (!!(this.targetNode = node)) {
      node.registerLink(this);
      this.getLastPoint().updateLocation(node);
    }
  }

  getDefaultPoints(points = []) {
    return [
      new PointModel(this, 0, 0),
      ...points,
      new PointModel(this, 0, 0),
    ];
  }

  setPoints(points) {
    this.points = _.map(points, point => new PointModel(this, point.x, point.y));
  }

  getFirstPoint() {
    return _.first(this.points);
  }

  getLastPoint() {
    return _.last(this.points);
  }

  getPoint(index) {
    const l = this.points.length;
    const i = (l + index) % l;
    return this.points[i];
  }

  getPoints() {
    return this.points;
  }

  addPoint(point, index = 1) {
    const pointModel = new PointModel(this, point.x, point.y);
    this.points.splice(index, 0, pointModel);
    return pointModel;
  }

  getType() {
    return this.linkType;
  }
}

export class PortModel extends BaseModel {
  constructor(node) {
    super();
    this.parentNode = node;
  }
}

export class NodeModel extends BaseModel {
  constructor(id, nodeType = 'default') {
    super();
    this.id = id;
    this.nodeType = nodeType;
    this.x = 0;
    this.y = 0;
    this.width = 100;
    this.height = 40;
    this.zIndex = 0;
    this.links = [];
  }

  registerLink(link) {
    this.links.push(link);
  }

  unregisterLink(link) {
    this.links = _.filter(this.links, (l) => l !== link);
  }

  getLinks() {
    return this.links;
  }

  getType() {
    return this.nodeType;
  }

  asPort() {
    return new PortModel(this);
  }
}

export class DiagramModel extends BaseEntity {
  constructor(id) {
    super();
    this.id = id;
    this.links = {};
    this.nodes = {};
    this.rendered = false;
  }

  getNode(node) {
    if (node instanceof NodeModel) {
      return node;
    }
    return this.nodes[node];
  }

  getLink(link) {
    if (link instanceof LinkModel) {
      return link;
    }
    return this.links[link];
  }

  addLink(link) {
    this.links[link.id] = link;
    return link;
  }

  addNode(node) {
    this.nodes[node.id] = node;
    return node;
  }

  getLinks() {
    return this.links;
  }

  getNodes() {
    return this.nodes;
  }

  setSelection(predicate) {
    _.forEach(this.getNodes(), (node) => {
      node.setSelected(predicate(node));
    });
    _.forEach(this.getLinks(), (link) => {
      let allSelected = predicate(link);
      _.forEach(link.getPoints(), (point) => {
        point.setSelected(predicate(point));
        allSelected |= point.isSelected();
      });
      link.setSelected(allSelected);
    });
  }

  getSelectedItems() {
    const result = [];
    _.forEach(this.getNodes(), (ref) => {
      if (ref.isSelected()) { result.push(ref); }
    });
    _.forEach(this.getLinks(), (ref) => {
      _.forEach(ref.getPoints(), (point) => {
        if (point.isSelected()) { result.push(point); }
      });
      if (ref.isSelected()) { result.push(ref); }
    });
    return result;
  }

  getRepaintEntities(entities) {
    const result = {};
    const addLink = (link) => {
      result[link.id] = true;
      if (link.sourceNode) { result[link.sourceNode.id] = true; }
      if (link.targetNode) { result[link.targetNode.id] = true; }
    };
    entities.forEach(entity => {
      result[entity.id] = true;
      if (entity instanceof NodeModel) {
        _.forEach(entity.getLinks(), link => addLink(link));
      } else if (entity instanceof PointModel) {
        addLink(entity.link);
      }
    });
    return result;
  }

  acquireRuntimeState(prev) {
    _.forEach(this.getNodes(), (ref) => {
      const p_node = prev.getNode(ref.id);
      if (!!p_node) { ref.selected |= !!p_node.selected; }
    });
    _.forEach(this.getLinks(), (ref) => {
      const p_link = prev.getLink(ref.id);
      if (!!p_link) {
        if (ref.points.length === p_link.points.length) {
          const a = 0, b = ref.points.length - 1;
          _.forEach(p_link.points, (p_point, idx) => {
            if (a < idx && idx < b) {
              ref.points[idx].selected = !!p_point.selected
            }
          });
        }
        ref.selected |= !!p_link.selected;
      }
    });
  }

}


