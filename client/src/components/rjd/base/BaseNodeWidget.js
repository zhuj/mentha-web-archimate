import React from 'react';
import * as RJD from 'react-js-diagrams';
import { PORT_NAME } from 'BasePortModel';

export class BaseNodeWidget extends React.Component {
  onRemove() {
    const { node, diagramEngine } = this.props;
    node.remove();
    diagramEngine.forceUpdate();
  }

  getClassName() {
    return "node-base";
  }

  getStyle() {
    return {};
  }

  render() {
    const { node } = this.props;
    return (
      <div className={this.getClassName()} style={this.getStyle()}>
        <div className='title'>
          <div className='name'>
            {node.getName()}
          </div>
          <div className='fa fa-close' onClick={this.onRemove.bind(this)} />
        </div>
        <div className='ports'>
          <div className='node-port'>
            <RJD.PortWidget name={PORT_NAME} node={node}/>
          </div>
        </div>
      </div>
    );
  }
}

export const BaseNodeWidgetFactory = React.createFactory(BaseNodeWidget);
