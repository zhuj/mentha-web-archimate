import _ from 'lodash';
import * as RJD from 'react-js-diagrams';

import { BasePortModel } from './BasePortModel';

export class BaseNodeModel extends RJD.NodeModel {
  constructor() {
    super('connection');
    this.addPort(new BasePortModel());
    this.width = 100;
    this.height = 40;
  }

  getName() {

  }

  serialize() {
    return _.merge(super.serialize(), {});
  }

  deSerialize(object) {
    super.deSerialize(object);
  }

  deSerializeViewNode(id, node) {
    this.deSerialize({
      id: id,
      type: /*node._tp*/ 'default',
      x: node.pos.x,
      y: node.pos.y
    });
    this.width = node.size.width;
    this.height = node.size.height;
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

