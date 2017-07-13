package org.mentha.utils.archimate.model

import org.apache.commons.lang3.builder.HashCodeBuilder
import org.mentha.utils.archimate.model.view.{Folder, View, ViewPoint}

import scala.reflect.ClassTag

/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap02.html
  * A collection of concepts in the context of the ArchiMate language structure.
  * Note: The top-level language structure is defined in detail in Section 3.2.
  * For a general definition of model, see the TOGAF framework - http://pubs.opengroup.org/architecture/archimate3-doc/front.html#ref4.
  * @see
  */
class Model extends IdentifiedArchimateObject with NamedArchimateObject {

  private[model] val concepts: Storage[Concept] = Storage.buildStorage
  private[model] val root: Folder = new Folder()

  def get[T <: Concept](id: Identifiable.ID): T = concepts(id)
  def add[T <: Concept](id: Identifiable.ID)(concept: T): T = concepts.store(concept, id)
  def add[T <: Concept](concept: T): T = concepts.store(concept)

  def select[X <: Concept](implicit tp: ClassTag[X]): Iterable[X] = concepts.select[X](tp)
  def nodes: Iterable[NodeConcept] = select[NodeConcept]
  def edges: Iterable[EdgeConcept] = select[EdgeConcept]

  def \(name: String): Folder = root \ name
  def \(names: List[String]): Folder = root \ names

  def \\(name: String)(viewpoint: ViewPoint = null): View = (root \\ name)(viewpoint)

  def getFolder(id: Identifiable.ID): Folder = root.getFolder(id).getOrElse(throw new IllegalStateException(s"No folder found with id=${id}"))
  def getView(id: Identifiable.ID): View = root.getView(id).getOrElse(throw new IllegalStateException(s"No view found with id=${id}"))

}
