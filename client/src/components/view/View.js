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
        <ViewDiagram id={id}/>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return { id: ownProps.id };
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(View)
