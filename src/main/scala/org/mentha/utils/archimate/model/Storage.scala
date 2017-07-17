package org.mentha.utils.archimate.model

import java.util.NoSuchElementException

import org.mentha.utils.archimate.model.Identifiable.ID
import org.mentha.utils.uuid.FastTimeBasedIdGenerator

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.runtime.Nothing$

trait Identifiable {

  private[model] var _id: Identifiable.ID = Identifiable.EMPTY_ID
  @inline def id: Identifiable.ID = _id

  def withId(id: Identifiable.ID): this.type = {
    this._id = id
    this
  }

  override def hashCode(): Int = id.hashCode
  override def equals(obj: scala.Any): Boolean = obj match {
    case o: this.type => id == o.id
    case _ => false
  }

  private[model] var _deleted: Boolean = false
  @inline def isDeleted: Boolean = _deleted

  def markAsDeleted(marker: Boolean = true): this.type = {
    this._deleted = marker
    this
  }

}

object Identifiable {

  type ID = String

  val EMPTY_ID: ID = ""

  def generateId(): ID = FastTimeBasedIdGenerator
    .generateId(0l)

}

trait Storage[T <: Identifiable] {

  def classTag: ClassTag[T]
  def get[X <: T](id: Identifiable.ID)(implicit tp: ClassTag[X]): Option[X]
  def apply[X <: T](id: Identifiable.ID)(implicit tp: ClassTag[X]): X = get(id)
    .getOrElse {
      val cls = if (tp.runtimeClass == classOf[Nothing$]) { this.classTag.runtimeClass } else { tp.runtimeClass }
      throw new NoSuchElementException(s"No ${cls.getSimpleName} found with id=${id}.")
    }

  def store[X <: T](entity: X): X = store(entity, Identifiable.generateId())
  def store[X <: T](entity: X, id: => Identifiable.ID): X
  def values: Iterable[T]
  def select[X <: T](implicit tp: ClassTag[X]): Iterable[X]

  def isEmpty: Boolean

}

object Storage {

  def buildStorage[T <: Identifiable](implicit et: ClassTag[T]): Storage[T] =
    new StorageImpl[T](et.runtimeClass.getSimpleName)(et)

}

/** */
class StorageImpl[T <: Identifiable](val entityName: String)(implicit et: ClassTag[T]) extends Storage[T] {

  private[model] val _map = mutable.LinkedHashMap[Identifiable.ID, T]()


  override def classTag: ClassTag[T] = et

  override def get[X <: T](id: ID)(implicit tp: ClassTag[X]): Option[X] = _map.get(id).map { _.asInstanceOf[X] }

  override def store[X <: T](entity: X, id: => Identifiable.ID): X = {
    if (entity._id.isEmpty) { entity._id = id }
    _map.put(entity.id, entity) match {
      case None => entity
      case Some(prev) if (prev eq entity) => entity
      case Some(prev) => throw new IllegalStateException(s"Duplicate ${entityName} with id: ${entity.id}, previous=${prev.toString}")
    }
  }

  override def isEmpty: Boolean = _map.isEmpty

  override def values: Iterable[T] = _map.values

  override def select[X <: T](implicit tp: ClassTag[X]): Iterable[X] = values
    .collect { case x: X => x.asInstanceOf[X] }

}


class Cache {

  @transient
  private[model] val _cache = mutable.HashMap[Identifiable.ID, Any]()

  private[model] def cached[T <: Identifiable](id: Identifiable.ID)(lookup: => Option[T]): Option[T] = {
    _cache.get(id) match {
      case Some(v) => Some(v.asInstanceOf[T])
      case None => lookup match {
        case s @ Some(v) => {
          _cache.put(id, v)
          s
        }
        case None => None
      }
    }
  }
}