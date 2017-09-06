package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model.view._
import org.mentha.utils.archimate.model._

abstract class ForceBasedLayout(view: View) {

  @inline private[layout] final def sqr(d: Double): Double = d*d
  @inline private[layout] final def l2(v: Vector): Double = sqr(v.x) + sqr(v.y)

  private[layout] val NORMAL_SIZE = View.defaultSize.mean
  private[layout] val SIZE_NORMALIZER = 1.0d / NORMAL_SIZE

  private[layout] val MIN_DISTANCE = 1e-1d
  private[layout] val MIN_DISTANCE_2 = sqr(MIN_DISTANCE)

  private[layout] val MAX_DISTANCE = 1e+1d
  private[layout] val MAX_DISTANCE_2 = sqr(MAX_DISTANCE)

  private[layout] class NodeWrapper(val node: ViewNode) extends Vertex with Body {

    var _mass: Mass = _
    @inline override def mass: Mass = _mass

    var _bounds: Bounds = _
    @inline override def bounds: Bounds = _bounds

    var force: Vector = Vector.ZERO
    var velocity: Vector = Vector.ZERO

    @inline def place(position: Vector): Unit = {
      _mass = Mass(
        center = position,
        value = 1.0
      )
      _bounds = Bounds(
        position,
        node.size.width * SIZE_NORMALIZER,
        node.size.height * SIZE_NORMALIZER
      )
    }

    @inline def move(displacement: Vector): Unit = place(mass.center + displacement)
    place(node.position * SIZE_NORMALIZER)

  }

  private[layout] val nodesMap = view.nodes.map { n => n.id -> new NodeWrapper(n) }.toMap
  private[layout] val nodesSeq = nodesMap.values.toVector
  private[layout] val nodesSeqPar = nodesSeq.par

  private[layout] class EdgeWrapper(val edge: ViewEdge) extends Edge[NodeWrapper] {
    override val source: NodeWrapper = nodesMap(edge.source.id)
    override val target: NodeWrapper = nodesMap(edge.target.id)
  }

  private[layout] val edgesMap = view.edges.map { e => e.id -> new EdgeWrapper(e)}.toMap
  private[layout] val edgesSeq = edgesMap.values.toVector
  private[layout] val edgesSeqPar = edgesMap.values.toVector.par

  class BarnesHut(
    val force: Double => Double,
    val theta: Double = 0.75d,
    val reducer: Double = 0.0d
  ) {
    require(theta > 0.0d)

    def calculateForce(body: Body, quad: QuadTree.Quad): Vector = {
      val displacement = quad.mass.center - body.mass.center
      val distance2 = Math.max(0d, l2(displacement) - reducer)

      quad match {
        case single: QuadTree.Single if body eq single.body => {
          // ignore the same object
          Vector.ZERO
        }
        case fork: QuadTree.Fork if distance2 < sqr(quad.bounds.mean / theta) => {
          // go into the quad if it is so close to the object that it's impossible to replace it as a single body
          fork.children.map { c => calculateForce(body, c) }.fold(Vector.ZERO) { _+_ }
        }
        case _ => {
          if (distance2 < MIN_DISTANCE_2) {
            // just repulse it with random force
            Vector.random * (body.mass.value * quad.mass.value * force(MIN_DISTANCE_2) / 1.0)
          } else if (distance2 < MAX_DISTANCE_2) {
            // calculate the real force
            val distance = math.sqrt(distance2) // TODO: use node bounds
            displacement * (body.mass.value * quad.mass.value * force(distance) / distance)
          } else {
            // object are too far, ignore
            Vector.ZERO
          }
        }
      }
    }

  }


  private[layout] val CENTER_GRAVITY = 1.0e-8d
  private[layout] val DRAG = -1.0e-1d
  private[layout] val TIMESTEP = 1.0d

  private[layout] val ENERGY_CUTOFF = 1.0e-3d

  private[layout] val MAX_VELOCITY = 1.0d
  private[layout] val MAX_VELOCITY_2 = sqr(MAX_VELOCITY)

  def barnesHutCore: BarnesHut
  def computeForces(quadTree: QuadTree.Quad): Unit

  private[layout] def computeRepulsion(quadTree: QuadTree.Quad) = {
    nodesSeqPar.foreach { node =>
      node.force += barnesHutCore.calculateForce(node, quadTree)
    }
  }

  private[layout] def computeGravityToCenter(quadTree: QuadTree.Quad) = {
    nodesSeqPar.foreach { node =>
      val d2 = l2(node.mass.center - quadTree.mass.center)
      val normalized = node.mass.center * (math.sqrt(d2) * CENTER_GRAVITY)
      node.force -= normalized
    }
  }

  private[layout] def step() = {

    val quadTree = QuadTree(nodesSeq)
    val center = quadTree.mass.center

    computeForces(quadTree)

    nodesSeqPar.foreach { node =>

      node.force += node.velocity * DRAG
      val acceleration = node.force * (1.0d / node.mass.value)

      node.force = Vector.ZERO

      node.velocity += acceleration * TIMESTEP
      val v2 = l2(node.velocity)
      if (v2 > MAX_VELOCITY_2) {
        node.velocity = node.velocity * (MAX_VELOCITY / math.sqrt(v2))
      }

      node.move(node.velocity * TIMESTEP - center * 0.5)
    }
  }

  private[layout] def totalEnergy = nodesSeq.map(node => {
    0.5 * node.mass.value * l2(node.velocity)
  }).sum

  def layout(maxIterations: Int = 1050): Unit = {
    var it = 0
    do {
      var i = 0; do { step(); i +=1 } while (i < 10)
      it += 1
    } while (/*totalEnergy > ENERGY_CUTOFF &&*/ it < maxIterations)
    for { node <- nodesSeq } {
      node.node.withPosition(node.mass.center * NORMAL_SIZE)
    }
  }

}
