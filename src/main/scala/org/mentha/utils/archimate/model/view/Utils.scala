package org.mentha.utils.archimate.model.view

/**
  *
  * @param x
  * @param y
  */
case class Vector(x: Double, y: Double) {

  @inline def +(v: Vector): Vector = Vector(x + v.x, y + v.y)
  @inline def -(v: Vector): Vector = Vector(x - v.x, y - v.y)
  @inline def *(n: Double): Vector = Vector(x * n, y * n)
  @inline def length: Double = math.hypot(x, y)

}

object Vector {

  val ZERO: Vector = Vector(0d, 0d)

  @inline def middle(source: Vector, target: Vector): Vector = Vector(
    0.5d * (source.x + target.x),
    0.5d * (source.y + target.y)
  )

  @inline def middle(source: Vector, points: Seq[Vector], target: Vector): Vector = {
    if (points.isEmpty) middle(source, target)
    else middle(
      points((points.length-0) >>> 1), // div2
      points((points.length-1) >>> 1)  // div2
    )
  }

  /** @return random unity vector */
  def random: Vector = {
    val rnd = new java.util.Random
    var v1 = 0.0d
    var v2 = 0.0d
    var s = 0.0d
    do {
      v1 = 2 * rnd.nextDouble - 1
      v2 = 2 * rnd.nextDouble - 1
      s = v1 * v1 + v2 * v2
    } while ( s >= 1 || s == 0 )
    val multiplier = 1.0d / Math.sqrt(s)
    Vector(x = v1 * multiplier, y = v2 * multiplier)
  }

}

/**
  *
  * @param width
  * @param height
  */
case class Size(width: Double, height: Double) {

  @inline def mean: Double = 0.5d * (width + height)

}

object Size {

  val ZERO: Size = Size(0d, 0d)

  @inline def size(source: Vector, points: Seq[Vector], target: Vector): Size = {
    // TODO: use points (they could be out of the simple bounds)
    Size(
      Math.abs(source.x - target.x),
      Math.abs(source.y - target.y)
    )
  }

}

