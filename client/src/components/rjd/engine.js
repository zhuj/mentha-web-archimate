import React from 'react';
import * as RJD from './rjd';

import { BaseNodeInstanceFactory } from './base/BaseNodeModel'
import { BasePortInstanceFactory } from './base/BasePortModel'
import { BaseLinkInstanceFactory } from './base/BaseLinkModel'

import { BaseNodeWidgetFactory } from './base/BaseNodeWidget'

export default () => {

  // Setup the diagram engine
  const diagramEngine = new RJD.DiagramEngine();

  // register widget factories (for nodes)
  diagramEngine.registerNodeFactory(new RJD.DefaultNodeFactory());
  diagramEngine.registerNodeFactory(new BaseNodeWidgetFactory());

  // register widget factories (for links)
  diagramEngine.registerLinkFactory(new RJD.DefaultLinkFactory());

  // Register instance/model factories
  diagramEngine.registerInstanceFactory(new BaseNodeInstanceFactory());
  diagramEngine.registerInstanceFactory(new BasePortInstanceFactory());
  diagramEngine.registerInstanceFactory(new BaseLinkInstanceFactory());

  diagramEngine.registerInstanceFactory(new RJD.DefaultNodeInstanceFactory());
  diagramEngine.registerInstanceFactory(new RJD.DefaultPortInstanceFactory());
  diagramEngine.registerInstanceFactory(new RJD.LinkInstanceFactory());

  diagramEngine.setDiagramModel(new RJD.DiagramModel());
  return diagramEngine;
}