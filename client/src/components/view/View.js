import React from 'react'
import { connect } from 'react-redux'

import ViewDiagram from './ViewDiagram'

class View extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  render() {
    const { id } = this.props
    return (
      <div className="view">
        <div>{id}</div>
        <div>
          <ViewDiagram id={id}/>
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return { id: ownProps.id };
  //const model = state.model;
  //const view = model.views[id];
  // return { id , model, view }
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(View)
