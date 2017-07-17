import * as RJD from '../rjd'
import * as _ from 'lodash'

export class BaseLinkModel extends RJD.LinkModel {
  constructor(linkType = 'default') {
    super(linkType);
  }

  serialize() {
    return _.merge(super.serialize(), {});
  }

  deSerialize(data) {
    super.deSerialize(data);
  }

  deSerializeViewEdge(id, edge) {
    this.deSerialize({
      id: id,
      type: /*edge._tp*/ 'default',
      points: [
        {x: 0, y: 0},
        ...edge.points,
        {x: 0, y: 0}
      ]
    });
  }

}

export class BaseLinkInstanceFactory extends RJD.AbstractInstanceFactory {
  constructor() {
    super('BaseLinkModel');
  }

  getInstance() {
    return new BaseLinkModel();
  }
}
