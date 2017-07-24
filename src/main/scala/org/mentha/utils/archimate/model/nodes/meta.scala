package org.mentha.utils.archimate.model.nodes

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges.RelationshipMeta

import scala.reflect.ClassTag

/** */
abstract class ElementMeta[T <: Element](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def newInstance(): T = runtimeClass.newInstance().asInstanceOf[T]
  def layerObject: LayerObject = ???
  def key: String = ???

  override def toString: String = s"ElementMeta(${name})"

}

/** */
class RelationshipConnectorMeta[T <: RelationshipConnector](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def newInstance(relationship: RelationshipMeta[_]): T = runtimeClass.newInstance().asInstanceOf[T]

  override def toString: String = s"RelationshipConnectorMeta(${name})"

}
