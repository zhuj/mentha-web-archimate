import React from "react";
import { connect } from "react-redux";

import "../stylesheets/main.scss";

import { withStyles, createStyleSheet } from 'material-ui/styles';
const styleSheet = createStyleSheet('AppFrame', theme => ({
  '@global': {
    html: {
      boxSizing: 'border-box',
    },
    '*, *:before, *:after': {
      boxSizing: 'inherit',
    },
    body: {
      margin: 0,
      background: theme.palette.background.default,
      color: theme.palette.text.primary,
      // lineHeight: '1.2',
      overflowX: 'hidden',
      // WebkitFontSmoothing: 'antialiased', // Antialiasing.
      // MozOsxFontSmoothing: 'grayscale', // Antialiasing.
    },
    img: {
      maxWidth: '100%',
      height: 'auto',
      width: 'auto',
    },
  },
  appFrame: {
    display: 'flex',
    alignItems: 'stretch',
    minHeight: '100vh',
    width: '100%',
  },
}));


// App component
export class AppFrame extends React.Component {

  // pre-render logic
  componentWillMount() {
  }

  // render
  render() {
    // show the loading state while we wait for the app to load
    const { children, classes } = this.props;

    // render
    return (
      <div className={classes.appFrame}>
        {children}
      </div>
    );
  }
}

// export the connected class
function mapStateToProps(state) {
  return { };
}
export default connect(mapStateToProps)( withStyles(styleSheet)(AppFrame) );
