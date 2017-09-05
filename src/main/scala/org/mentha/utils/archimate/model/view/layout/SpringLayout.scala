package org.mentha.utils.archimate.model.view.layout

import org.mentha.utils.archimate.model.{Edge, Vertex}
import org.mentha.utils.archimate.model.view._

import scala.util.Random

class SpringLayout(view: View) {

  @inline private def sqr(d: Double): Double = d*d
  @inline private def l2(p: Point): Double = sqr(p.x) + sqr(p.y)

  private val SIZE_NORMALIZER = 10.0d

  private val MIN_DISTANCE = 1e-2d
  private val MIN_DISTANCE_2 = sqr(MIN_DISTANCE)


  private class NodeWrapper(val node: ViewNode) extends Vertex with QuadTree.Body {

    private val w2 = 0.5 * node.size.width / SIZE_NORMALIZER
    private val h2 = 0.5 * node.size.height / SIZE_NORMALIZER

    var _mass: QuadTree.Mass = _
    @inline override def mass: QuadTree.Mass = _mass

    var _bounds: QuadTree.Bounds = _
    @inline override def bounds: QuadTree.Bounds = _bounds

    var force: Point = Planar.ZERO_POINT
    var velocity: Point = Planar.ZERO_POINT

    @inline def place(position: Point): Unit = {
      _mass = QuadTree.Mass(
        center = position,
        count =  1,
        value = 1.0
      )
      _bounds = QuadTree.Bounds(
        min_X = position.x - w2, max_X = position.x + w2,
        min_Y = position.y - h2, max_Y = position.y + h2
      )
    }

    @inline def move(displacement: Point): Unit = { place(mass.center + displacement) }
    place(node.position / SIZE_NORMALIZER)

  }

  private val nodesMap = view.nodes.map { n => n.id -> new NodeWrapper(n) }.toMap
  private val nodesSeq = nodesMap.values.toSeq

  private class EdgeWrapper(val edge: ViewEdge) extends Edge[NodeWrapper] {
    override val source: NodeWrapper = nodesMap(edge.source.id)
    override val target: NodeWrapper = nodesMap(edge.target.id)
  }

  private val edgesMap = view.edges.map { e => e.id -> new EdgeWrapper(e)}.toMap
  private val edgesSeq = edgesMap.values.toSeq

  private val ENERGY_CUTOFF = 1e-3d
  private val REPULSION = -1.2d
  private val CENTER_GRAVITY = 1e-5d
  private val SPRING_LENGTH = 1.0d
  private val SPRING_COEFFICIENT = 1.8e-3d
  private val DRAG = -0.02d
  private val TIMESTEP = 1.0d
  private val MAX_VELOCITY = 5.0d
  private val THETA = 1.0d
  private val THETA2 = sqr(THETA)


  private def step() = {


    val quadTree = QuadTree(nodesSeq)

    def computeHookesLaw = for { edge <- edgesSeq } {
      val d = edge.target.mass.center - edge.source.mass.center
      val l = math.sqrt(l2(d))

      val displacement = l - (SPRING_LENGTH + 0.5 * (edge.source.bounds.mean + edge.target.bounds.mean))
      if (Math.abs(displacement) > MIN_DISTANCE) {
        val coeff = SPRING_COEFFICIENT * displacement * 0.5
        val force = if (l > MIN_DISTANCE) {
          d * (coeff / l)
        } else {
          Point(x = Random.nextGaussian(), y = Random.nextGaussian()) * coeff
        }
        edge.source.force += force
        edge.target.force -= force
      }

    }

    def computeBarnesHut = {

      def calculateDirectForce(node: NodeWrapper, quad: QuadTree.Quad): Point = {
        val d = quad.mass.center - node.mass.center
        val d2 = l2(d)
        if (d2 > MIN_DISTANCE_2) {
          val d2inv = 1.0 / d2
          val direction = d * math.sqrt(d2inv)
          direction * REPULSION * quad.mass.count * d2inv * 2.0
        } else {
          val direction = Point(x = Random.nextGaussian(), y = Random.nextGaussian())
          direction * REPULSION * quad.mass.count * 1.0 * 2.0
        }
      }

      def apply(node: NodeWrapper, quad: QuadTree.Quad): Unit = {

        val d2 = l2(quad.bounds.mid - node.mass.center)
        val t2 = sqr(quad.bounds.mean) * THETA2

        if (d2 < t2) {
          // Nearby quad
          quad match {
            case fork: QuadTree.Fork => {
              apply(node, fork.nw)
              apply(node, fork.ne)
              apply(node, fork.sw)
              apply(node, fork.se)
            }
            case single: QuadTree.Single if single.body == node => {}
            case _ => {
              node.force += calculateDirectForce(node, quad)
            }
          }
        } else {
          // Far-away quad
          node.force += calculateDirectForce(node, quad)
        }
      }

      for { node <- nodesSeq } { apply(node, quadTree) }
    }

    // drag
    def computeDrag = for { node <- nodesSeq } {
      node.force += node.velocity * DRAG
    }

    // gravity to center
    def computeGravity = for { node <- nodesSeq } {
      val d2 = l2(node.mass.center) * CENTER_GRAVITY
      val normalized = node.mass.center * math.pow(d2, 3)
      node.force -= normalized
    }

    computeHookesLaw
    computeBarnesHut
    computeDrag
    computeGravity

    for { node <- nodesSeq } {

      val acceleration = node.force / node.mass.value
      node.force = Planar.ZERO_POINT

      node.velocity += acceleration * TIMESTEP
      val v = math.sqrt(l2(node.velocity))
      if (v > MAX_VELOCITY) {
        node.velocity = node.velocity / v * MAX_VELOCITY
      }

      node.move(node.velocity * TIMESTEP)
    }
  }

  def totalEnergy = nodesSeq.map(node => {
    0.5 * node.mass.value * l2(node.velocity)
  }).sum

  def layout(maxIterations: Int = 1050): Unit = {
    var it = 0
    do {
      var i = 0; do { step(); i +=1 } while (i < 10)
      it += 1
    } while (/*totalEnergy > ENERGY_CUTOFF &&*/ it < maxIterations)
    for { node <- nodesSeq } {
      node.node.withPosition(node.mass.center * SIZE_NORMALIZER)
    }
  }

}
