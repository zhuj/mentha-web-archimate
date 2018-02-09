package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.view._

abstract class ForceBasedLayout(view: View) {

  private[layout] val rnd: java.util.Random = new java.util.Random(0)

  private[layout] val NORMAL_SIZE = View.defaultSize.mean
  private[layout] val SIZE_NORMALIZER = 1.0d / NORMAL_SIZE

  private[layout] val MIN_DISTANCE = 0.10d
  private[layout] val MIN_DISTANCE_2 = sqr(MIN_DISTANCE)

  private[layout] val MAX_DISTANCE = 10.0d
  private[layout] val MAX_DISTANCE_2 = sqr(MAX_DISTANCE)

  private[layout] val SIZE_BOUND = 0.0d

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

  private[layout] val nodesPairs = {
    val z = nodesSeq.zipWithIndex
    z.flatMap {
      case (n1, i1) => z.collect {
        case (n2, i2) if i1 < i2 => (n1, n2)
      }
    }
  }.toVector


  private[layout] class EdgeWrapper(val edge: ViewEdge) extends Edge[NodeWrapper] {
    // TODO: do smth with associations to relationships
    edge withPoints { Nil } // clear all intermediate points
    override val source: NodeWrapper = nodesMap(edge.source.id)
    override val target: NodeWrapper = nodesMap(edge.target.id)
  }

  private[layout] val edgesMap = view.edges.map { e => e.id -> new EdgeWrapper(e)}.toMap
  private[layout] val edgesSeq = edgesMap.values.toVector

  private[layout] val CENTER_GRAVITY = 1.0e-4d
  private[layout] val DRAG = 0.10d
  private[layout] val TIMESTEP = 1.00d

  private[layout] val ENERGY_CUTOFF = 1.0e-3d

  private[layout] val MAX_VELOCITY = 1.0d
  private[layout] val MAX_VELOCITY_2 = sqr(MAX_VELOCITY)

  def repulsion(d: Double): Double
  def computeForces(center: Vector, temperature: Double): Unit

  private[layout] def computeRepulsion(center: Vector, temperature: Double): Unit = {
    for { (n1, n2) <- nodesPairs } {

      val displacement = reduce(
        vector = n1.mass.center - n2.mass.center,
        x = SIZE_BOUND * (n1.bounds.width + n2.bounds.width),
        y = SIZE_BOUND * (n1.bounds.height + n2.bounds.height)
      )

      val distance2 = l2(displacement)

      val f = if (distance2 < MIN_DISTANCE_2) {
        // just repulse it with random force
        Vector.random(rnd) * (n1.mass.value * n2.mass.value * repulsion(MIN_DISTANCE) /* / 1.0d */)
      } else if (distance2 < MAX_DISTANCE_2) {
        // calculate the real force
        val distance = Math.sqrt(distance2)
        displacement * (n1.mass.value * n2.mass.value * repulsion(distance) / distance)
      } else {
        // object are too far, there is no repulsion, ignore
        Vector.ZERO
      }

      n2.force += f
      n1.force -= f

    }
  }

  private[layout] def computeGravityToCenter(center: Vector, temperature: Double): Unit = {
    nodesSeq.foreach { node =>
      val d2 = l2(node.mass.center - center) / nodesSize
      val normalized = node.mass.center * (d2 * CENTER_GRAVITY) * (1.0e-3d + temperature)
      node.force -= normalized
    }
  }

  private[layout] def step(temperature: Double): Unit = {

    val center = {
      var x = 0d
      var y = 0d
      if (nodesSize > 0d) {
        for {n <- nodesSeq} {
          x += n.mass.center.x
          y += n.mass.center.y
        }
        x /= nodesSize
        y /= nodesSize
      }
      Vector(x, y)
    }

    computeForces(center, temperature)

    nodesSeq.foreach { node =>

      if (temperature > 1.0e-4) {
        node.force += Vector.random(rnd, rnd.nextGaussian() * temperature)
      }

      val acceleration = node.force * (1.0d / node.mass.value)
      node.force = Vector.ZERO // clean force for next iteration

      node.velocity = node.velocity * (1.0d - DRAG) + acceleration * TIMESTEP
      val v2 = l2(node.velocity) / temperature
      if (v2 > MAX_VELOCITY_2) {
        node.velocity = node.velocity * (MAX_VELOCITY / Math.sqrt(v2))
      }

      node.move(node.velocity * TIMESTEP - center * 0.75d)
    }
  }

  private[layout] def totalEnergy = nodesSeq.map(node => {
    0.5 * node.mass.value * l2(node.velocity)
  }).sum

  def layout(maxIterations: Int): Unit = {
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
