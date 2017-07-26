import React from 'react'
import { connect } from 'react-redux'

import Markers from './edges/markers'
import ViewDiagram from './ViewDiagram'
import DragWrapper from './DragWrapper'

class View extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  render() {
    const { id } = this.props
    return (
      <div className="view" style={{display:'inline'}}>
        <Markers/>
        <ViewDiagram id={id}/>
        <div>
          <DragWrapper style={{display: 'inline-block'}}>
            <div>qwe</div>
          </DragWrapper>
        </div>
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
