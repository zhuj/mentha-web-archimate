package org.mentha.utils.archimate.model.edges.validator

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.nodes._

abstract class Validation {

  type Meta = String //ElementMeta[_ <: Element]
  type RecordKey = (Meta, Meta)
  type RecordValue = (String, String)
  type Record = (RecordKey, RecordValue)

  class RelationshipRegistry(src: ElementMeta[_]) {
    private[validator] var result: List[Record] = Nil
    def register(dst: ElementMeta[_], all: String, derived: String): Unit = {
      result = ((src.name/*.asInstanceOf[Meta]*/, dst.name/*.asInstanceOf[Meta]*/) -> (all, derived)) :: result
    }
  }

  def in(src: ElementMeta[_])(block: RelationshipRegistry => Unit): List[Record] = {
    val registry = new RelationshipRegistry(src)
    block(registry)
    registry.result
  }

  def data: Map[RecordKey, RecordValue]

  def validate(src: ElementMeta[_], dst: ElementMeta[_], rel: RelationshipMeta[_]): Boolean = {
    (rel == OtherRelationships.association) || {
      data.get((src.name, dst.name)).exists { case (all, _) => all.contains(rel.key.toString) }
    }
  }

  def validate(src: ConceptMeta[_], dst: ConceptMeta[_], rel: RelationshipMeta[_]): Boolean = {
    (rel == OtherRelationships.association) || {
      if (src.isInstanceOf[ElementMeta[_]] && dst.isInstanceOf[ElementMeta[_]]) {
        validate(
          src.asInstanceOf[ElementMeta[_]],
          dst.asInstanceOf[ElementMeta[_]],
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
