import React from 'react'
import * as RJD from '../rjd'
import _ from 'lodash'

export class BaseLinkWidget extends RJD.DefaultLinkWidget {
  constructor(props) { super(props); }

  getPaths() {
    const { points } = this.props.link;
    if (points.length > 2) {
      return this.drawAdvancedLine();
    }
    return this.drawLine();
  }

  render() {
    return (
      <g>
        {this.getPaths()}
      </g>
    );
  }

}

export class BaseLinkWidgetFactory extends RJD.LinkWidgetFactory {
  constructor() { super("base-link"); }
  generateReactWidget(diagramEngine, link) {
    return (
      <BaseLinkWidget link={link} diagramEngine={diagramEngine} />
    );
  }
}

