import React from 'react'

import { DefaultLinkWidget } from '../diagram/DefaultWidgets'

const BaseLinkWidget = DefaultLinkWidget;

export const ViewLinkWidget = BaseLinkWidget;

export class ModelLinkWidget extends BaseLinkWidget {
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

export const StructuralRelationshipsWidget  = ModelLinkWidget;
