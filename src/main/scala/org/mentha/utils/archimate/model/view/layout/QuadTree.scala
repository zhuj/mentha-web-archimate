package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model.view._
import scala.collection.mutable.ArrayBuffer

object QuadTree {

  private val MIN_SIZE: Double = 0.001d

  case class Mass(
    center: Point,
    value: Double,
    count: Int
  ) {

  }

  private val NO_MASS = Mass(center = Planar.ZERO_POINT, value = 0d, count = 0)

  trait MassObject {
    def mass: Mass
  }

  private def calculateMass(it: Iterable[MassObject]): Mass = {
    val value = it.map { _.mass.value }.sum
    if (value > 0d) {
      Mass(
        center = Point(
          x = it.map { v => v.mass.center.x * v.mass.value }.sum / value,
          y = it.map { v => v.mass.center.y * v.mass.value }.sum / value,
        ),
        value = value,
        count = it.map { v => v.mass.count }.sum,
      )
    } else {
      NO_MASS
    }
  }

  case class Bounds(
    min_X: Double,
    min_Y: Double,
    max_X: Double,
    max_Y: Double
  ) {
    require((min_X <= max_X) && (min_Y <= max_Y))

    val mid: Point = Point(x = 0.5 * (min_X + max_X), y = 0.5 * (min_Y + max_Y))

    @inline def width: Double = (max_X - min_X)
    @inline def height: Double = (max_Y - min_Y)
    val mean: Double = 0.5 * (width + height)

    @inline def nw: Bounds = Bounds(min_X, min_Y, mid.x, mid.y)
    @inline def ne: Bounds = Bounds(mid.x, min_Y, max_X, mid.y)
    @inline def sw: Bounds = Bounds(min_X, mid.y, mid.x, max_Y)
    @inline def se: Bounds = Bounds(mid.x, mid.y, max_X, max_Y)
  }

  trait BoundsObject {
    def bounds: Bounds
  }

  private def calculateBounds(it: Iterable[BoundsObject]): Bounds = {
    if (it.isEmpty) {
      Bounds(0d, 0d, 0d, 0d)
    } else {
      var min_X: Double = Double.MaxValue
      var min_Y: Double = Double.MaxValue
      var max_X: Double = Double.MinValue
      var max_Y: Double = Double.MinValue
      for { b <- it } {
        val bounds = b.bounds
        min_X = math.min(min_X, bounds.min_X)
        min_Y = math.min(min_Y, bounds.min_Y)
        max_X = math.max(max_X, bounds.max_X)
        max_Y = math.max(max_Y, bounds.max_Y)
      }
      Bounds(min_X, min_Y, max_X, max_Y)
    }
  }

  trait Body extends MassObject with BoundsObject {
  }


  sealed abstract class Quad(override val bounds: Bounds) extends MassObject with BoundsObject {
    private[QuadTree] def refresh(): Unit
    private[QuadTree] def +(body: Body): Quad
  }

  class Empty(bounds: Bounds) extends Quad(bounds) {
    override def mass: Mass = NO_MASS
    override def refresh(): Unit = {}
    override def +(body: Body): Quad = {
      new Single(bounds)(body)
    }
  }

  class Single(bounds: Bounds)(val body: Body) extends Quad(bounds) {
    override def mass: Mass = body.mass
    override def refresh(): Unit = {}
    override def +(body: Body): Quad = {
      if (bounds.mean > MIN_SIZE) {
        new Fork(bounds) + this.body + body
      } else {
        new Bunch(bounds)(this.body, body)
      }
    }
  }

  class Bunch(bounds: Bounds)(b1: Body, b2: Body) extends Quad(bounds) {
    private val bodies: ArrayBuffer[Body] = ArrayBuffer[Body](b1, b2)
    private var _mass: Mass = NO_MASS
    @inline override def mass: Mass = _mass
    override def refresh(): Unit = {
      this._mass = calculateMass(bodies)
    }
    override def +(body: Body): Quad = {
      bodies += body
      this
    }
  }

  class Fork(bounds: Bounds) extends Quad(bounds) {
    private var _nw: Quad = new Empty(bounds.nw)
    private var _ne: Quad = new Empty(bounds.ne)
    private var _sw: Quad = new Empty(bounds.sw)
    private var _se: Quad = new Empty(bounds.se)
    private var _mass: Mass = NO_MASS
    override def refresh(): Unit = {
      val seq = Seq(_nw, _ne, _sw, _se)
      seq.foreach { _.refresh() }
      this._mass = calculateMass(seq)
    }
    override def +(body: Body): Quad = {
      val w = body.mass.center.x < bounds.mid.x
      val n = body.mass.center.y < bounds.mid.y
      if (w) {
        if (n) { _nw += body }
        else { _sw += body }
      } else {
        if (n) { _ne += body }
        else { _se += body }
      }
      this
    }
    @inline override def mass: Mass = _mass
    @inline def nw: Quad = _nw
    @inline def ne: Quad = _ne
    @inline def sw: Quad = _sw
    @inline def se: Quad = _se
  }

  def apply(bodies: Seq[Body]): Quad = {
    val b = calculateBounds(bodies)
    val q = bodies.foldLeft(new Empty(b).asInstanceOf[Quad]) { _+_ }
    q.refresh()
    q
  }

}
