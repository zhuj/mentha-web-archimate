import React from 'react'

import Button from '@material-ui/core/Button';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';

import { relationships } from './edges/view/viewRelationship'
import { constraints } from './edges/constraints'

import _ from 'lodash'

import { withStyles } from '@material-ui/core/styles';
const styleSheet =theme => ({
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
    transform:'scale(0.8)',
    position:'absolute',
    transformOrigin: '0 0'
  }
});

class NewLinkMenu extends React.Component {

  renderRelationItem(rel, className, params, title) {
    const { classes, select } = this.props;
    return (
      <ListItem key={rel} className={classes.item}>
        <Button
          onClick={() => { select({ ...params, _tp: rel }) }}
          className={classes.button}
          disableRipple
        >
          <span className={`diagrams-canvas ${classes.sample}`}>
            <svg className={`link-view ${classes.svg}`}>
              <g className={`${rel} ${className}`}>
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
      const s = 'o' + (constraints[`${srcTp}-${dstTp}`] || '');
      const rels = _.map(s.split(''), (c) => relationships[c]);

      const special = [
      ];

      if (typeof(_.find(rels, (r) => r === 'accessRelationship')) !== 'undefined') {
        special.push(<Divider key="divider"/>);
        special.push(this.renderRelationItem("accessRelationship", "r", { access:'r' }, "reads"));
        special.push(this.renderRelationItem("accessRelationship","w", { access:'w' }, "writes"));
        special.push(this.renderRelationItem("accessRelationship", "r w", { access:'rw' }, "reads/writes"));
      }

      return [
        ... _.map(rels, (rel) => this.renderRelationItem(rel, '', {}, rel.replace('Relationship', ''))),
        ... special,
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
          <Divider key="divider"/>
          { this.renderRelationItem("viewConnection", '', {}, "connection")}
        </List>
      </div>
    );
  }

}

export default withStyles(styleSheet)(NewLinkMenu);
