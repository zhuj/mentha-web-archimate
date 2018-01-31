import React from 'react'
import { connect } from 'react-redux'

import _ from 'lodash'

class DiagramLegend extends React.Component {

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps, nextContext) {
  }

  render() {
    const { nodes, edges } = this.props;
    return (
      <div className="legend">
        {/*
          _.forEach(nodes, (node) => (
            <div>
              { node }
            </div>
          ))
        */}
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.id;
  const model = state.model || {};
  const view = model.views[id] || {};

  const collect = (c) => ( _.chain(c || {})
    .values()
    .map( (v) => v['.conceptInfo'] ).filter( (v) => v !== undefined )
    .map( (v) => v['_tp'] ).filter( (v) => v !== undefined )
    .sortedUniqBy()
    .valueOf()
  );

  const nodes = collect(view.nodes || {});
  const edges = collect(view.edges || {});
  return { id, nodes, edges };
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(DiagramLegend)
