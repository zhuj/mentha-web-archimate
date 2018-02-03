package org.mentha.tools.archimate.model.view.layout

import scala.collection.mutable.ArrayBuffer

object QuadTree {

  @inline private def requireNonQuad(body: Body): Unit = {
    require(!body.isInstanceOf[Quad], "Body argument shouldn't be a Quad")
  }

  private val MIN_SIZE: Double = 1e-2d

  sealed abstract class Quad(override val bounds: Bounds) extends Body {
    private[QuadTree] def recalculateMass(): Mass
    private[QuadTree] def +(body: Body): Quad
  }

  class Empty(bounds: Bounds) extends Quad(bounds) {
    override def mass: Mass = Mass.NO_MASS
    private[QuadTree] override def recalculateMass(): Mass = mass
    private[QuadTree] override def +(body: Body): Quad = {
      // TEST: requireNonQuad(body)
      new Single(bounds)(body)
    }
  }

  class Single(bounds: Bounds)(val body: Body) extends Quad(bounds) {
    // TEST: requireNonQuad(body)
    override def mass: Mass = body.mass
    private[QuadTree] override def recalculateMass(): Mass = mass
    private[QuadTree] override def +(body: Body): Quad = {
      // TEST: requireNonQuad(body)
      if (bounds.mean < MIN_SIZE) {
        new Bunch(bounds)(this.body, body)
      } else {
        new Fork(bounds) + this.body + body
      }
    }
  }

  class Bunch(bounds: Bounds)(b1: Body, b2: Body) extends Quad(bounds) {
    // TEST: requireNonQuad(b1)
    // TEST: requireNonQuad(b2)
    private val bodies: ArrayBuffer[Body] = ArrayBuffer[Body](b1, b2)
    private var _mass: Mass = Mass.NO_MASS
    @inline override def mass: Mass = _mass
    private[QuadTree] override def recalculateMass(): Mass = {
      this._mass = Mass.calculateMass[Body]( bodies, _.mass ) // there is no quad inside, don't have to recalculate mass
      this._mass
    }
    private[QuadTree] override def +(body: Body): Quad = {
      // TEST: requireNonQuad(body)
      bodies += body
      this
    }
  }

  class Fork(bounds: Bounds) extends Quad(bounds) {
    private var _nw: Quad = new Empty(bounds.nw)
    private var _ne: Quad = new Empty(bounds.ne)
    private var _sw: Quad = new Empty(bounds.sw)
    private var _se: Quad = new Empty(bounds.se)
    private var _mass: Mass = Mass.NO_MASS
    private[QuadTree] override def recalculateMass(): Mass = {
      this._mass = Mass.calculateMass[Quad]( children, _.recalculateMass() )
      this._mass
    }
    private[QuadTree] override def +(body: Body): Quad = {
      // TEST: requireNonQuad(body)
      val w = body.mass.center.x <= bounds.mid.x
      val n = body.mass.center.y <= bounds.mid.y
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
    @inline def children: Iterable[Quad] = Seq(_nw, _ne, _sw, _se)
  }

  def apply(bodies: Iterable[Body]): Quad = {
    val b = Bounds.calculateBounds[Body]( bodies, _.bounds  )
    val q = bodies.foldLeft(new Empty(b).asInstanceOf[Quad]) { _+_ }
    q.recalculateMass()
    q
  }

}
