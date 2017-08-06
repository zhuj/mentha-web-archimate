package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.nodes._

package object validator {

  def validate(src: data.EMeta, dst: data.EMeta, rel: data.RMeta): Boolean = {
    (rel == OtherRelationships.association) || {
      data.data.get((src, dst)).exists { case (all, _) => all.contains(rel) }
    }
  }

  def validate(src: ConceptMeta[_], dst: ConceptMeta[_], rel: RelationshipMeta[_]): Boolean = {
    (rel == OtherRelationships.association) || {
      if (src.isInstanceOf[data.EMeta] && dst.isInstanceOf[data.EMeta]) {
        validate(
          src.asInstanceOf[data.EMeta],
          dst.asInstanceOf[data.EMeta],
          rel
        )
      } else if (src.isInstanceOf[RelationshipConnectorMeta[_]] || dst.isInstanceOf[RelationshipConnectorMeta[_]]) {
        true
      } else {
        false
      }
    }
  }


}
