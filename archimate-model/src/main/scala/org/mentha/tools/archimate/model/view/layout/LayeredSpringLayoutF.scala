package org.mentha.tools.archimate.model.view.layout

import java.util.Objects

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._
import org.mentha.tools.archimate.model.edges.impl._
import org.mentha.tools.archimate.model.view._

import scala.annotation.tailrec

class LayeredSpringLayoutF(view: View) extends SpringLayoutF(view) {

  override private[layout] val SIZE_BOUND = 0.25d

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

  private val LAYER_COEFFICIENT = 1.2d * SPRING_COEFFICIENT
  private val DIRECTION_COEFFICIENT = 0.8d * SPRING_COEFFICIENT


//  override def repulsion(d: Double): Double = {
//    val x = sqr(d)
//    -REPULSION_COEFFICIENT / (sqr(x) * d)
//  }

  private def withMisalignedLayers(action: (NodeWrapper, Double) => Unit): Unit = {
    def compute(tail: List[Seq[NodeWrapper]]): Unit = {
      val border_Y = tail.head.map { _.bounds.max_Y }.max + 1.2 * SPRING_LENGTH
      for { t <- tail.tail; n <- t } {
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

  private def computeLayers(center: Vector): Unit = {
    withMisalignedLayers { (n, displacement) =>
      n.force += Vector(x = 0, y = -LAYER_COEFFICIENT * springCoeff(displacement))
    }
  }

  private def computeDirections(center: Vector, temperature: Double): Unit = {

    val border = MIN_DISTANCE * (1.0d + temperature)
    for {edge <- edgesSeq } {
      edge.edge match {
        case vr: ViewRelationship[_] => vr.concept match {
          case _: DynamicRelationship => {
            val d = edge.target.mass.center - edge.source.mass.center
            val displacement = d.x - border
            if (displacement < 0) {
              val coeff = DIRECTION_COEFFICIENT * springCoeff(displacement) * temperature
              val force = Vector(coeff, 0)
              edge.source.force += force
              edge.target.force -= force
            }
          }
          case a: AccessRelationship => {
            val c = { if (a.access.write) 1 else 0 } - { if (a.access.read) 1 else 0 }
            if (c != 0) {
              val d = (edge.target.mass.center - edge.source.mass.center) * c
              val displacement = d.x - border
              if (displacement < 0) {
                val coeff = DIRECTION_COEFFICIENT * springCoeff(displacement) * temperature * c
                val force = Vector(coeff, 0)
                edge.source.force += force
                edge.target.force -= force
              }
            }
          }
          case _ =>
        }
        case _ =>
      }
    }

  }

  override def computeSprings(center: Vector, temperature: Double): Unit = for { edge <- edgesSeq } {

    val sameLayer = {
      val lt = layerObject.get(edge.target)
      val ls = layerObject.get(edge.source)
      Objects.equals(lt, ls)
    }

    val d = reduce(
      vector = edge.target.mass.center - edge.source.mass.center,
      x = SIZE_BOUND * (edge.source.bounds.width + edge.target.bounds.width),
      y = SIZE_BOUND * (edge.source.bounds.height + edge.target.bounds.height)
    )

    val l = math.sqrt(l2(d))
    val displacement = {
      if (sameLayer) {
        l - SPRING_LENGTH
      } else {
        (0.45d * math.abs(d.x) + 0.55d * math.abs(d.y)) - SPRING_LENGTH
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
    // val coeff = math.min(0.25d, temperature)
    // withMisalignedLayers { (n, displacement) => n.move(Vector(0, -coeff*displacement)) }
    super.step(temperature)
  }

  override def computeForces(center: Vector, temperature: Double): Unit = {
    computeLayers(center)
    computeSprings(center, temperature)
    computeRepulsion(center, temperature)
    computeDirections(center, temperature)
    computeGravityToCenter(center, temperature)
  }

}
