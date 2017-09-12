import React from 'react'
import _ from 'lodash'

import { allMeta } from '../../../meta/index'
import { DefaultLinkWidget } from '../diagram/DefaultWidgets'

export class BaseLinkWidget extends DefaultLinkWidget {
  constructor(props) { super(props); }
  render() {
    try {
      const {link: {sourceNode: source, targetNode: target}} = this.props;
      if (!!source && !!target) {
        const intersects = (source !== target) && (
          (Math.abs(source.x - target.x) < 0.5 * (source.width + target.width)) &&
          (Math.abs(source.y - target.y) < 0.5 * (source.height + target.height))
        );
        if (intersects) {
          return null;
        }
      }
    } catch(exc) {
      // do nothing, just render the link as usual
      console.error(exc);
    }

    return super.render();
  }
}

export const ViewLinkWidget = BaseLinkWidget;

export class ModelLinkWidget extends BaseLinkWidget {
  constructor(props) { super(props); }
  getBaseClassName(link) { return super.getClassName(); }
  getClassName(link) {
    let classes = this.getBaseClassName(link);
    if (!!this.props.conceptInfo['invalid']) { classes += " invalid"; }
    if (!!this.props.conceptInfo['derived']) { classes += " derived"; }
    return classes;
  }

  getConceptInfo(link) {
    if (link) {
      const obj = link.viewObject;
      if (obj) {
        return obj['.conceptInfo'];
      }
    }
    return null;
  }

  getHint(link) {
    const obj = this.getConceptInfo(link);
    if (!obj) { return null; }

    const meta = allMeta[obj['_tp']];
    if (!!meta) {
      return `${meta['name']}\n * ${_.join(meta['help']['summ'])}`;
    }
    return `${obj['_tp']}`;
  }


}

export const StructuralRelationshipsWidget = ModelLinkWidget;
