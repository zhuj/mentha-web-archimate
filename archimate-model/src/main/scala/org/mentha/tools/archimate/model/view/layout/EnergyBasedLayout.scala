//package org.mentha.tools.archimate.model.view.layout
//
//import org.mentha.tools.archimate.model.view._
//import org.mentha.tools.archimate.model._
//
//import scala.annotation.tailrec
//
//abstract class EnergyBasedLayout(view: View) {
//
//  private[layout] val rnd: java.util.Random = new java.util.Random(0)
//
//  @inline private[layout] final def sqr(d: Double): Double = d*d
//  @inline private[layout] final def l2(v: Vector): Double = sqr(v.x) + sqr(v.y)
//  @inline private[layout] final def reduce(vector: Vector, x: Double, y: Double): Vector = {
//    val sx = Math.signum(vector.x)
//    val sy = Math.signum(vector.y)
//    Vector(
//      x = sx * Math.max(0, Math.abs(vector.x) - Math.abs(x)),
//      y = sy * Math.max(0, Math.abs(vector.y) - Math.abs(y)),
//    )
//  }
//
//  private[layout] val NORMAL_SIZE = View.defaultSize.mean
//  private[layout] val SIZE_NORMALIZER = 1.0d / NORMAL_SIZE
//
//  private[layout] val MIN_DISTANCE = 1e-1d
//  private[layout] val MIN_DISTANCE_2 = sqr(MIN_DISTANCE)
//
//  private[layout] val MAX_DISTANCE = 1e+1d
//  private[layout] val MAX_DISTANCE_2 = sqr(MAX_DISTANCE)
//
//  private[layout] class NodeWrapper(val node: ViewNode) extends Vertex with Body {
//
//    private val width = node.size.width * SIZE_NORMALIZER
//    private val height = node.size.height * SIZE_NORMALIZER
//    private val massValue = Math.pow(width * height, 0.4)
//
//    private[layout] var position: Vector = set(node.position * SIZE_NORMALIZER)
//
//    def commit(): Unit = {
//      position = _mass.center
//    }
//
//    def randomize(length: Double): Unit = {
//      set(p = position + Vector.random(rnd, length * rnd.nextGaussian()))
//    }
//
//    @inline def set(p: Vector): Vector = {
//      _mass = Mass(
//        center = p,
//        value = massValue
//      )
//      _bounds = Bounds(
//        position = p,
//        width = width,
//        height = height
//      )
//      p
//    }
//
//    var _mass: Mass = _
//    @inline override def mass: Mass = _mass
//
//    var _bounds: Bounds = _
//    @inline override def bounds: Bounds = _bounds
//
//  }
//
//  private[layout] val nodesMap = view.nodes.map { n => n.id -> new NodeWrapper(n) }.toMap
//  private[layout] val nodesSeq = nodesMap.values.toVector
//  private[layout] val nodesSeqPar = nodesSeq//.par
//
//  private[layout] class EdgeWrapper(val edge: ViewEdge) extends Edge[NodeWrapper] {
//    // TODO: do smth with associations to relationships
//    override val source: NodeWrapper = nodesMap(edge.source.id)
//    override val target: NodeWrapper = nodesMap(edge.target.id)
//  }
//
//  private[layout] val edgesMap = view.edges.map { e => e.id -> new EdgeWrapper(e)}.toMap
//  private[layout] val edgesSeq = edgesMap.values.toVector
//  private[layout] val edgesSeqPar = edgesSeq//.par
//
//  class BarnesHut(
//    val energy: Double => Double,
//    val theta: Double = 0.75d,
//    val reducerBounds: Double = 0.0d,
//    val reducerLength: Double = 0.0d
//  ) {
//    require(theta > 0.0d)
//    require(reducerBounds >= 0.0d)
//    require(reducerLength >= 0.0d)
//
//    def calculateEnergy(body: Body, quad: QuadTree.Quad): Double = {
//      def core(quad: QuadTree.Quad): Double = {
//        val displacement = reduce(
//          vector = quad.mass.center - body.mass.center,
//          x = reducerBounds * body.bounds.width,
//          y = reducerBounds * body.bounds.height
//        )
//
//        val distance2 = Math.max(0d, l2(displacement) - reducerLength)
//        quad match {
//          case single: QuadTree.Single if body eq single.body => {
//            0.0d // ignore the same object
//          }
//          case fork: QuadTree.Fork if distance2 < MAX_DISTANCE_2 && distance2 < sqr(quad.bounds.mean / theta) => {
//            fork.children.map { c => core(c) }.sum // go into the quad if it is so close to the object that it's impossible to replace it as a single body
//          }
//          case _ => {
//            if (distance2 < MIN_DISTANCE_2) {
//              quad.mass.value * energy(MIN_DISTANCE) // just repulse it with random force
//            } else if (distance2 < MAX_DISTANCE_2) {
//              quad.mass.value * energy(Math.sqrt(distance2)) // calculate the real force
//            } else {
//              0.0d
//            }
//          }
//        }
//      }
//
//      body.mass.value * core(quad)
//    }
//
//  }
//
//  private[layout] val CENTER_GRAVITY = 1.0e-1d
//  private[layout] val ENERGY_CUTOFF = 1.0e-3d
//
//  def barnesHutCore: BarnesHut
//  def computeEnergy(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double): Double
//
//  private[layout] def computeRepulsion(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double) = {
//    barnesHutCore.calculateEnergy(node, quadTree)
//  }
//
//  private[layout] def computeGravityToCenter(quadTree: QuadTree.Quad, node: NodeWrapper): Double = {
//    l2(node.mass.center) * CENTER_GRAVITY
//  }
//
//  var lastQuad: QuadTree.Quad = QuadTree(nodesSeq)
//  private[layout] def step(temperature: Double): Unit = {
//    @tailrec def core(): Unit = {
//      val node = nodesSeq.apply(rnd.nextInt(nodesSeq.size))
//      val lastEnergy = computeEnergy(lastQuad, node, temperature)
//
//      node.randomize(0.001 + temperature)
//      val nextEnergy = computeEnergy(lastQuad, node, temperature)
//
//      val delta = nextEnergy - lastEnergy
//      val good = (delta <= 0) || (rnd.nextDouble() < Math.exp(-delta / temperature))
//      if (!good) {
//        core()
//      } else {
//        node.commit()
//        lastQuad = QuadTree(nodesSeq)
//      }
//    }
//    core()
//  }
//
//  def layout(maxIterations: Int = 5000): Unit = {
//    var it = 0
//    do {
//      val temperature = Math.exp(3.0 - 12.0 * it / maxIterations)
//      step(temperature)
//      it += 1
//    } while (it < maxIterations)
//    for { node <- nodesSeq } {
//      node.node.withPosition(node.position * NORMAL_SIZE)
//    }
//  }
//
//}
