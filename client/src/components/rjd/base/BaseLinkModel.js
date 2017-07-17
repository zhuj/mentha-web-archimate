import * as RJD from '../rjd'
import * as _ from 'lodash'


const TYPE = 'base-link';
export class BaseLinkModel extends RJD.LinkModel {
  constructor(linkType = TYPE) {
    super(linkType);
  }

  getLinkType() {
    return TYPE;
  }

  serialize() {
    return _.merge(super.serialize(), {});
  }

  deSerialize(data) {
    super.deSerialize(data);
  }

  deSerializeSource(id, edge) {
    return {
      id: id,
      type: this.getLinkType(),
      points: [
        {x: 0, y: 0},
        ...edge.points,
        {x: 0, y: 0}
      ]
    };
  }

  deSerializeViewEdge(id, edge) {
    this.deSerialize(this.deSerializeSource(id, edge));
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
