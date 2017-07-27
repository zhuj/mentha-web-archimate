import React from 'react'
import { connect } from 'react-redux'

import _ from 'lodash'

import * as models from './diagram/models'
import DragWrapper from './diagram/DragWrapper'

import { layerElements, viewNodeConceptWidget } from './nodes/view/viewNodeConcept'


import './palette.sass.scss'

class Palette extends React.Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  componentWillMount() {
  }

  componentWillReceiveProps(nextProps) {
  }

  renderDragSource(element) {
    const x = 0, y = 0, width = 35, height = 35;
    const conceptInfo = { _tp: element, name: '' };
    const node = Object.assign(new models.NodeModel(), {
      id: element, x, y, width, height, conceptInfo
    });

    const style = {
      position: 'relative',
      x, y, width, height
    };

    return (
      <div
        key={`palette-${element}`}
        onMouseEnter={()=>this.setState({hover:element})}
        onMouseOut={()=>this.setState({hover:null})}
      >
      <DragWrapper key={`palette-drag-${element}`} tp={element}>
        <div className="x-node p-node" style={style} title={element}>
        { viewNodeConceptWidget({ node, conceptInfo }) }
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
            <div key={`palette-${layer}`} className={`diagrams-canvas layer ${layer}`} style={{display:'flex'}}>
              {  _.map(elements, (element) => this.renderDragSource(element)) }
            </div>
          ))
        }
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return {};
};

const mapDispatchToProps = (dispatch) => ({
});


export default connect(mapStateToProps, mapDispatchToProps)(Palette)
