import React from 'react'
import { connect } from 'react-redux'

import { DragDropContext } from 'react-dnd'
import HTML5Backend from 'react-dnd-html5-backend';

import reactLS from 'react-localstorage'

import _ from 'lodash'

import { withStyles, createStyleSheet } from 'material-ui/styles';

import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Button from 'material-ui/Button';
import Drawer from 'material-ui/Drawer';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import List, { ListItem, ListItemText } from 'material-ui/List';

import View from './view/View'

import * as actions from '../actions'

const styleSheet = createStyleSheet('ModelPage', theme => ({
  modelPage: {
    width: '100%',
  },
  grow: {
    flex: '1 1 auto',
  },
  title: {
    marginLeft: 24,
    flex: '0 1 auto',
  },
  appBar: {
    transition: theme.transitions.create('width'),
  },
  content: theme.mixins.gutters({
    paddingTop: 80,
    paddingBottom: 16,
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
  }),
  button: theme.mixins.gutters({
    borderRadius: 0,
    justifyContent: 'flex-start',
    textTransform: 'none',
    width: '100%',
    transition: theme.transitions.create('background-color', {
      duration: theme.transitions.duration.shortest,
    }),
    '&:hover': {
      textDecoration: 'none',
    },
  }),
  navItem: {
    ...theme.typography.body2,
    display: 'block',
    paddingTop: 0,
    paddingBottom: 0,
  },
}));


const loadData = ({ id, connectModel }) => {
  connectModel(id);
};

@DragDropContext(HTML5Backend)
class ModelPage extends React.Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  /* @override: react-localstorage */
  getLocalStorageKey() {
    return `model-page-${this.props.id}`;
  }

  /* @override: react-localstorage */
  getStateFilterKeys() {
    return ["current-view"];
  }

  componentWillUpdate(nextProps, nextState) {
    reactLS.componentWillUpdate.bind(this)(nextProps, nextState);
    if (!!super.componentWillUpdate) { super.componentWillUpdate(nextProps, nextState); }
  }

  componentDidMount() {
    reactLS.componentDidMount.bind(this)();
    if (!!super.componentDidMount) { return super.componentDidMount(); }
  }

  componentWillMount() {
    loadData(this.props);
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.id !== this.props.id) {
      loadData(nextProps);
    }
  }

  renderViewList() {
    const { model, classes } = this.props;
    return (
      <List>
        {
          _.map( model.views, (view, id) => (
            <ListItem key={id} className={classes.navItem}>
              <Button
                onClick={()=>{ this.setState({'current-view': id}) }}
                className={classes.button}
                disableRipple
              >
                [{id}] {view.name}
              </Button>
            </ListItem>
          ) )
        }
      </List>
    );
  }

  renderCurrentView() {
    const { 'current-view': id } = this.state;
    if (!!id) {
      const { model } = this.props;
      if (!model) { return null; }

      const { views } = model;
      if (!views) { return null; }

      if (!!views[id]) {
        return (
          <View id={id} key={id}/>
        )
      }
    }
    return null;
  }

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.modelPage}>
        <AppBar className={classes.appBar}>
          <Toolbar>
            <IconButton
              color="contrast"
              onClick={()=>{ this.setState({drawer: !this.state.drawer}) }}
              className={classes.icon}
            >
              <MenuIcon/>
            </IconButton>
            <div className={classes.grow} />
          </Toolbar>
        </AppBar>
        <Drawer
          className={classes.paper}
          open={this.state.drawer}
          onRequestClose={()=>{ this.setState({drawer: false}) }}
        >
          <div className={classes.nav}>
            <Toolbar className={classes.toolbar}>
            </Toolbar>
            { this.renderViewList() }
          </div>
        </Drawer>
        <div className={classes.content}>
          { this.renderCurrentView() }
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  const id = ownProps.params.id; // 'params' goes from router
  const model = state.model;
  return { id, model };
};

const mapDispatchToProps = (dispatch) => ({
  connectModel: (id) => dispatch(actions.connectModel(id))
});

export default connect(mapStateToProps, mapDispatchToProps)( withStyles(styleSheet)(ModelPage) )
