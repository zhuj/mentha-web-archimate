import _ from 'lodash';
import * as actions from "../actions/index"

const postProcessModel = (model) => ({
  ...model,
  views: _.mapValues(model.views, (view) => ({
    ...view,
    nodes: _.mapValues(view.nodes, (node) => {
      if (!!node.concept) { return { ...node, conceptInfo: model.nodes[node.concept] }; }
      return node;
    }),
    edges: _.mapValues(view.edges, (edge) => {
      if (!!edge.concept) { return {...edge, conceptInfo: model.edges[edge.concept]}; }
      return edge;
    })
  }))
});

const applyNoop = (model, payload) => {
    return model;
};

const applyObject = (model, payload) => {
  const apply = (model, obj) => {
    const tp = obj['_tp'].toLowerCase();
    if (tp === 'model') {
      return obj;
    }
    // TODO: apply other types
    return model;
  };

  const ids = Object.getOwnPropertyNames(payload);
  for (const id of ids) {
    const obj = payload[id];
    model = apply(model, obj);
  }

  return postProcessModel(model);
};

const applyCommit = (model, payload) => {

  const apply = (model, obj) => {
    model = { ...model };

    for (const name of Object.getOwnPropertyNames(obj)) {
      const prefix = name.substring(0, 1);
      const postfix = name.substring(1);
      const value = obj[name];
      switch (prefix) {
        case "=":
        case "+": {
          model[postfix] = value;
          break;
        }
        case "-": {
          delete model[postfix];
          break;
        }
        case "@": {
          model[postfix] = apply(model[postfix] || {}, value);
          break;
        }
      }
    }

    return model;
  };

  model = apply(model, payload);
  return postProcessModel(model);
};

const applyError = (model, payload) => {
    return model;
};

const selectViewObjects = (model, viewId, selection) => applyCommit(
  model, {
    [`@views`]: {
      [`@${viewId}`]: {
        [`=selection`]: selection
      }
    }
  }
);

const getInitialState = () => ({
  nodes: {},
  edges: {},
  views: {}
});

const reducer = (state = getInitialState(), action) => {
  switch (action.type) {
    // external
    case actions.MODEL_NOOP_RECEIVED: return applyNoop(state, action.payload);
    case actions.MODEL_OBJECT_RECEIVED: return applyObject(state, action.payload);
    case actions.MODEL_COMMIT_RECEIVED: return applyCommit(state, action.payload);
    case actions.MODEL_ERROR_RECEIVED: return applyError(state, action.payload);

    // internal
    case actions.VIEW_SELECT_OBJECTS: return selectViewObjects(state, action.viewId, action.selection);
  }
  return state;
};

export default reducer
