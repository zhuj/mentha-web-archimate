package org.mentha.utils.archimate.model.nodes

import org.mentha.utils.archimate.model._

import scala.reflect.ClassTag

/** */
abstract class ElementMeta[T <: Element](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def newInstance(): T = runtimeClass.newInstance().asInstanceOf[T]
  def layerObject: LayerObject = ???

}
