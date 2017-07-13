package org.mentha.utils.archimate.model.nodes

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges.RelationshipMeta

import scala.reflect.ClassTag

/** */
class RelationshipConnectorMeta[T <: RelationshipConnector](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def newInstance(relationship: RelationshipMeta[_]): T = runtimeClass.newInstance().asInstanceOf[T]

}
