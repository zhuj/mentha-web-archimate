import _ from 'lodash'

export const composite = (commands) => {
  if (commands.length == 1) { return commands[0]; }
  return {
    'composite': _.chain(commands).reduce((o, v, idx) => { o[idx] = v; return o; }, {}).value()
  };
};

export const addElement = (json) => ({ 'add-element' : json });
export const addConnector = (json) => ({ 'add-connector' : json });
export const addRelationship = (json) => ({ 'add-relationship' : json });

export const moveViewNode = (viewId, voId, pos, size) => ({
  'mov-view-node': {
    viewId: viewId,
    id: voId,
    pos: { x:pos.x, y:pos.y },
    size: { width: size.width, height: size.height }
  }
});

export const moveViewEdge = (viewId, voId, points) => ({
  'mov-view-edge': {
    viewId: viewId,
    id: voId,
    points: _.chain(points).map((p) => ({ x:p.x, y:p.y })).value()
  }
});

export const addViewNodeConcept = (viewId, concept, pos, size) => ({
  'add-view-node-concept': {
    viewId: viewId,
    concept: concept,
    pos: { x:pos.x, y:pos.y },
    size: { width: size.width, height: size.height }
  }
});

export const addViewNotes = (viewId, pos, size) => ({
  'add-view-notes': {
    viewId: viewId,
    pos: { x:pos.x, y:pos.y },
    size: { width: size.width, height: size.height }
  }
});