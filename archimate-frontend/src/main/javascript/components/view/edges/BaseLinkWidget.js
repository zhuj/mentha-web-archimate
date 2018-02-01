import React from 'react'
import _ from 'lodash'

import { allMeta } from '../../../meta/index'
import { DefaultLinkWidget } from '../diagram/DefaultWidgets'
import {buildKeyPress, inputFocus} from "../../../utils/textarea";
import * as api from "../../../actions/model.api";

export class BaseLinkWidget extends DefaultLinkWidget {
  
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
  
  getBaseClassName(link) { return super.getClassName(); }
  getClassName(link) {
    let classes = this.getBaseClassName(link);
    const { invalid, derived } = this.props.conceptInfo;
    if (!!invalid) { classes += " invalid"; }
    if (!!derived) { classes += " derived"; }
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

    const tp = obj['_tp'];
    const meta = allMeta[tp];
    if (!!meta) {
      // let specs = "";
      // if (tp === 'accessRelationship') { specs = `(${obj['access']})`; }
      // else if (tp === 'influenceRelationship') { specs = `(${obj['influence']})`; }
      // else if (tp === 'flowRelationship') { specs = `(${obj['flows']})`; }
      return `${meta['name']}\n * ${_.join(meta['help']['summ'], "\n * ")}`;
    }
    return `${tp}`;
  }

  renderEditTitle(link, property) {
    const keyPress = buildKeyPress(
      (title) => {
        this.props.diagram.onChange({
          type: 'title-changed',
          title: title,
          items: [link],
          command: (viewId) => api.modConcept(link.viewObject.concept, { [property]: title })
        });
        link.setSelected(true);
        this.forceUpdate();
      },
      () => {
        link.setSelected(true);
        this.forceUpdate();
      }
    );

    // TODO: const { x, y } = this.props.diagram.toInternalCoordinates(mouseX, mouseY);
    const { x, y } = link.getFirstPoint();
    const conceptInfo = this.getConceptInfo(link);
    return (
      <foreignObject x={x} y={y}>
        <div key="title-edit" className="title-edit">
            <textarea
              defaultValue={conceptInfo[property]}
              ref={inputFocus} onKeyDown={keyPress}
            />
        </div>
      </foreignObject>
    );
  }

}

export const StructuralRelationshipsWidget = ModelLinkWidget;
