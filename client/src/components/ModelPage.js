import React from 'react'
import { connect } from 'react-redux'

import { DragDropContext } from 'react-dnd'
import HTML5Backend from 'react-dnd-html5-backend';

import reactLS from 'react-localstorage'

import _ from 'lodash'

import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Button from 'material-ui/Button';
import Divider from 'material-ui/Divider';
import Drawer from 'material-ui/Drawer';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import DownloadIcon from 'material-ui-icons/ScreenShare';
import AddViewIcon from 'material-ui-icons/PlaylistAdd';
import List, { ListItem } from 'material-ui/List';

import View from './view/View'

import * as actions from '../actions'
import * as api from '../actions/model.api'

import dom2image from 'dom-to-image'
import filesaver from 'file-saver'

import { withStyles } from 'material-ui/styles';
const styleSheet = theme => ({
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
  content: {
    paddingTop: 80,
    paddingBottom: 16,
    paddingLeft: 16,
    paddingRight: 16,
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
  },
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
});

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
    reactLS.componentWillUpdate.call(this, nextProps, nextState);
    if (!!super.componentWillUpdate) { super.componentWillUpdate(nextProps, nextState); }
  }

  componentDidMount() {
    reactLS.componentDidMount.call(this);
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
                {view.name}
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
            <IconButton
              color="contrast"
              onClick={()=>{ this.downloadCurrentView() }}
              className={classes.icon}
            >
              <DownloadIcon/>
            </IconButton>
          </Toolbar>
        </AppBar>
        <Drawer
          className={classes.paper}
          open={this.state.drawer}
          onRequestClose={()=>{ this.setState({drawer: false}) }}
        >
          <div className={classes.nav}>
            <Toolbar className={classes.toolbar}>
              {/* TODO: search */}
              {
                <ListItem key="add-view" className={classes.navItem}>
                  <IconButton
                    color="contrast"
                    onClick={() => { this.addView() }}
                    className={classes.icon}
                  >
                    <AddViewIcon/>
                  </IconButton>
                </ListItem>
              }
            </Toolbar>
            <Divider/>
            { this.renderViewList() }
          </div>
        </Drawer>
        <div className={classes.content}>
          { this.renderCurrentView() }
        </div>
      </div>
    )
  }

  addView() {
    this.props.sendModelCommands(
      api.addView('layered', 'View#' + new Date().toISOString())
    );
  }

  downloadCurrentView() {
    const { 'current-view': id } = this.state;
    const diagram = document.getElementById(`diagrams-canvas-${id}`);
    if (!!diagram) {
      dom2image
        .toBlob(diagram, {})
        .then(function (blob) {
          filesaver.saveAs(blob, `view-${id}.png`);
        });
    }
  }

}

const mapStateToProps = (state, ownProps) => {
  // console.log(ownProps);
  const id = ownProps.params.id; // 'params' goes from router
  const model = state.model;
  return { id, model };
};

const mapDispatchToProps = (dispatch) => ({
  connectModel: (id) => dispatch(actions.connectModel(id)),
  sendModelCommands: (commands) => dispatch(actions.sendModelMessage(api.composite(commands))),
});

export default connect(mapStateToProps, mapDispatchToProps)( withStyles(styleSheet)(ModelPage) )
