import React from 'react';
import { DragSource } from 'react-dnd';

const nodeSource = {
  beginDrag(props) {
    return props;
  }
};

@DragSource('node-source', nodeSource, (connect, monitor) => ({
  connectDragSource: connect.dragSource(),
  connectDragPreview: connect.dragPreview(),
  isDragging: monitor.isDragging(),
}))
export default class DragWrapper extends React.Component {
  render() {
    const { isDragging, connectDragSource, children, style } = this.props;
    const opacity = isDragging ? 0.4 : 1;

    return (
      connectDragSource(
        <div style={{ ...style, opacity }}>
          {children}
        </div>
      )
    );
  }
}
