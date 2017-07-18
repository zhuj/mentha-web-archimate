import React from 'react';
import * as RJD from './rjd';

import { BaseNodeInstanceFactory } from './base/BaseNodeModel'
import { BasePortInstanceFactory } from './base/BasePortModel'
import { BaseLinkInstanceFactory } from './base/BaseLinkModel'

import { BaseNodeWidgetFactory } from './base/BaseNodeWidget'
import { BaseLinkWidgetFactory } from './base/BaseLinkWidget'

import { registerEdges } from './edges/engine'
import { registerNodes } from './nodes/engine'

export default (model) => {

  // Setup the diagram engine
  const diagramEngine = new RJD.DiagramEngine();

  // register all nodes & edges
  registerNodes(diagramEngine);
  registerEdges(diagramEngine);

  // register widget factories (for nodes)
  diagramEngine.registerNodeFactory(new BaseNodeWidgetFactory());

  // register widget factories (for links)
  diagramEngine.registerLinkFactory(new BaseLinkWidgetFactory());

  // Register instance/model factories
  diagramEngine.registerInstanceFactory(new BaseNodeInstanceFactory());
  diagramEngine.registerInstanceFactory(new BasePortInstanceFactory());
  diagramEngine.registerInstanceFactory(new BaseLinkInstanceFactory());

  // default nodes, links and ports
  diagramEngine.registerNodeFactory(new RJD.DefaultNodeFactory());
  diagramEngine.registerLinkFactory(new RJD.DefaultLinkFactory());
  diagramEngine.registerInstanceFactory(new RJD.DefaultNodeInstanceFactory());
  diagramEngine.registerInstanceFactory(new RJD.DefaultPortInstanceFactory());
  diagramEngine.registerInstanceFactory(new RJD.LinkInstanceFactory());

  // abstract links - that's what we will create initially
  diagramEngine.setLinkInstanceFactory('viewAbstractLink');

  diagramEngine.setDiagramModel(model);
  return diagramEngine;
}