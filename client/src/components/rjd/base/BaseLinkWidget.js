import React from 'react'
import * as RJD from '../rjd'
import _ from 'lodash'

export class BaseLinkWidget extends RJD.DefaultLinkWidget {
  constructor(props) {
    super(props);
  }

  getPaths() {
    const { points } = this.props.link;
    if (points.length > 2) {
      return this.drawAdvancedLine();
    }
    return this.drawLine();
  }

  /** @override RJD.DefaultLinkWidget.generateLink */
  generateLink(extraProps) {
    const {link} = this.props;
    const {selected} = this.state;
    const className = link.linkType;
    const bottom = (
      <path
        className={className + ((selected || link.isSelected()) ? ' selected' : '')}
        {...extraProps}
      />
    );

    const top = (
      <path
        className={className}
        strokeLinecap={'round'}
        data-linkid={link.getID()}
        strokeOpacity={selected ? 0.1 : 0}
        strokeWidth={20}
        onMouseLeave={() => this.setState({selected: false})}
        onMouseEnter={() => this.setState({selected: true})}
        onContextMenu={event => {
          event.preventDefault();
          this.props.link.remove();
        }}
        {...extraProps}
      />
    );

    return (
      <g key={`link-${extraProps.id}`}>
        {bottom}
        {top}
      </g>
    );
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

