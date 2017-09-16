package org.mentha.utils.archimate.model

import java.util.NoSuchElementException
import java.util.function.LongSupplier

import scala.collection.mutable
import scala.reflect.ClassTag
import scala.runtime.Nothing$
import scala.util.DynamicVariable

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
  import org.mentha.utils.uuid.FastTimeBasedIdGenerator

  private[model] val timeSource = new DynamicVariable[LongSupplier](
    () => System.currentTimeMillis()
  )

  type ID = String

  val EMPTY_ID: ID = ""

  @inline def ensureId[T <: Identifiable](entity: T, id: => Identifiable.ID): T = {
    if (entity.id == EMPTY_ID) { entity.withId(id) }
    else entity
  }

  @inline private def typeIdentifier(klass: Class[_]): Short = {
    (klass.getName.hashCode & 0x7ff).toShort
  }

  @inline def generateId(klass: Class[_]): ID = FastTimeBasedIdGenerator
    .generateId(typeIdentifier(klass), timeSource.value.getAsLong)

  @inline def validateId(identifiable: Identifiable): Unit = {
    require(
      FastTimeBasedIdGenerator.validateId(typeIdentifier(identifiable.getClass), identifiable.id),
      s"id=${identifiable.id} : ${FastTimeBasedIdGenerator.getCheckSum(identifiable.id)} should be ${FastTimeBasedIdGenerator.getCheckSum(typeIdentifier(identifiable.getClass))}"
    )
  }

}

trait Storage[T <: Identifiable] {

  def classTag: ClassTag[T]
  def get[X <: T](id: Identifiable.ID)(implicit tp: ClassTag[X]): Option[X]
  def apply[X <: T](id: Identifiable.ID)(implicit tp: ClassTag[X]): X = get(id)
    .getOrElse {
      val cls = if (tp.runtimeClass == classOf[Nothing$]) { this.classTag.runtimeClass } else { tp.runtimeClass }
      throw new NoSuchElementException(s"No ${cls.getSimpleName} found with id=${id}.")
    }

  def store[X <: T](entity: X): X = store(entity, Identifiable.generateId(entity.getClass))
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

  override def get[X <: T](id: Identifiable.ID)(implicit tp: ClassTag[X]): Option[X] = _map.get(id).map { _.asInstanceOf[X] }

  override def store[X <: T](entity: X, id: => Identifiable.ID): X = {
    Identifiable.ensureId(entity, id)
    // TODO: Identifiable.validateId(entity)
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