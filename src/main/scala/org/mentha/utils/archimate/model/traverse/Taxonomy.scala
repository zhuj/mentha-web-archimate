package org.mentha.utils.archimate.model.traverse

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._
import org.mentha.utils.archimate.model.view._

import scala.collection.mutable

/**
  *
  */
object Taxonomy {

  object TaxonomyDirection extends ModelTraversing.Direction {

    override def nextLevelResolver(c: Concept): PartialFunction[EdgeConcept, (EdgeConcept, Concept)] = {
      case r: StructuralRelationship if r.source == c => (r, r.target)
//      case r: CompositionRelationship if r.source == c => (r, r.target)
//      case r: AggregationRelationship if r.source == c => (r, r.target)
//      case r: AssignmentRelationship if r.source == c => (r, r.target)
//      case r: RealizationRelationship if r.source == c => (r, r.target)

//      case r: SpecializationRelationship if r.target == c => (r, r.source)
//      case r: RealizationRelationship if r.target == c => (r, r.source)
//      case r: ServingRelationship if r.target == c => (r, r.source)
//      case r: InfluenceRelationship if r.target == c => (r, r.source)
//
//      case r: AccessRelationship if r.access.write && r.target == c => (r, r.source)
//      case r: AccessRelationship if r.access.read && r.source == c => (r, r.target)
//
//      case r: AssociationRelationship if r.source == c => (r, r.target)
//      case r: AssociationRelationship if r.target == c => (r, r.source)
    }

  }

  def apply(model: Model, visitor: ModelVisitor = new ModelVisitor {}): View = {
    val traversing = new ModelTraversing(TaxonomyDirection)(model)
    val view = new View
    val visitedVertexes = mutable.Set[Concept]()
    val modelVisitor = new ModelVisitor {
      override def visitEdge(from: Concept, edge: EdgeConcept, to: Concept) = {
        if (/*visitedVertexes.add(to) &&*/ visitor.visitEdge(from, edge, to)) {
          view.attach(edge)
          true
        } else {
          false
        }
      }
    }

    for { v <- model.nodes } {
      traversing.apply(v) { modelVisitor }
    }
    view
  }

}
