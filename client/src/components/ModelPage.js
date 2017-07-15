import React from 'react'
import { connect } from 'react-redux'

import { connectModel } from '../actions'

const loadData = ({ id, connectModel }) => {
  connectModel(id)
}

class ModelPage extends React.Component {

  componentWillMount() {
    loadData(this.props)
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.id !== this.props.id) {
      loadData(nextProps)
    }
  }

  render() {
    const { model, id } = this.props
    if (!model) {
      return <h1><i>Loading model with id={id}</i></h1>
    }
    return (
      <div>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.params.id.toLowerCase()
  const model = state.model
  return { id, model }
}

export default connect(mapStateToProps, {
  connectModel
})(ModelPage)
