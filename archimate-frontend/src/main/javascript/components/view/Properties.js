import React from 'react'
import { connect } from 'react-redux'

import _ from 'lodash'

import './Properties.sass.scss'

class Palette extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps, nextContext) {
  }

  render() {
    return (
      <div className="properties">
        {
          _.map(this.props.selection, (el, idx) => (
            <pre key={idx} className="json"> {JSON.stringify(el, null, 2)} </pre>
          ))
        }
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const view = state.model.views[ownProps.id] || {};
  const selection = view['.selection'] || [];

  return {
    id: ownProps.id,
    selection: _.chain(selection).map((id) => view.nodes[id] || view.edges[id]).value()
  };
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(Palette)
