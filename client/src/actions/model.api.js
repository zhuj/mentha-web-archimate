import _ from 'lodash'

export const composite = (commands) => {
  if (commands.length == 1) { return commands[0]; }
  return {
    'composite': _.chain(commands).reduce((o, v, idx) => { o[idx] = v; return o; }, {}).value()
  };
};

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

