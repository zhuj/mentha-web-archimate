import React from "react";
import { Router, Route, IndexRoute } from "react-router";
import { history } from "./store.js";

import AppFrame from "./components/AppFrame";
import HomePage from "./components/HomePage";
import ModelPage from "./components/ModelPage";
import NotFound from "./components/NotFound";

// build the router
const router = (
  <Router onUpdate={() => window.scrollTo(0, 0)} history={history}>
    <Route path="/" component={AppFrame}>
      <IndexRoute component={HomePage}/>
      <Route path="/model/:id" component={ModelPage} />
      <Route path="*" component={NotFound}/>
    </Route>
  </Router>
);

// export
export { router };
