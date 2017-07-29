package org.mentha.utils.archimate.model.view

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.utils.Utils

import scala.annotation.tailrec
import scala.reflect.ClassTag

case class Point(x: Double, y: Double)
case class Size(width: Double, height: Double)

object View {

  private[model] val defaultPosition = Point(0, 0)
  private[model] val defaultSize = Size(120, 40)

  private[model] def middle(source: Point, target: Point): Point = Point(
    (source.x + target.x) / 2,
    (source.y + target.y) / 2
  )

  private[model] def middle(source: Point, points: Seq[Point], target: Point): Point = {
    if (points.isEmpty) middle(source, target)
    else middle(
      points((points.length-0) / 2),
      points((points.length-1) / 2)
    )
  }

  private[model] def size(source: Point, points: Seq[Point], target: Point): Size = {
    Size(
      Math.abs(source.x - target.x),
      Math.abs(source.y - target.y)
    )
  }

}

sealed abstract class ViewObject extends IdentifiedArchimateObject with VersionedArchimateObject with PropsArchimateObject with Vertex {
  def position: Point
  def size: Size
}

sealed abstract class ViewNode extends ViewObject {

  // position of the center of the box of the node
  private[model] var _position: Point = View.defaultPosition
  @inline def position: Point = _position
  def withPosition(position: Point): this.type = {
    this._position = position
    this
  }
  def withPosition(opt: Option[Point]): this.type = {
    for { position <- opt } { withPosition(position) }
    this
  }

  // box size in the center of _position
  private[model] var _size: Size = View.defaultSize
  @inline def size: Size = _size
  def withSize(size: Size): this.type = {
    this._size = size
    this
  }
  def withSize(opt: Option[Size]): this.type = {
    for { size <- opt } { withSize(size) }
    this
  }

}

sealed abstract class ViewEdge extends ViewObject with Edge[ViewObject] {

  // bend points
  private[model] var _points: Seq[Point] = Nil
  @inline def points: Seq[Point] = _points
  def withPoints(points: Seq[Point]): this.type = {
    this._points = points
    this
  }
  def withPoints(opt: Option[Seq[Point]]): this.type = {
    for { points <- opt } { withPoints(points) }
    this
  }

  // TODO: XXX: make it work safer - scheme should not contain cycles on the edges level
  override def isDeleted: Boolean = {
    super.isDeleted || (source.isDeleted || target.isDeleted)
  }

  override def position: Point = View.middle(
    source.position,
    points,
    target.position
  )

  override def size: Size = View.size(
    source.position,
    points,
    target.position
  )

}

/** Just a text notes */
final class ViewNotes extends ViewNode {

  private[model] var _text: String = ""
  @inline def text: String = _text
  def withText(text: String): this.type = {
    this._text = text
    this
  }

}

/** Connects everything */
final class ViewConnection(val source: ViewObject, val target: ViewObject) extends ViewEdge {

}

/** Archimate Concept in the View */
sealed trait ViewConcept[+T <: Concept] {
  self: ViewObject =>
  def concept: Concept with T
}

/** NodeConcept representation in the View */
final class ViewNodeConcept[+T <: NodeConcept](val concept: T) extends ViewNode with ViewConcept[T] {

}

/** Relationship representation in the View */
final class ViewRelationship[+T <: Relationship](val source: ViewObject with ViewConcept[_], val target: ViewObject with ViewConcept[_])(val concept: T) extends ViewEdge with ViewConcept[T] {
  require(source.concept == concept.source)
  require(target.concept == concept.target)

}


/**
  * http://pubs.opengroup.org/architecture/archimate3-doc/chap14.html
  * Viewpoints are a means to focus on particular aspects and layers of the architecture. These aspects and layers are determined by the concerns of a stakeholder with whom communication takes place.
  */
final class View(val viewpoint: ViewPoint = LayeredViewPoint) extends IdentifiedArchimateObject  with VersionedArchimateObject with PathBasedArchimateObject with NamedArchimateObject {
  require(null != viewpoint, "Viewpoint is required.")

  private[model] val _objects: Storage[ViewObject] = Storage.buildStorage

  def get[X <: ViewObject](id: Identifiable.ID)(implicit tp: ClassTag[X]): X = _objects[X](id)
  def add[X <: ViewObject](id: Identifiable.ID)(vo: X): X = _objects.store(vo, id)
  def add[X <: ViewObject](vo: X): X = _objects.store(vo)

  def objects[X <: ViewObject](implicit tp: ClassTag[X]): Iterable[X] = _objects.select[X](tp)
  def nodes: Iterable[ViewNode] = objects[ViewNode]
  def edges: Iterable[ViewEdge] = objects[ViewEdge]

  @transient private[model] val _locate_cache = new Cache()
  @inline private[model] def locate[X <: Concept, T <: ViewObject with ViewConcept[X]](concept: X): Option[T] =
    _locate_cache.cached(concept.id) {
      this._objects.values
        .collectFirst { case v: ViewConcept[_] if (v.concept == concept) => v.asInstanceOf[T] }
    }

  private[model] def attach[X <: Concept](concept: X): ViewObject with ViewConcept[X] = concept match {
    case n: NodeConcept => this.attach_node[NodeConcept](n).asInstanceOf[ViewObject with ViewConcept[X]]
    case r: Relationship => this.attach_edge[Relationship](r).asInstanceOf[ViewObject with ViewConcept[X]]
    case _ => throw new IllegalStateException(concept.getClass.getName)
  }

  private[model] def attach_node[X <: NodeConcept](concept: X): ViewNodeConcept[X] = this
    .locate[X, ViewNodeConcept[X]](concept)
    .getOrElse {
      this.add {
        new ViewNodeConcept[X](concept)
      }
    }

  private[model] def attach_edge[X <: Relationship](concept: X): ViewRelationship[X] = this
    .locate[X, ViewRelationship[X]](concept)
    .getOrElse {
      this.add {
        new ViewRelationship[X](
          source = this.attach(concept.source),
          target = this.attach(concept.target)
        )(concept)
      }
    }

  def backwardDependencies(vo: ViewObject): Set[ViewObject] = Utils.backwardDependencies(vo, this.edges)

}
