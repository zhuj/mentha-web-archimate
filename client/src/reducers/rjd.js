import { RJD_NODE_SELECTED, RJD_UPDATE_MODEL } from "../actions"

const getInitialState = () => ({
});

const reducer = (state = getInitialState(), action) => {
  switch (action.type) {
    case RJD_NODE_SELECTED: {
      const {id} = action;
      const rjd = Object.assign({}, state[id] || {}, {selectedNode: action.node});
      return {...state, [id]: rjd};
    }
    case RJD_UPDATE_MODEL: {
      const {id} = action;
      const rjd = Object.assign({}, state[id] || {}, {model: action.model, ...action.props});
      return {...state, [id]: rjd};
    }
  }
  return state;
};

export default reducer
