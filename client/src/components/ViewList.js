import React from 'react'
import { connect } from 'react-redux'

import { Nav, NavItem, Tab, Row, Col } from "react-bootstrap";
import reactLS from 'react-localstorage'

import _ from 'lodash'

import Markers from './view/edges/markers'
import View from './view/View'

import './view-list.scss'

class ViewList extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      selected: undefined
    };
  }

  /* @override: react-localstorage */
  getLocalStorageKey() {
    return `view-list-${this.props.id}`;
  }

  /* @override: react-localstorage */
  getStateFilterKeys() {
    return ["selected"];
  }

  componentWillUpdate(nextProps, nextState) {
    reactLS.componentWillUpdate.bind(this)(nextProps, nextState);
    if (!!super.componentWillUpdate) { super.componentWillUpdate(nextProps, nextState); }
  }

  componentDidMount() {
    reactLS.componentDidMount.bind(this)();
    if (!!super.componentDidMount) { return super.componentDidMount(); }
  }

  render() {
    const { selected } = this.state;
    const { views } = this.props;
    return (
      <div className="view-list">
        <Tab.Container
          id="view-list-tabs"
          activeKey={selected}
          onSelect={ (id) => this.setState({selected: id}) }
        >
          <Row className="clearfix">
            <Col sm={2}>
              <Nav bsStyle="pills" stacked>
                {
                  _.map( views, (view, id) => (
                    <NavItem eventKey={id} key={id}>
                      [{id}] {view.name}
                    </NavItem>
                  ) )
                }
              </Nav>
            </Col>
            <Col sm={10}>
              <Tab.Content>
                <Markers/>
                { (!!selected && !!views[selected]) ? <View id={selected} key={selected}/> : null }
              </Tab.Content>
            </Col>
          </Row>
        </Tab.Container>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const views = state.model.views || {};
  return { id: state.model.id, views }
}

export default connect(mapStateToProps)(ViewList)
