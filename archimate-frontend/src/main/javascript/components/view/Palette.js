import React from 'react'
import { connect } from 'react-redux'

import _ from 'lodash'

import * as models from './diagram/models'
import DragWrapper from './diagram/DragWrapper'

import { layerElements, viewNodeConceptWidget } from './nodes/view/viewNodeConcept'
import { ViewNotesWidget } from "./nodes/view/viewNotes"
import { allMeta } from '../../meta/index'

import './Palette.sass.scss'

const hint = (tp) => {
  let meta = allMeta[tp];
  if (!!meta) {
    return `${meta.name}:\n * ${_.join(meta['help']['summ'])}`
  }
  return tp;
};

class Palette extends React.Component {

  constructor(props, context) {
    super(props, context);
    this.state = {
      hover: null
    };
  }

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps, nextContext) {
  }


  renderConceptDragSource(tp, kind) {
    const conceptInfo = { _tp: tp };
    return this.renderDragSource(
      tp,
      kind,
      { conceptInfo },
      (node) => viewNodeConceptWidget({ node, conceptInfo })
    );
  }

  renderDragSource(tp, kind, params, body) {
    const x = 0, y = 0, width = 35, height = 35;
    const node = Object.assign(new models.NodeModel(), {
      id: `${kind}-${tp}`, x, y, width, height, ...params
    });

    const style = {
      position: 'relative',
      x, y, width, height
    };

    return (
      <div
        key={`palette-${node.id}`}
        onMouseEnter={()=>this.setState({hover:node.id})}
        onMouseOut={()=>this.setState({hover:null})}
      >
        <DragWrapper key={`palette-drag-${node.id}`} tp={tp} kind={kind}>
          <div className="x-node p-node" style={style} title={hint(tp)}>
            { body(node) }
          </div>
        </DragWrapper>
      </div>
    );
  }


  render() {
    return (
      <div className="palette">
        {
          _.map(layerElements, (elements, layer) => (
            <div key={`palette-${layer}`} className={`diagrams-canvas layer ${layer}`} style={{display: 'flex'}}>
              {_.map(elements, (element) => this.renderConceptDragSource(element, 'element'))}
            </div>
          ))
        }
        <div key={`palette-other`} className={`diagrams-canvas layer other`} style={{display: 'flex'}}>
          {this.renderConceptDragSource('orJunction', 'connector')}
          {this.renderConceptDragSource('andJunction', 'connector')}
          {this.renderDragSource('viewNotes', 'notes', {viewObject: {}}, (node) => <ViewNotesWidget {...{node}}/>)}
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const view = state.model.views[ownProps.id] || {};
  return {
    id: ownProps.id,
    vp: view['viewpoint']
  };
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(Palette)
