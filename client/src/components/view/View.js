import React from 'react'
import { connect } from 'react-redux'

import Markers from './edges/markers'
import ViewDiagram from './ViewDiagram'
import Palette from './Palette'
import Properties from './Properties'
import { LeftPanel, RightPanel } from "./Panels"

class View extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  render() {
    const { id } = this.props
    const style = {
      display: 'flex',
      height: '100%'
    };
    return (
      <div className="view" style={style}>
        <Markers/>
        <LeftPanel visible={true}>
          <Palette id={id}/>
        </LeftPanel>
        <ViewDiagram id={id}/>
        <RightPanel visible={false}>
          <Properties id={id}/>
        </RightPanel>
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
