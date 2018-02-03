package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model.view._

abstract class SpringLayoutF(view: View) extends ForceBasedLayout(view) {

  private[layout] val SPRING_LENGTH = 0.75d
  private[layout] val SPRING_COEFFICIENT = 2.75e-1d
  private[layout] val REPULSION_COEFFICIENT = 1.0e-1d
  private[layout] val SIZE_BOUND = 0.0d

  override val barnesHutCore = new BarnesHut( d => -REPULSION_COEFFICIENT / sqr(0.5d * d) )

  private[layout] def springCoeff(displacement: Double) = {
    if (displacement < 0.0) {
      sqr(displacement) * displacement // repulsion
    } else {
      displacement // attraction
    }
  }

  def computeSprings(quadTree: QuadTree.Quad, temperature: Double): Unit = for {edge <- edgesSeq } {
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

  override def computeForces(quadTree: QuadTree.Quad, temperature: Double): Unit = {
    computeSprings(quadTree, temperature)
    computeRepulsion(quadTree, temperature)
    computeGravityToCenter(quadTree, temperature)
  }

}

//abstract class SpringLayoutE(view: View) extends EnergyBasedLayout(view) {
//
//  private[layout] val SPRING_LENGTH = 0.75d
//  private[layout] val SPRING_COEFFICIENT = 2.75e-1d
//  private[layout] val REPULSION_COEFFICIENT = 1.0e-1d
//  private[layout] val SIZE_BOUND = 0.0d
//
//  override val barnesHutCore = new BarnesHut( d => REPULSION_COEFFICIENT / sqr(0.5d * d) )
//
//  private[layout] def springCoeff(displacement: Double) = {
//    if (displacement < 0.0) {
//      sqr(displacement) * displacement
//    } else {
//      displacement
//    }
//  }
//
//  def computeSprings(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double) = {
//    nodesEdges(node).map { edge =>
//      val d = reduce(
//        vector = edge.target.mass.center - edge.source.mass.center,
//        x = SIZE_BOUND * (edge.source.bounds.width + edge.target.bounds.width),
//        y = SIZE_BOUND * (edge.source.bounds.height + edge.target.bounds.height)
//      )
//      val l = math.sqrt(l2(d))
//      val displacement = l - SPRING_LENGTH
//      if (Math.abs(displacement) > MIN_DISTANCE) {
//        SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
//      } else {
//        0
//      }
//    }.fold(0d) { _+_ }
//  }
//
//  override def computeEnergy(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double): Double = {
//    computeSprings(quadTree, node, temperature) +
//      computeRepulsion(quadTree, node, temperature) +
//      computeGravityToCenter(quadTree, node, temperature)
//  }
//
//}
