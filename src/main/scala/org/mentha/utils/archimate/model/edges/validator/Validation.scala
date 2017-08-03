package org.mentha.utils.archimate.model.edges.validator

import org.mentha.utils.archimate.model.Element
import org.mentha.utils.archimate.model.nodes.ElementMeta

class Validation {

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

}
