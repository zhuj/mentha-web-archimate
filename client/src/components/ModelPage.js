import React from 'react'
import { connect } from 'react-redux'

import { DragDropContext } from 'react-dnd'
import HTML5Backend from 'react-dnd-html5-backend';

import ViewList from './ViewList'

import * as actions from '../actions'

const loadData = ({ id, connectModel }) => {
  connectModel(id)
};

@DragDropContext(HTML5Backend)
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
    return (
      <div>
        <ViewList/>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.params.id; // 'params' goes from router
  const model = state.model;
  return { id, model }
};

const mapDispatchToProps = (dispatch) => ({
  connectModel: (id) => dispatch(actions.connectModel(id))
});

export default connect(mapStateToProps, mapDispatchToProps)(ModelPage)
