package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model.view._

/**
  *
  * @param center
  * @param value
  */
case class Mass(
  center: Vector,
  value: Double
) {
  // TODO: enable for testing only:
  if (false) {
    require(java.lang.Double.isFinite(value), "value has to be finite")
    require(value >= 0d, "value has to be either zero or a positive number")
  }
}

/**
  *
  */
object Mass {

  val NO_MASS: Mass = Mass(center = Vector.ZERO, value = 0d)

  def calculateMass[T](it: Iterable[T], accessor: T => Mass): Mass = {
    if (it.isEmpty) {
      NO_MASS
    } else {
      var value = 0.0d
      var x = 0.0d
      var y = 0.0d
      for { t <- it; m = accessor(t) } {
        value += m.value
        x += m.value * m.center.x
        y += m.value * m.center.y
      }
      Mass(
        center = Vector(
          x = x / value,
          y = y / value
        ),
        value = value
      )
    }
  }

  @inline def calculateMass(it: Seq[Mass]): Mass = calculateMass[Mass](it, (m)=>m)

}

/**
  *
  * @param min_X
  * @param min_Y
  * @param max_X
  * @param max_Y
  */
case class Bounds(
  min_X: Double,
  min_Y: Double,
  max_X: Double,
  max_Y: Double
) {

  // TODO: enable for testing only:
  if (false) {
    require(java.lang.Double.isFinite(min_X), "min_X has to be finite")
    require(java.lang.Double.isFinite(max_X), "max_X has to be finite")
    require(java.lang.Double.isFinite(min_Y), "min_Y has to be finite")
    require(java.lang.Double.isFinite(max_Y), "max_Y has to be finite")
    require(min_X <= max_X, "min_X has to be less or equal to max_X")
    require(min_Y <= max_Y, "min_Y has to be less or equal to max_Y")
  }

    /** middle point of the bounds */
  val mid: Vector = Vector(x = 0.5d * (min_X + max_X), y = 0.5 * (min_Y + max_Y))

  @inline def width: Double = max_X - min_X
  @inline def height: Double = max_Y - min_Y
  @inline def mean: Double = math.max(width, height)
}

/**
  *
  */
object Bounds {

  @inline def apply(position: Vector, width: Double, height: Double): Bounds = {
    val w2 = width
    val h2 = height
    Bounds(
      min_X = position.x - w2, max_X = position.x + w2,
      min_Y = position.y - h2, max_Y = position.y + h2
    )
  }

  @inline def apply(position: Vector, size: Size): Bounds = apply(
    position = position,
    width = size.width,
    height = size.height
  )

  def calculateBounds[T](it: Iterable[T], accessor: T => Bounds): Bounds = {
    if (it.isEmpty) {
      Bounds(0d, 0d, 0d, 0d)
    } else {
      var min_X = Double.PositiveInfinity
      var min_Y = Double.PositiveInfinity
      var max_X = Double.NegativeInfinity
      var max_Y = Double.NegativeInfinity
      for { t <- it; b = accessor(t) } {
        min_X = Math.min(min_X, b.min_X)
        min_Y = Math.min(min_Y, b.min_Y)
        max_X = Math.max(max_X, b.max_X)
        max_Y = Math.max(max_Y, b.max_Y)
      }

      // TODO: enable for testing only:
      if (true) {
        require(java.lang.Double.isFinite(min_X), "min_X has to be finite")
        require(java.lang.Double.isFinite(max_X), "max_X has to be finite")
        require(java.lang.Double.isFinite(min_Y), "min_Y has to be finite")
        require(java.lang.Double.isFinite(max_Y), "max_Y has to be finite")
        require(min_X <= max_X, "min_X has to be less or equal to max_X")
        require(min_Y <= max_Y, "min_Y has to be less or equal to max_Y")
      }

      Bounds(min_X, min_Y, max_X, max_Y)
    }
  }

  @inline def calculateBounds(it: Iterable[Bounds]): Bounds = calculateBounds[Bounds](it, (b)=>b)

}

trait Body {
  def mass: Mass
  def bounds: Bounds
}
