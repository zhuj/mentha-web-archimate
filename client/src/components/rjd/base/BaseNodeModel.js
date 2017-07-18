import * as RJD from '../rjd'
import * as _ from 'lodash'

import { BasePortModel } from './BasePortModel'

export class BaseNodeModel extends RJD.NodeModel {
  constructor() {
    super('base-node');
    this.addPort(new BasePortModel());
    this.width = 100;
    this.height = 40;
  }

  getNodeName() {
    return "";
  }

  defaultNodeType() {
    return 'base-node';
  }

  serialize() {
    return _.merge(super.serialize(), {});
  }

  deSerialize(object) {
    super.deSerialize(object);
  }

  deSerializeSource(id, node) {
    return {
      id: id,
      type: this.defaultNodeType(),
      x: node.pos.x,
      y: node.pos.y,
      width: node.size.width,
      height: node.size.height
    };
  }

  deSerializeViewNode(id, node) {
    const source = this.deSerializeSource(id, node);
    this.deSerialize(source);
    this.width = source.width;
    this.height = source.height;
  }

  getInPorts() {
    return _.filter(this.ports,(portModel) => true);
  }

  getOutPorts() {
    return _.filter(this.ports,(portModel) => true);
  }


}

export class BaseNodeInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() {
    super('BaseNodeModel');
  }

  getInstance() {
    return new BaseNodeModel();
  }
}

