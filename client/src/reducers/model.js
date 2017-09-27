import _ from 'lodash';
import * as actions from "../actions/index"

const hash = (v) => {

  // h = 31*h + str[i]
  const _add = (h, v) => (((h << 5) - h) +  v) & 0xFFFFFFFF;

  const _str = (str) => {
    let h = 0;
    for (let i=0,l=str.length; i<l; i++) {
      h = _add(h, str.charCodeAt(i));
    }
    return h;
  };

  const _obj = (obj) => {
    let h = 0;
    for (const k of Object.getOwnPropertyNames(obj).sort()) {
      if (!k.startsWith('.')) {
        h = _add(h, _str(k) ^ _hash(obj[k]));
      }
    }
    return h;
  };

  const _arr = (arr) => {
    let h = 0;
    for (let i=0, l =arr.length; i<l; i++) {
      h = _add(h, _hash(arr[i]));
    }
    return h;
  };

  const _bool = (value) => (value ? 1231 : 1237);

  const _num = (num) => Math.trunc(num*1024) & 0xFFFFFFFF;

  const _hash = (v) => {
    if (null === v) return 0;
    switch (typeof(v)) {
      case 'undefined': return 0;
      case 'boolean': return _bool(v);
      case 'string': return _str(v);
      case 'number': return _num(v);
    }
    if (Array.isArray(v)) { return _arr(v); }
    return _obj(v);
  };

  console.time('hash');
  try { return _hash(v); }
  finally { console.timeEnd('hash'); }
};

const postProcessModel = (model) => ({
  ...model,
  views: _.mapValues(model.views, (view) => ({
    ...view,
    nodes: _.mapValues(view.nodes, (node, id) => {
      if (!!node.concept) { return { ...node, ['.conceptInfo']: model.nodes[node.concept] }; }
      return node;
    }),
    edges: _.mapValues(view.edges, (edge, id) => {
      if (!!edge.concept) { return {...edge, ['.conceptInfo']: model.edges[edge.concept]}; }
      return edge;
    })
  })),
  // TODO: ['.hash-local']: hash(model)
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
    model = { ...model }; // make it writeable
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
        case ".": {
          model[name] = value;
          break;
        }
        default:
          throw `Unexpected prefix: '${prefix}' for ${obj}`;
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
        [`=.selection`]: selection
      }
    }
  }
);

const getInitialState = () => ({
  nodes: {},
  edges: {},
  views: {}
});

const debug = (type, v) => {
  const timer = `reducer-model-${type}`;
  console.time(timer);
  try { return v(); }
  finally { console.timeEnd(timer); }
};

const reducer = (state = getInitialState(), action) => {
  const type = action.type;
  switch (type) {
    // external
    case actions.MODEL_NOOP_RECEIVED: return debug(type, ()=>applyNoop(state, action.payload));
    case actions.MODEL_OBJECT_RECEIVED: return debug(type, ()=>applyObject(state, action.payload));
    case actions.MODEL_COMMIT_RECEIVED: return debug(type, ()=>applyCommit(state, action.payload));
    case actions.MODEL_ERROR_RECEIVED: return debug(type, ()=>applyError(state, action.payload));

    // internal TODO: don't store it in the model
    case actions.VIEW_SELECT_OBJECTS: return debug(type, ()=>selectViewObjects(state, action.viewId, action.selection));
  }
  return state;
};

export default reducer
