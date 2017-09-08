package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model.view._

class SpringLayout(view: View) extends ForceBasedLayout(view) {

  private val SPRING_LENGTH = 0.75d

  private val SPRING_COEFFICIENT = 2.75e-1d
  private val REPULSION_COEFFICIENT = 1.0e-1d

  override val barnesHutCore = new BarnesHut( d => -REPULSION_COEFFICIENT / sqr(0.5d * d) )

  def computeSprings(quadTree: QuadTree.Quad, temperature: Double) = for {edge <- edgesSeq } {
    val d = edge.target.mass.center - edge.source.mass.center
    val l = math.sqrt(l2(d))
    val displacement = l - SPRING_LENGTH
    if (Math.abs(displacement) > MIN_DISTANCE) {
      val coeff = SPRING_COEFFICIENT * 0.5 * {
        if (displacement < 0.0) { sqr(displacement) * displacement } else displacement
      }
      val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random * coeff }
      edge.source.force += force
      edge.target.force -= force
    }

  }

  override def computeForces(quadTree: QuadTree.Quad, temperature: Double): Unit = {
    computeSprings(quadTree, temperature)
    computeRepulsion(quadTree, temperature)
    computeGravityToCenter(quadTree)
  }


}
