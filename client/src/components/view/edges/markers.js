import React from 'react'

export default class Markers extends React.Component {
  render() {
    return (
      <div className="markers">
        <svg style={{width:0, height:0}}>
          <defs>

            <marker id="MarkerCompositionArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,10 10,4 20,10 10,16 0,10" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerAggregationArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,10 10,4 20,10 10,16 0,10" fill="white" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerAssignmentArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <circle cx="4" cy="10" r="4" color="black" strokeWidth="1"/>
            </marker>
            <marker id="MarkerAssignmentArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="8,4 20,10 8,16 8,4" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerRealizationArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="8,4 20,10 8,16 8,4" fill="white" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerServingArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="12,2 20,10 12,18" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerInfluenceArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,0 20,10 0,20" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerAccessArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="8,2 0,10 8,18" fill="none" strokeWidth="2" stroke="black"/>
            </marker>
            <marker id="MarkerAccessArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="12,2 20,10 12,18" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerFlowArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="12,6 20,10 12,14 12,6" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerTriggerArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="12,6 20,10 12,14 12,6" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerSpecializationArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="30">
              <polyline points="0,0 20,10 0,20 0,0" fill="white" strokeWidth="1" stroke="black"/>
            </marker>



          </defs>
        </svg>
      </div>
    )
  }
}
