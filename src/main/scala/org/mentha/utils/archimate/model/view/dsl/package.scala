package org.mentha.utils.archimate.model.view

import org.mentha.utils.archimate.model.edges.{CompositionRelationship, StructuralRelationship, StructuralRelationships}

import scala.util.{Success, Try}

package object dsl {

  import org.mentha.utils.archimate.model._

  implicit class NodeConceptToView[T <: NodeConcept](val concept: T) {
    @inline def attach(implicit view: View): ViewNodeConcept[T] = view.attach_node(concept)
  }

  implicit class EdgeConceptToView[T <: Relationship](val concept: T) {
    @inline def attach(implicit view: View): ViewRelationship[T] = view.attach_edge(concept)
  }

  object directions extends Enumeration {
    type Type = Value
    val Left, Right, Up, Down = Value
  }

  object geometry {
    @inline def x(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = {
      (1.0 - shift) * v1.position.x + (shift) * v2.position.x
    }

    @inline def y(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = {
      (1.0 - shift) * v1.position.y + (shift) * v2.position.y
    }

    @inline def w(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = {
      (1.0 - shift) * v1.size.width + (shift) * v2.size.width
    }

    @inline def h(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = {
      (1.0 - shift) * v1.size.height + (shift) * v2.size.height
    }
  }

  implicit class ImplicitViewNode[+T <: ViewNode](node: T) {

    def scaleWidth(scale: Double): T = node withSize {
      Size(scale * node.size.width, node.size.height)
    }

    def scaleHeight(scale: Double): T = node withSize {
      Size(node.size.width, scale * node.size.height)
    }

    def doubleWidth(): T = scaleWidth(2.0)

    def doubleHeight(): T = scaleHeight(2.0)

    @inline def place(dir: directions.Type, v: ViewObject)(implicit space: Size): T = place(dir, v, v)(space)

    /** places the element between the given element + shifted to the given direction */
    def place(dir: directions.Type, v1: ViewObject, v2: ViewObject)(implicit space: Size): T = node.withPosition(
      dir match {
        case directions.Up => Point(
          x = geometry.x(v1, v2),
          y = geometry.y(v1, v2) - (geometry.h(v1, v2) + node.size.height) / 2 - space.height
        )
        case directions.Down => Point(
          x = geometry.x(v1, v2),
          y = geometry.y(v1, v2) + (geometry.h(v1, v2) + node.size.height) / 2 + space.height
        )
        case directions.Left => Point(
          x = geometry.x(v1, v2) - (geometry.w(v1, v2) + node.size.width) / 2 - space.width,
          y = geometry.y(v1, v2)
        )
        case directions.Right => Point(
          x = geometry.x(v1, v2) + (geometry.w(v1, v2) + node.size.width) / 2 + space.width,
          y = geometry.y(v1, v2)
        )
      }
    )

    /** places the element in the intersection of vertical and horizontal position of the given elements */
    def place(vx: ViewObject, vy: ViewObject)(implicit space: Size): T = node.withPosition(
      Point(
        x = vx.position.x,
        y = vy.position.y
      )
    )


    def move(dir: directions.Type, amount: Double = 1.0)(implicit space: Size): T = node.withPosition(
      dir match {
        case directions.Up => Point(
          x = node.position.x,
          y = node.position.y - (node.size.height + space.height) * amount
        )
        case directions.Down => Point(
          x = node.position.x,
          y = node.position.y + (node.size.height + space.height) * amount
        )
        case directions.Left => Point(
          x = node.position.x - (node.size.width + space.width) * amount,
          y = node.position.y
        )
        case directions.Right => Point(
          x = node.position.x + (node.size.width + space.width) * amount,
          y = node.position.y
        )
      }
    )

    /** wraps the current element over the given group */
    def wrap(callback: (ViewNode, ViewNode) => Unit, elements: ViewNode*)(implicit space: Size): T = {

      if (elements.isEmpty) {
        throw new IllegalStateException("")
      }

      var x0 = Double.MaxValue
      var y0 = Double.MaxValue
      var x1 = Double.MinValue
      var y1 = Double.MinValue
      for { el <- elements } {
        callback(node, el)
        x0 = Math.min(x0, el.position.x - el.size.width / 2)
        x1 = Math.max(x1, el.position.x + el.size.width / 2)
        y0 = Math.min(y0, el.position.y - el.size.height / 2)
        y1 = Math.max(y1, el.position.y + el.size.height / 2)
      }

      node withPosition { Point( (x0+x1)/2, (y0+y1)/2 - 0.15*space.height ) } withSize { Size((x1-x0) + 0.5*space.width, (y1-y0) + 0.75*space.height) }
    }
  }

  def wrapWithComposition(implicit model: Model, view: View): (ViewNode, ViewNode) => Unit = {
    case (src: ViewGroup, dst) => view.add { new ViewConnection(src, dst) }
    case (src: ViewNodeConcept[_], dst: ViewNodeConcept[_]) => view.add {
      new ViewRelationship[Relationship](src, dst)(
        model.add {
          Seq(StructuralRelationships.composition, StructuralRelationships.assignment, StructuralRelationships.realization, StructuralRelationships.aggregation)
            .iterator // make it lazy
            .map { meta => Try[Relationship] { meta.newInstance(src.concept, dst.concept).validate } }
            .collectFirst { case Success(r) => r }
            .getOrElse { throw new IllegalArgumentException(s"There is no possible relationship between ${src} and ${dst}") }
        }
      )
    }
    case (src, dst) => throw new IllegalArgumentException(s"There is no possible relationship between ${src} and ${dst}")
  }

  implicit class ImplicitViewEdge[+T <: ViewEdge](edge: T) {

    def points(route: (Double, Double)*): T = edge.withPoints {
      route.map { case (x, y) => Point(x, y) }
    }

    def route(route: (Double, Double)*): T = edge.withPoints {
      route.foldLeft((edge.source.position, List[Point]())) {
        case ((sp, seq), (x, y)) => {
          val p = Point(sp.x + x, sp.y + y)
          (p, p :: seq)
        }
      } match {
        case (_, b) => b.reverse
      }
    }

    def flex(levels: Double*): T = {
      if (edge.source eq edge.target) {
        throw new IllegalStateException(s"Edge: ${edge} is a loop. Use the `routeLoop` method instead.")
      } else {

        val vx = edge.target.position.x - edge.source.position.x
        val vy = edge.target.position.y - edge.source.position.y

        val dA = 0.1
        val rx = -vy*dA
        val ry =  vx*dA
        val l = 1.0 + levels.length

        edge.withPoints(
          levels.zipWithIndex.map {
            case (lvl, idx) => Point(
              geometry.x(edge.source, edge.target, (1.0 + idx) / l) + rx*lvl,
              geometry.y(edge.source, edge.target, (1.0 + idx) / l) + ry*lvl
            )
          }
        )
      }
    }

    def routeLoop(dir: directions.Type, level: Int = 1): T = {
      if (edge.source eq edge.target) {
        val x = edge.source.position.x
        val y = edge.source.position.y
        val h = edge.source.size.height
        val w = edge.source.size.width

        val d10 = 1.0 + 0.25*level
        val d20 = Math.pow(d10, 2.0)
        val d05 = Math.pow(d10, 0.5)

        val dA = 0.45*d20
        val dB = 0.75*d05
        val dC = 0.95*d10


        dir match {
          case directions.Up => points(
            (x - dA*h, y - dB*h), (x, y - dC*h), (x + dA*h, y - dB*h)
          )
          case directions.Down => points(
            (x - dA*h, y + dB*h), (x, y + dC*h), (x + dA*h, y + dB*h)
          )
          case directions.Left => points(
            (x - dB*h, y - dA*h), (x - dC*h, y), (x - dB*h, y + dA*h),
          )
          case directions.Right => points(
            (x + dB*h, y - dA*h), (x + dC*h, y), (x + dB*h, y + dA*h),
          )
        }
      } else {
        throw new IllegalStateException(s"Edge: ${edge} is not a loop. Use the `flex` method instead.")
      }
    }
  }

  def in(view: View) = new {

    // TODO: def apply[T <: NodeConcept: ClassTag](r: => NodeConcept with T): ViewNodeConcept[NodeConcept with T] = r.attach(view)
    // TODO: def apply[T <: Relationship: ClassTag](r: => Relationship with T): ViewRelationship[Relationship with T] = r.attach(view)
    // TODO: def apply(text: String): ViewNotes = view.add { new ViewNotes withText(text) }
    // TODO: def apply(left: ViewObject, right: ViewObject): ViewConnection = view.add { new ViewConnection(left, right) }

    def node[T <: NodeConcept](r: => NodeConcept with T): ViewNodeConcept[NodeConcept with T] = r.attach(view)
    def edge[T <: Relationship](r: => Relationship with T): ViewRelationship[Relationship with T] = r.attach(view)
    def notes(text: String): ViewNotes = view.add { new ViewNotes withText(text) }
    def connect(left: ViewObject, right: ViewObject): ViewConnection = view.add { new ViewConnection(left, right) }
  }

  @inline def $[T <: Concept](t: ViewObject with ViewConcept[T]): T = t.concept

}
