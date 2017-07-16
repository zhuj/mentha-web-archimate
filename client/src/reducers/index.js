import { combineReducers } from "redux";
import { routerReducer } from "react-router-redux";
import { reducer as formReducer } from "redux-form";

import model from './model'
import rjd from './rjd'

// main reducers
export const reducers = combineReducers({
  routing: routerReducer,
  form: formReducer,
  model: model,
  rjd: rjd
});
