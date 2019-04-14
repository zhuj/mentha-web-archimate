import "babel-polyfill";
import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import { store } from "./store.js";
import { router } from "./router.js";

import blue from '@material-ui/core/colors/blue';
import pink from '@material-ui/core/colors/pink';
import { createMuiTheme } from '@material-ui/core/styles';
import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';

const theme = createMuiTheme({
  palette: {
    primary: blue,
    secondary: pink,
    type: 'dark',
  },
  typography: {
    useNextVariants: true,
  }
});

// render the main component
ReactDOM.render(
  <MuiThemeProvider theme={theme}>
    <Provider store={store}>
      {router}
    </Provider>
  </MuiThemeProvider>,
  document.getElementById('app')
);
