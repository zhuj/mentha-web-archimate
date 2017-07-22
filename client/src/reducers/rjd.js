import { RJD_UPDATE_MODEL } from "../actions"

const getInitialState = () => ({
});

const reducer = (state = getInitialState(), action) => {
  switch (action.type) {
    case RJD_UPDATE_MODEL: {
      const {id} = action;
      const rjd = { ...state[id] || {}, model: action.model, ...action.props };
      return {...state, [id]: rjd};
    }
  }
  return state;
};

export default reducer
