import React from "react";
import { Route } from "react-router";
import { ConnectedRouter } from "react-router-redux";
import { history } from "./store.js";

import AppFrame from "./components/AppFrame";
import HomePage from "./components/HomePage";
import ModelPage from "./components/ModelPage";
import NotFound from "./components/NotFound";

// build the router
const router = (
  <ConnectedRouter onUpdate={() => window.scrollTo(0, 0)} history={history}>
    <AppFrame>
      <Route exact path="/" component={HomePage}/>
      <Route path="/model/:id" component={ModelPage} />
      {/*<Route path="/model/:id/:view" component={ModelPage} />*/}
      <Route path="*" component={NotFound}/>
    </AppFrame>
  </ConnectedRouter>
);

// export
export { router };
