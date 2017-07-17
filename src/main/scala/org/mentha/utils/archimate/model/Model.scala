package org.mentha.utils.archimate.model

import org.mentha.utils.archimate.model.view._

import scala.reflect.ClassTag

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap02.html
  * A collection of concepts in the context of the ArchiMate language structure.
  * Note: The top-level language structure is defined in detail in Section 3.2.
  * For a general definition of model, see the TOGAF framework - http://pubs.opengroup.org/architecture/archimate3-doc/front.html#ref4.
  * @see
  */
class Model extends IdentifiedArchimateObject with VersionedArchimateObject with NamedArchimateObject {

  private[model] val _concepts: Storage[Concept] = Storage.buildStorage
  private[model] val _views: Storage[View] = Storage.buildStorage

  def concept[T <: Concept](id: Identifiable.ID)(implicit tp: ClassTag[T]): T = _concepts[T](id)
  def view(id: Identifiable.ID): View = _views[View](id)

  def add(id: Identifiable.ID) = new {
    def apply[T <: Concept](concept: T): T = _concepts.store(concept, id)
    def apply(view: View): View = _views.store(view, id)
  }

  def add[T <: Concept](concept: T): T = _concepts.store(concept)
  def add(view: View): View = _views.store(view)

  def concepts[X <: Concept](implicit tp: ClassTag[X]): Iterable[X] = _concepts.select[X](tp)
  def nodes: Iterable[NodeConcept] = concepts[NodeConcept]
  def edges: Iterable[EdgeConcept] = concepts[EdgeConcept]

  def views: Iterable[View] = _views.select[View]

  def findView(path: List[String], name: String): Option[View] =
    views.collectFirst { case v if v.path == path && v.name == name => v }

  def <<(path: List[String]) = new {
    def <<(name: String)(viewpoint: ViewPoint = null): View = findView(path, name)
      .map {
        case v if (null == viewpoint) || (v.viewpoint == viewpoint) => v
        case v => throw new IllegalStateException(s"View @ `${name}` has wrong viewpoint: ${v.viewpoint}")
      }
      .getOrElse { add(new View(viewpoint) withName name) }
  }

}
