package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

abstract class ForceBasedLayout(view: View) {

  private[layout] val rnd: java.util.Random = new java.util.Random(0)

  private[layout] val NORMAL_SIZE = View.defaultSize.mean
  private[layout] val SIZE_NORMALIZER = 1.0d / NORMAL_SIZE

  private[layout] val MIN_DISTANCE = 1e-1d
  private[layout] val MIN_DISTANCE_2 = sqr(MIN_DISTANCE)

  private[layout] val MAX_DISTANCE = 1e+1d
  private[layout] val MAX_DISTANCE_2 = sqr(MAX_DISTANCE)

  @inline private[layout] final def sqr(d: Double): Double = d*d
  @inline private[layout] final def l2(v: Vector): Double = sqr(v.x) + sqr(v.y)
  @inline private[layout] final def reduce(vector: Vector, x: Double, y: Double, threshold: Double=MIN_DISTANCE): Vector = {
    val sx = Math.signum(vector.x)
    val sy = Math.signum(vector.y)
    val vx = Math.abs(vector.x)
    val vy = Math.abs(vector.y)
    Vector(
      x = sx * Math.max(threshold * vx, vx - Math.abs(x)),
      y = sy * Math.max(threshold * vy, vy - Math.abs(y)),
    )
  }

  private[layout] class NodeWrapper(val node: ViewNode) extends Vertex with Body {

    private val width = node.size.width * SIZE_NORMALIZER
    private val height = node.size.height * SIZE_NORMALIZER
    private val massValue = Math.pow(width * height, 0.4)

    var _mass: Mass = _
    @inline override def mass: Mass = _mass

    var _bounds: Bounds = _
    @inline override def bounds: Bounds = _bounds

    var force: Vector = Vector.ZERO
    var velocity: Vector = Vector.ZERO

    @inline def place(position: Vector): Unit = {
      _mass = Mass(
        center = position,
        value = massValue
      )
      _bounds = Bounds(
        position = position,
        width = width,
        height = height
      )
    }

    @inline def move(displacement: Vector): Unit = place(mass.center + displacement)
    place(node.position * SIZE_NORMALIZER)

  }


  private[layout] val nodesMap = view.nodes.map { n => n.id -> new NodeWrapper(n) }.toMap
  private[layout] val nodesSeq = nodesMap.values.toVector
  private[layout] val nodesSize = 1.0d * nodesSeq.size
  private[layout] val nodesSeqPar = if (nodesSize > 100.0d) { nodesSeq.par } else { nodesSeq }

  private[layout] class EdgeWrapper(val edge: ViewEdge) extends Edge[NodeWrapper] {
    // TODO: do smth with associations to relationships
    edge withPoints(Nil) // clear all intermediate points
    override val source: NodeWrapper = nodesMap(edge.source.id)
    override val target: NodeWrapper = nodesMap(edge.target.id)
  }

  private[layout] val edgesMap = view.edges.map { e => e.id -> new EdgeWrapper(e)}.toMap
  private[layout] val edgesSeq = edgesMap.values.toVector
  private[layout] val edgesSeqPar = if (nodesSize > 100.0d) { edgesSeq.par } else { edgesSeq }

  class BarnesHut(
    val force: Double => Double,
    val theta: Double = 0.75d,
    val reducerBounds: Double = 0.0d,
    val reducerLength: Double = 0.0d
  ) {
    require(theta > 0.0d)
    require(reducerBounds >= 0.0d)
    require(reducerLength >= 0.0d)

    def calculateForce(body: Body, quad: QuadTree.Quad): Vector = {
      val displacement = reduce(
        vector = quad.mass.center - body.mass.center,
        x = reducerBounds * body.bounds.width,
        y = reducerBounds * body.bounds.height
      )

      val distance2 = Math.max(0d, l2(displacement) - reducerLength)
      quad match {
        case single: QuadTree.Single if body eq single.body => {
          // ignore the same object
          Vector.ZERO
        }
        case fork: QuadTree.Fork if (distance2 < MAX_DISTANCE_2) && (distance2 < sqr(quad.bounds.mean / theta)) => {
          // go into the quad if it is so close to the object that it's impossible to replace it as a single body
          fork.children.map { c => calculateForce(body, c) }.fold(Vector.ZERO) { _+_ }
        }
        case _ => {
          if (distance2 < MIN_DISTANCE_2) {
            // just repulse it with random force
            Vector.random(rnd) * (body.mass.value * quad.mass.value * force(MIN_DISTANCE) / 1.0)
          } else if (distance2 < MAX_DISTANCE_2) {
            // calculate the real force
            val distance = Math.sqrt(distance2)
            displacement * (body.mass.value * quad.mass.value * force(distance) / distance)
          } else {
            // object are too far, ignore
            Vector.ZERO
          }
        }
      }
    }

  }


  private[layout] val CENTER_GRAVITY = 1.0e-5d
  private[layout] val DRAG = 0.1d
  private[layout] val TIMESTEP = 1.0d

  private[layout] val ENERGY_CUTOFF = 1.0e-3d

  private[layout] val MAX_VELOCITY = 1.0d
  private[layout] val MAX_VELOCITY_2 = sqr(MAX_VELOCITY)

  def barnesHutCore: BarnesHut
  def computeForces(quadTree: QuadTree.Quad, temperature: Double): Unit

  private[layout] def computeRepulsion(quadTree: QuadTree.Quad, temperature: Double) = {
    nodesSeqPar.foreach { node =>
      node.force += barnesHutCore.calculateForce(node, quadTree) * (1.0d + temperature)
    }
  }

  private[layout] def computeGravityToCenter(quadTree: QuadTree.Quad, temperature: Double) = {
    nodesSeqPar.foreach { node =>
      val d2 = l2(node.mass.center /* - quadTree.mass.center */ ) / nodesSize
      val normalized = node.mass.center * (d2 * CENTER_GRAVITY) * (1e-3d + temperature)
      node.force -= normalized
    }
  }

  private[layout] def step(temperature: Double) = {

    val quadTree = QuadTree(nodesSeq)
    val center = quadTree.mass.center

    computeForces(quadTree, temperature)

    nodesSeqPar.foreach { node =>

      if (temperature > 1.0e-4) {
        node.force += Vector.random(rnd) * rnd.nextGaussian() * temperature
      }

      val acceleration = node.force * (1.0d / node.mass.value)
      node.force = Vector.ZERO

      node.velocity = node.velocity * (1.0d - DRAG) + acceleration * TIMESTEP
      val v2 = l2(node.velocity) / temperature
      if (v2 > MAX_VELOCITY_2) {
        node.velocity = node.velocity * (MAX_VELOCITY / Math.sqrt(v2))
      }
      node.move(node.velocity * TIMESTEP - center * 0.5)
    }
  }

  private[layout] def totalEnergy = nodesSeq.map(node => {
    0.5 * node.mass.value * l2(node.velocity)
  }).sum

  def layout(maxIterations: Int = 1000): Unit = {
    var it = 0
    do {
      // DEBUG: println(it)
      val temperature = Math.exp(3.0 - 10.0 * it / maxIterations)
      (0 until 20) foreach { _ => step(temperature) }
      it += 1
    } while (/*totalEnergy > ENERGY_CUTOFF &&*/ it < maxIterations)
    for { node <- nodesSeq } {
      node.node.withPosition(node.mass.center * NORMAL_SIZE)
    }
  }

}
