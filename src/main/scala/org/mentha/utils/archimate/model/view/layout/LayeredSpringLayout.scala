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

  private val SPRING_LENGTH = 0.85d

  private val LAYER_COEFFICIENT = 3.25e-1d
  private val SPRING_COEFFICIENT = 2.65e-1d
  private val REPULSION_COEFFICIENT = 1.5e-2d

  override val barnesHutCore = new BarnesHut(
    d => -REPULSION_COEFFICIENT / sqr(0.5 * d),
    reducerLength = 0.01,
    reducerBounds = 0.40
  )

  private def withLayers(action: (NodeWrapper, Double) => Unit) = {
    def compute(list: List[(LayerObject, Seq[NodeWrapper])]): Unit = {
      val border_Y = list.head._2.map { _.bounds.max_Y }.max + 2 * SPRING_LENGTH
      for { t <- list.tail; n <- t._2 } {
        val displacement = n.bounds.min_Y - border_Y
        if (displacement < 0) { action(n, displacement) }
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

  private def computeLayers(quadTree: QuadTree.Quad) = {
    withLayers { (n, displacement) =>
      n.force += Vector(x = 0, y = -LAYER_COEFFICIENT * springCoeff(displacement))
    }
  }

  private def springCoeff(displacement: Double) = {
    if (displacement < 0.0) {
      sqr(displacement - 0.1) * displacement
    } else {
      displacement
    }
  }

  private def computeSprings(quadTree: QuadTree.Quad) = for { edge <- edgesSeq } {

    val lt = layerObject(edge.target).orNull
    val ls = layerObject(edge.source).orNull

    if (null != lt && null != ls && lt != ls) {

      val d = edge.target.mass.center - edge.source.mass.center
      val l = 0.45 * Math.abs(d.x) + 0.55 * Math.abs(d.y)
      val displacement = l - SPRING_LENGTH
      if (Math.abs(displacement) > MIN_DISTANCE) {
        val coeff = SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
        val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random * coeff }
        edge.source.force += force
        edge.target.force -= force
      }

    } else {

      val d = reduce(
        vector = edge.target.mass.center - edge.source.mass.center,
        x = 0.1 * (edge.source.bounds.width + edge.target.bounds.width),
        y = 0.1 * (edge.source.bounds.height + edge.target.bounds.height)
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


  override private[layout] def step(): Unit = {
    withLayers { (n, displacement) => n.move(Vector(0, -0.1 * displacement)) }
    super.step()
  }

  override def computeForces(quadTree: QuadTree.Quad): Unit = {
    computeLayers(quadTree)
    computeSprings(quadTree)
    computeRepulsion(quadTree)
    computeGravityToCenter(quadTree)
  }


}
