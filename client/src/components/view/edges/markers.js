import React from 'react'

export default class Markers extends React.Component {
  render() {
    return (
      <div className="markers" style={{width:0, height:0}}>
        <svg style={{width:0, height:0}}>
          <defs>

            <marker id="MarkerCompositionArrowSrc" refX="2" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="2,10 10,5 18,10 10,15 2,10" fill="black" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerAggregationArrowSrc" refX="2" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="2,10 10,5 18,10 10,15 2,10" fill="white" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerAssignmentArrowSrc" refX="4" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <circle cx="4" cy="10" r="4" color="black" strokeWidth="1"/>
            </marker>
            <marker id="MarkerAssignmentArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="6,4 18,10 6,16 6,4" fill="black" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerRealizationArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="6,4 18,10 6,16 6,4" fill="white" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerServingArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,5 18,10 10,15" fill="none" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerInfluenceArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,5 18,10 10,15" fill="none" strokeWidth="2" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerAccessArrowSrc" refX="2" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,5 2,10 10,15" fill="none" strokeWidth="2" strokeLinecap="round" stroke="black"/>
            </marker>
            <marker id="MarkerAccessArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,5 18,10 10,15" fill="none" strokeWidth="2" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerFlowArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,6 18,10 10,14 10,6" fill="black" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerTriggerArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="10,6 18,10 10,14 10,6" fill="black" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

            <marker id="MarkerSpecializationArrowDst" refX="18" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="30">
              <polyline points="3,2 18,10 3,18 3,2" fill="white" strokeWidth="1" strokeLinecap="round" stroke="black"/>
            </marker>

          </defs>
        </svg>
      </div>
    )
  }
}
