import React from 'react'
import { connect } from 'react-redux'

import Markers from './view/edges/markers'
import View from './view/View'

class ViewList extends React.Component {

  render() {
    const { views } = this.props;
    return (
      <div className="view-list">
        <Markers/>
        { Object.getOwnPropertyNames(views).map((id) => <View key={id} id={id}/>) }
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const views = state.model.views || {};
  return { views }
}

export default connect(mapStateToProps)(ViewList)
