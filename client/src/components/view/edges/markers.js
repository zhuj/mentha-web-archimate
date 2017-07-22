import React from 'react'

export default class Markers extends React.Component {
  render() {
    return (
      <div>
        <svg>
          <defs>

            <marker id="MarkerCompositionArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="24" markerHeight="20">
              <polyline points="0,10 12,2 24,10 12,18 0,10" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerAggregationArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="24" markerHeight="20">
              <polyline points="0,10 12,2 24,10 12,18 0,10" fill="white" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerAssignmentArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <circle cx="5" cy="10" r="5" color="black"/>
            </marker>
            <marker id="MarkerAssignmentArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,2 20,10 0,18 0,0" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerRealizationArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,2 20,10 0,18 0,0" fill="white" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerServingArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,0 20,10 0,20" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerInfluenceArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,0 20,10 0,20" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerAccessArrowSrc" refX="0" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="20,0 0,10 20,20" fill="none" strokeWidth="2" stroke="black"/>
            </marker>
            <marker id="MarkerAccessArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,0 20,10 0,20" fill="none" strokeWidth="2" stroke="black"/>
            </marker>

            <marker id="MarkerFlowArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,2 20,10 0,18 0,0" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerTriggerArrowDst" refX="20" refY="10" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="20">
              <polyline points="0,2 20,10 0,18 0,0" fill="black" strokeWidth="1" stroke="black"/>
            </marker>

            <marker id="MarkerSpecializationArrowDst" refX="20" refY="15" markerUnits="userSpaceOnUse" orient="auto" markerWidth="20" markerHeight="30">
              <polyline points="0,0 20,15 0,30 0,0" fill="white" strokeWidth="1" stroke="black"/>
            </marker>



          </defs>
        </svg>
      </div>
    )
  }
}
