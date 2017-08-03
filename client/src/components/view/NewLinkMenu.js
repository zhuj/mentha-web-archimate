import React from 'react'

import Button from 'material-ui/Button';
import Divider from 'material-ui/Divider';
import List, { ListItem } from 'material-ui/List';

import { relationships } from './edges/view/viewRelationship'
import { constraints } from './edges/constraints'

import _ from 'lodash'

import { withStyles, createStyleSheet } from 'material-ui/styles';
const styleSheet = createStyleSheet(theme => ({
  root: {
    background: theme.palette.background.paper,
  },
  list: {
    padding: [2, 0],
  },
  item: {
    padding: 0,
  },
  button: {
    padding: [2, 8],
    margin: 0,
    minHeight: 20,
    minWidth: '-webkit-fill-available',
    textTransform: 'none'
  },
  sample: {
    marginRight: 5,
    width: 20,
    height: 20
  },
  svg: {
    transform:'scale(0.8)'
  }
}));

class NewLinkMenu extends React.Component {

  renderRelationItem(rel, title) {
    const { classes, select } = this.props;
    return (
      <ListItem key={rel} className={classes.item}>
        <Button
          onClick={() => { select(rel) }}
          className={classes.button}
          disableRipple
        >
          <span className={`diagrams-canvas ${classes.sample}`}>
            <svg className={`link-view ${classes.svg}`}>
              <g className={rel}>
                <g className='x-link first last'>
                  <path className='x-link p' d="M 2 18 L 18 2"/>
                </g>
              </g>
            </svg>
          </span>
          {title}
        </Button>
      </ListItem>
    );
  }

  renderRelationships() {
    try {
      const {
        linkModel: {
          sourceNode: { viewObject: { ['.conceptInfo']: srcCnt } },
          targetNode: { viewObject: { ['.conceptInfo']: dstCnt } }
        }
      } = this.props;

      if (!srcCnt || !dstCnt) {
        return null;
      }

      const srcTp = srcCnt['_tp'], dstTp = dstCnt['_tp'];
      const s = constraints[`${srcTp}-${dstTp}`];
      if (!s) {
        return null;
      }

      const rels = _.map(s[0].split(''), (c) => relationships[c]);
      return [
        ... _.map(rels, (rel) => this.renderRelationItem(rel, rel.replace('Relationship', ''))),
        <Divider key="divider"/>
      ];
    } catch (e) {
      console.log(e);
      return null;
    }
  }

  render() {
    const { classes } = this.props;
    return (
      <div className={classes.root}>
        <List className={classes.list}>
          { this.renderRelationships() }
          { this.renderRelationItem("viewConnection", "connection")}
        </List>
      </div>
    );
  }

}

export default withStyles(styleSheet)(NewLinkMenu);
