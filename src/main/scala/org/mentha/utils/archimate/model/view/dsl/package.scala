package org.mentha.utils.archimate.model.view

package object dsl {

  import org.mentha.utils.archimate.model._

  implicit class NodeConceptToView[+T <: NodeConcept](val concept: T) {
    @inline def attach(implicit view: View): ViewNodeConcept[T] = view.attach_node(concept)
  }

  implicit class EdgeConceptToView[+T <: Relationship](val concept: T) {
    @inline def attach(implicit view: View): ViewRelationship[T] = view.attach_edge(concept)
  }

  object directions extends Enumeration {
    type Type = Value
    val Left, Right, Up, Down = Value
  }

  object geometry {
    @inline def x(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = { (1.0-shift)*v1.position.x + (shift)*v2.position.x }
    @inline def y(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = { (1.0-shift)*v1.position.y + (shift)*v2.position.y }
    @inline def w(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = { (1.0-shift)*v1.size.width + (shift)*v2.size.width }
    @inline def h(v1: ViewObject, v2: ViewObject, shift: Double = 0.5) = { (1.0-shift)*v1.size.height + (shift)*v2.size.height }
  }

  implicit class ImplicitViewNode[+T <: ViewNode](node: T) {

    def scaleWidth(scale: Double): T = node withSize { Size(scale*node.size.width, node.size.height) }
    def scaleHeight(scale: Double): T = node withSize { Size(node.size.width, scale*node.size.height) }

    def doubleWidth(): T = scaleWidth(2.0)
    def doubleHeight(): T = scaleHeight(2.0)

    @inline def place(dir: directions.Type, v: ViewObject)(implicit space: Size): T = place(dir, v, v)(space)

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
