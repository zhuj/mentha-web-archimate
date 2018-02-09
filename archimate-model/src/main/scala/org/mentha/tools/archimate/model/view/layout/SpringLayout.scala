package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model.view._

abstract class SpringLayoutF(view: View) extends ForceBasedLayout(view) {

  private[layout] val SPRING_LENGTH = 0.75d
  private[layout] val SPRING_COEFFICIENT = 2.75e-1d
  private[layout] val REPULSION_COEFFICIENT = 2.0e-1d

  override def repulsion(d: Double): Double = -REPULSION_COEFFICIENT / sqr(d)

  private[layout] def springCoeff(displacement: Double) = {
    if (displacement < 0.0) {
      sqr(displacement) * displacement // repulsion
    } else {
      displacement // attraction
    }
  }

  def computeSprings(center: Vector, temperature: Double): Unit = for {edge <- edgesSeq } {
    val d = reduce(
      vector = edge.target.mass.center - edge.source.mass.center,
      x = SIZE_BOUND * (edge.source.bounds.width + edge.target.bounds.width),
      y = SIZE_BOUND * (edge.source.bounds.height + edge.target.bounds.height)
    )
    val l = math.sqrt(l2(d))
    val displacement = l - SPRING_LENGTH
    if (Math.abs(displacement) > MIN_DISTANCE) {
      val coeff = SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
      val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random(rnd) * coeff }
      edge.source.force += force
      edge.target.force -= force
    }
  }

  override def computeForces(center: Vector, temperature: Double): Unit = {
    computeSprings(center, temperature)
    computeRepulsion(center, temperature)
    computeGravityToCenter(center, temperature)
  }

}
