import * as RJD from 'react-js-diagrams';
import * as _ from 'lodash';

export const PORT_NAME = "port";

export class BasePortModel extends RJD.PortModel {
  constructor() {
    super(PORT_NAME);
  }

  serialize() {
    return _.merge(super.serialize(), {});
  }

  deSerialize(data) {
    super.deSerialize(data);
  }

  deSerializeViewNode(id, node) {
    this.deSerialize({
      id: id+"@port"
    });
  }

}


export class BasePortInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() {
    super('BasePortModel');
  }

  getInstance() {
    return new BasePortModel();
  }
}
