import React from 'react';
import * as RJD from 'react-js-diagrams';

import { BaseNodeInstanceFactory } from './base/BaseNodeModel'
import { BasePortInstanceFactory } from './base/BasePortModel'
import { BaseLinkInstanceFactory } from './base/BaseLinkModel'

export default () => {

  // Setup the diagram engine
  const diagramEngine = new RJD.DiagramEngine();

  // register widget factories
  diagramEngine.registerNodeFactory(new RJD.DefaultNodeFactory());
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