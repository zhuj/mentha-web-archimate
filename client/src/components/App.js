import React from "react";
import { connect } from "react-redux";

import Menu from "./Menu";
import "../stylesheets/main.scss";

// App component
export class App extends React.Component {

  // pre-render logic
  componentWillMount() {
  }

  // render
  render() {
    // show the loading state while we wait for the app to load
    const { children } = this.props;

    // render
    return (
      <div className="container">
        <div>
          <Menu/>
        </div>
        <div>
          {children}
        </div>
      </div>
    );
  }
}

// export the connected class
function mapStateToProps(state) {
  return {
  };
}
export default connect(mapStateToProps)(App);
