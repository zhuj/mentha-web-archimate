import React from 'react'

import './Panels.sass.scss'

export class LeftPanel extends React.Component {

  constructor(props, context) {
    super(props, context);
    this.state = { visible: !!props.visible };
  }

  render() {
    const { visible } = this.state;
    return (
      <div className={`left-panel ${visible ? '' : ' hidden'}`}>
        { (!visible) ? null : ( <div className="content"> { this.props.children } </div> ) }
        {
          (visible) ? (
            <div className="control v">
              <div onClick={()=>this.setState({visible:false})}> &#x25C0; </div>
            </div>
          ) : (
            <div className="control h">
              <div onClick={()=>this.setState({visible:true})}> &#x25B6; </div>
            </div>
          )
        }
      </div>
    )
  }
}

export class RightPanel extends React.Component {

  constructor(props, context) {
    super(props, context);
    this.state = { visible: !!props.visible };
  }

  render() {
    const { visible } = this.state;
    return (
      <div className={`right-panel ${visible ? '' : ' hidden'}`}>
        {
          (visible) ? (
            <div className="control v">
              <div onClick={()=>this.setState({visible:false})}> &#x25B6; </div>
            </div>
          ) : (
            <div className="control h">
              <div onClick={()=>this.setState({visible:true})}> &#x25C0; </div>
            </div>
          )
        }
        { (!visible) ? null : ( <div className="content"> { this.props.children } </div> ) }
      </div>
    )
  }
}
