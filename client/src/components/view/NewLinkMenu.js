import React from 'react'

import Button from 'material-ui/Button';
import List, { ListItem } from 'material-ui/List';

import { relationships } from './edges/view/viewRelationship'

import _ from 'lodash'

import { withStyles, createStyleSheet } from 'material-ui/styles';
const styleSheet = createStyleSheet(theme => ({
  root: {
    background: theme.palette.background.paper,
  },
  item: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  button: {
    padding: 0,
    margin: 0,
    minHeight: 20,
    textTransform: 'none',
    textAlign: 'left'
  }
}));

class NewLinkMenu extends React.Component {

  render() {
    const {classes, select} = this.props;
    return (
      <div className={classes.root}>
        <List>
          {
            _.map(relationships, (rel)=>(
              <ListItem key={rel} className={classes.item}>
                <Button
                  onClick={()=>{select(rel)}}
                  className={classes.button}
                  disableRipple
                >
                  {rel.replace('Relationship', '')}
                </Button>
              </ListItem>
            ))
          }
        </List>
      </div>
    );
  }

}

export default withStyles(styleSheet)(NewLinkMenu);
