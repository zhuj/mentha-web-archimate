package org.mentha.tools.archimate.model.edges

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges.impl._

package object validator {

  type CMeta = ConceptMeta[_ <: Concept]
  type RMeta = RelationshipMeta[_ <: Relationship]

  def validate(src: CMeta, dst: CMeta, rel: RMeta): Boolean = {
    (rel == OtherRelationships.associationRelationship) || {
      data.data.get((src, dst)).exists { case (all, _) => all.contains(rel) }
    }
  }

  def derived(src: CMeta, dst: CMeta, rel: RMeta): Boolean = {
    (rel != OtherRelationships.associationRelationship) && {
      data.data.get((src, dst)).exists { case (_, der) => der.contains(rel) }
    }
  }

}
