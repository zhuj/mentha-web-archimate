import * as RJD from 'react-js-diagrams';
import * as _ from 'lodash';

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
      points: edge.points
    });
  }

}

export class BaseLinkInstanceFactory extends RJD.LinkInstanceFactory {
  constructor() {
    super('BaseLinkModel');
  }

  getInstance() {
    return new BaseLinkModel();
  }
}
