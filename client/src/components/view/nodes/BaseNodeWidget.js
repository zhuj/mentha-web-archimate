import React from 'react'

import { DefaultNodeWidget } from '../diagram/DefaultWidgets'

const BaseNodeWidget = DefaultNodeWidget;

export class ModelNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
  getName(node) {
    return this.props.conceptInfo.name;
  }
}

export class ViewNodeWidget extends BaseNodeWidget {
  constructor(props) { super(props); }
}