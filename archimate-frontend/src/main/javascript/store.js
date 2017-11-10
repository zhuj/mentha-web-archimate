import { createStore, applyMiddleware, compose } from "redux";
import { routerMiddleware } from "react-router-redux";
import createSagaMiddleware from "redux-saga";
import freeze from "redux-freeze";
import thunk from 'redux-thunk'

import { createModelMiddleware } from './middleware/model/index'
import { reducers } from "./reducers/index";
import { sagas } from "./sagas/index";

import createHistory from 'history/createBrowserHistory';
import qhistory from 'qhistory';
import { parse, stringify } from 'qs';
const history = qhistory(createHistory(), stringify, parse);

// add the middlewares
let middlewares = [];

// add redux-thunk
middlewares.push(thunk);

// add the router middleware
middlewares.push(routerMiddleware(history));

// add the saga middleware
const sagaMiddleware = createSagaMiddleware();
middlewares.push(sagaMiddleware);

// add web-socket middleware for model
const modelMiddleware = createModelMiddleware();
middlewares.push(modelMiddleware);

// add the freeze dev middleware
if (process.env.NODE_ENV !== 'production') {
  middlewares.push(freeze);
}

// apply the middleware
let middleware = applyMiddleware(...middlewares);

// add the redux dev tools
if (process.env.NODE_ENV !== 'production' && window.devToolsExtension) {
  middleware = compose(middleware, window.devToolsExtension());
}

// create the store
const store = createStore(reducers, middleware);
sagaMiddleware.run(sagas);

// export
export { store, history };
