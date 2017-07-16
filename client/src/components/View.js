import React from 'react'
import { connect } from 'react-redux'

import ViewRZD from './rjd/ViewRJD'

class View extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  render() {
    const { model, view, id } = this.props
    return (
      <div className="view">
        <div>{id}</div>
        <div>
          <ViewRZD id={id}/>
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id
  const model = state.model
  const view = model.views[id]
  return { id, model, view }
}

export default connect(mapStateToProps)(View)
