package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.nodes._

package object validator {

  type CMeta = ConceptMeta[_ <: Concept]
  type EMeta = ElementMeta[_ <: Element]
  type RMeta = RelationshipMeta[_ <: Relationship]
  type RCMeta = RelationshipConnectorMeta[_ <: RelationshipConnector]

  def validate(src: EMeta, dst: EMeta, rel: RMeta): Boolean = {
    (rel == OtherRelationships.association) || {
      data.data.get((src, dst)).exists { case (all, _) => all.contains(rel) }
    }
  }

  def validate(src: CMeta, dst: CMeta, rel: RMeta): Boolean = {
    (rel == OtherRelationships.association) || {
      (src, dst) match {
        case (s: EMeta, d: EMeta) => validate(s, d, rel)
        case (_: RCMeta, _: RCMeta) => true
        case _ => false
      }
    }
  }


}
