import React from 'react'
import * as RJD from '../rjd'

import { PORT_NAME } from './BasePortModel'

export class BaseNodeWidget extends React.Component {

  onRemove() {
    const { node, diagramEngine } = this.props;
    node.remove();
    diagramEngine.forceUpdate();
  }

  getNodeClassName() {
    const { node } = this.props;
    return "basic-node " + node.nodeType;
  }

  getNodeStyle() {
    return {};
  }

  render() {
    const { node } = this.props;
    return (
      <div className={this.getNodeClassName()} style={this.getNodeStyle()}>
        <div className='title'>
          <div className='name'>
            {node.getNodeName()}
          </div>
          {/*<div className='fa fa-close' onClick={this.onRemove.bind(this)} />*/}
        </div>
        <div className='ports'>
          <div className='center'>
            <RJD.PortWidget name={PORT_NAME} node={node}/>
          </div>
        </div>
      </div>
    );
  }
}

export class BaseNodeWidgetFactory extends RJD.NodeWidgetFactory {
  constructor() {
    super('base-node');
  }

  generateReactWidget(diagramEngine, node) {
    return (
      <BaseNodeWidget node={node} diagramEngine={diagramEngine} />
    );
  }
}
