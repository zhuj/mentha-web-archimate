package org.mentha.utils.archimate.model.nodes

import org.mentha.utils.archimate.model.{ConceptMeta, Element}

import scala.reflect.ClassTag

/** */
class ElementMeta[T <: Element](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def newInstance(): T = runtimeClass.newInstance().asInstanceOf[T]

}
