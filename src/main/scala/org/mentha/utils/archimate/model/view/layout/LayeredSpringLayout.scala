package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

import scala.annotation.tailrec

class LayeredSpringLayout(view: View) extends ForceBasedLayout(view) {

  private val layers = Seq(
    MotivationLayer,
    StrategyLayer,
    BusinessLayer,
    ApplicationLayer,
    PhysicalLayer,
    TechnologyLayer,
    ImplementationLayer
  )

  @inline private def layerObject(n: NodeWrapper): Option[LayerObject] = Option(n.node)
    .collect { case n: ViewNodeConcept[_] => n.concept }
    .collect { case e: Element => e.meta.layerObject }

  private val layeredNodesList = layers
    .map { case (lo) => (lo, nodesSeq.filter { node => layerObject(node).orNull == lo } ) }
    .filter { _._2.nonEmpty }
    .toList

  private val SPRING_LENGTH = 0.75d

  private val SPRING_COEFFICIENT = 2.75e-1d
  private val REPULSION_COEFFICIENT = 1.0e-1d

  override val barnesHutCore = new BarnesHut(
    d => -REPULSION_COEFFICIENT / sqr(0.5d * d),
    reducer = SPRING_LENGTH
  )

  private def computeLayers(quadTree: QuadTree.Quad) = {
    def compute(list: List[(LayerObject, Seq[NodeWrapper])]): Unit = {
      val border_Y = list.head._2.map { _.bounds.max_Y }.max + SPRING_LENGTH
      for { t <- list.tail; n <- t._2 } {
        val displacement = n.bounds.min_Y - border_Y
        if (displacement < 0) {
          n.force += Vector(x = 0, y = -SPRING_COEFFICIENT * springCoeff(displacement))
        }
      }
    }

    @tailrec def core(list: List[(LayerObject, Seq[NodeWrapper])]): Unit = {
      if (list.nonEmpty) {
        compute(list)
        core(list.tail)
      }
    }
    core(layeredNodesList)
  }

  private def springCoeff(displacement: Double) = {
    if (displacement < 0.0) { sqr(displacement) * displacement } else displacement
  }

  private def computeSprings(quadTree: QuadTree.Quad) = for { edge <- edgesSeq } {

    val lt = layerObject(edge.target).orNull
    val ls = layerObject(edge.source).orNull

    if (null != lt && null != ls && lt != ls) {

      val d = edge.target.mass.center - edge.source.mass.center
      val l = 0.2 * Math.abs(d.x) + 0.8 * Math.abs(d.y)
      val displacement = l - SPRING_LENGTH
      if (Math.abs(displacement) > MIN_DISTANCE) {
        val coeff = SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
        val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random * coeff }
        edge.source.force += force
        edge.target.force -= force
      }

    } else {

      val d0 = edge.target.mass.center - edge.source.mass.center
      val d = if (false) d0 else Vector(
        x = d0.x - math.signum(d0.x) * 0.1 * (edge.source.bounds.width + edge.target.bounds.width),
        y = d0.y - math.signum(d0.y) * 0.1 * (edge.source.bounds.height + edge.target.bounds.height)
      )

      val l = math.sqrt(l2(d))
      val displacement = l - SPRING_LENGTH
      if (Math.abs(displacement) > MIN_DISTANCE) {
        val coeff = SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
        val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random * coeff }
        edge.source.force += force
        edge.target.force -= force
      }

    }



  }

  override def computeForces(quadTree: QuadTree.Quad): Unit = {
    computeLayers(quadTree)
    computeSprings(quadTree)
    computeRepulsion(quadTree)
    computeGravityToCenter(quadTree)
  }


}
