import React from 'react'
import { connect } from 'react-redux'

import View from './View'

class ViewList extends React.Component {

  render() {
    const { views } = this.props;
    return (
      <div className="view-list">
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
