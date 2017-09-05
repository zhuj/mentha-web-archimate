package org.mentha.utils.archimate.model.view


case class Point(x: Double, y: Double) {

  @inline def +(v: Point) = Point(x + v.x, y + v.y)
  @inline def -(v: Point) = Point(x - v.x, y - v.y)
  @inline def *(n: Double) = Point(x * n, y * n)
  @inline def /(n: Double) = this * (1.0 / n)

}

case class Size(width: Double, height: Double) {

  @inline def asPoint: Point = Point(width, height)
  @inline def mean: Double = 0.5 * (width + height)
}

trait PlanarObject {
  def position: Point
  def size: Size
}

object Planar {

  val ZERO_POINT: Point = Point(0d, 0d)

  @inline private[model] def middle(source: Point, target: Point): Point = Point(
    0.5 * (source.x + target.x),
    0.5 * (source.y + target.y)
  )

  @inline private[model] def middle(source: Point, points: Seq[Point], target: Point): Point = {
    if (points.isEmpty) middle(source, target)
    else middle(
      points((points.length-0) >>> 1), // div2
      points((points.length-1) >>> 1)  // div2
    )
  }

  @inline private[model] def size(source: Point, points: Seq[Point], target: Point): Size = {
    Size(
      Math.abs(source.x - target.x),
      Math.abs(source.y - target.y)
    )
  }

}