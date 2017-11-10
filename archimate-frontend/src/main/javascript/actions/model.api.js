import _ from 'lodash'

export const composite = (commands) => {
  if (!Array.isArray(commands)) { return commands; }
  if (commands.length === 1) { return commands[0]; }
  return {
    'composite': _.chain(commands).reduce((o, v, idx) => { o[idx] = v; return o; }, {}).value()
  };
};

export const addElement = (json) => ({ 'add-element' : json });
export const addConnector = (json) => ({ 'add-connector' : json });
export const addRelationship = (json) => ({ 'add-relationship' : json });

export const modViewObject = (viewId, voId, payload) => ({
  'mod-view-object': {
    viewId: viewId,
    id: voId,
    ...payload
  }
});

export const modConcept = (id, payload) => ({
  'mod-concept': {
    id: id,
    ...payload
  }
});

export const deleteViewObject = (viewId, voId) => ({
  'del-view-object': {
    viewId: viewId,
    id: voId
  }
});

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

export const addViewRelationship = (viewId, concept, src, dst) => ({
  'add-view-relationship': {
    viewId: viewId,
    concept: concept,
    src, dst
  }
});

export const addViewNotes = (viewId, pos, size) => ({
  'add-view-notes': {
    viewId: viewId,
    pos: { x:pos.x, y:pos.y },
    size: { width: size.width, height: size.height }
  }
});

export const addViewConnection = (viewId, src, dst) => ({
  'add-view-connection': {
    viewId: viewId,
    src, dst
  }
});

export const addView = (viewpoint, name, payload={}) => ({
  'add-view': {
    viewpoint: viewpoint,
    name: name,
    ...payload
  }
});

export const modView = (id, name, payload={}) => ({
  'mod-view': {
    id: id,
    name: name,
    ...payload
  }
});
