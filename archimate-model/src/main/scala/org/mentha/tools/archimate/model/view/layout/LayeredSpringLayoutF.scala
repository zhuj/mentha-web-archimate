package org.mentha.tools.archimate.model.view.layout

import java.util.Objects

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._
import org.mentha.tools.archimate.model.view._

import scala.annotation.tailrec

class LayeredSpringLayoutF(view: View) extends SpringLayoutF(view) {

  private val layers = {
    @inline def element(n: NodeWrapper): Option[Element] = Option(n.node)
      .collect { case n: ViewNodeConcept[_] => n.concept }
      .collect { case e: Element => e }

    Seq(
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == MotivationLayer),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == StrategyLayer),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == BusinessLayer && e.isInstanceOf[ActiveStructureAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == BusinessLayer && e.isInstanceOf[BehaviorAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == BusinessLayer && e.isInstanceOf[PassiveStructureAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == ApplicationLayer && e.isInstanceOf[ActiveStructureAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == ApplicationLayer && e.isInstanceOf[BehaviorAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == ApplicationLayer && e.isInstanceOf[PassiveStructureAspect]),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == PhysicalLayer),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == TechnologyLayer),
      (node: NodeWrapper) => element(node).exists(e => e.meta.layerObject == ImplementationLayer)
    )
  }

  private val layeredNodesList = layers
    .map { case (lo) => nodesSeq.filter { node => lo(node) } }
    .filter { _.nonEmpty }
    .toList

  private val layerObject = layeredNodesList
    .zipWithIndex
    .flatMap { case (e, idx) => e.map { x => (x, Integer.valueOf(idx)) } }
    .toMap

  private val LAYER_COEFFICIENT = 0.325d
  private val DIRECTION_COEFFICIENT = 0.900d

  override private[layout] val SIZE_BOUND = 0.25d

  override val barnesHutCore = new BarnesHut(
    d => {
      val x = 0.50d * d
      -REPULSION_COEFFICIENT / (sqr(x) * x)
    },
    reducerLength = 0.00,
    reducerBounds = SIZE_BOUND
  )


  private def withLayers(action: (NodeWrapper, Double) => Unit): Unit = {
    def compute(list: List[Seq[NodeWrapper]]): Unit = {
      val border_Y = list.head.map { _.bounds.max_Y }.max + 2.0 * SPRING_LENGTH
      for { t <- list.tail; n <- t } {
        val displacement = n.bounds.min_Y - border_Y
        if (displacement < 0) { action(n, displacement) }
      }
    }
    @tailrec def core(list: List[Seq[NodeWrapper]]): Unit = {
      if (list.nonEmpty) {
        compute(list)
        core(list.tail)
      }
    }
    core(layeredNodesList)
  }

  private def computeLayers(quadTree: QuadTree.Quad): Unit = {
    withLayers { (n, displacement) =>
      n.force += Vector(x = 0, y = -LAYER_COEFFICIENT * springCoeff(displacement))
    }
  }

  private def computeDirections(quadTree: QuadTree.Quad, temperature: Double): Unit = {

    val border = MIN_DISTANCE * (1.0d + temperature)
    for {edge <- edgesSeq } {
      edge.edge match {
        case vr: ViewRelationship[_] => vr.concept match {
          case _: DynamicRelationship => {
            val d = edge.target.mass.center - edge.source.mass.center
            if (d.x < border) {
              val displacement = d.x - border
              val coeff = DIRECTION_COEFFICIENT * springCoeff(displacement) * temperature
              val force = Vector(coeff, 0)
              edge.source.force += force
              edge.target.force -= force
            }
          }
          case _ =>
        }
        case _ =>
      }
    }

  }

  override def computeSprings(quadTree: QuadTree.Quad, temperature: Double): Unit = for { edge <- edgesSeq } {

    val d = reduce(
      vector = edge.target.mass.center - edge.source.mass.center,
      x = SIZE_BOUND * (edge.source.bounds.width + edge.target.bounds.width),
      y = SIZE_BOUND * (edge.source.bounds.height + edge.target.bounds.height)
    )

    val l = math.sqrt(l2(d))
    val displacement = {
      val lt = layerObject.get(edge.target)
      val ls = layerObject.get(edge.source)
      if (Objects.equals(lt, ls)) {
        l - SPRING_LENGTH
      } else {
        0.45d * math.abs(d.x) + 0.55d * math.abs(d.y) - SPRING_LENGTH
      }
    }

    if (math.abs(displacement) > MIN_DISTANCE) {
      val coeff = SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
      val force = if (l > MIN_DISTANCE) { d * (coeff / l) } else { Vector.random(rnd, coeff) }
      edge.source.force += force
      edge.target.force -= force
    }

  }

  override private[layout] def step(temperature: Double): Unit = {
    val coeff = math.min(1.0d, 1e-4d + temperature)
    withLayers { (n, displacement) => n.move(Vector(0, -coeff*displacement)) }
    super.step(temperature)
  }

  override def computeForces(quadTree: QuadTree.Quad, temperature: Double): Unit = {
    computeLayers(quadTree)
    computeSprings(quadTree, temperature)
    computeRepulsion(quadTree, temperature)
    computeDirections(quadTree, temperature)
    computeGravityToCenter(quadTree, temperature)
  }

}
