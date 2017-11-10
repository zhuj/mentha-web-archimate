//package org.mentha.tools.archimate.model.view.layout
//
//import org.mentha.tools.archimate.model._
//import org.mentha.tools.archimate.model.edges._
//import org.mentha.tools.archimate.model.view._
//
//import scala.annotation.tailrec
//
//class LayeredSpringLayoutE(view: View) extends EnergyBasedLayout(view) {
//
//  private val layers = Seq(
//    MotivationLayer,
//    StrategyLayer,
//    BusinessLayer,
//    ApplicationLayer,
//    PhysicalLayer,
//    TechnologyLayer,
//    ImplementationLayer
//  )
//
//  private val nodesEdges = nodesSeq.map {
//    node => node -> edgesSeq.filter {
//      edge => edge.source == node || edge.target == node
//    }
//  }.toMap
//
//
//
//  @inline private def layerObject(n: NodeWrapper): Option[LayerObject] = Option(n.node)
//    .collect { case n: ViewNodeConcept[_] => n.concept }
//    .collect { case e: Element => e.meta.layerObject }
//
//  private val layeredNodesList = layers
//    .map { case (lo) => (lo, nodesSeq.filter { node => layerObject(node).orNull == lo } ) }
//    .filter { _._2.nonEmpty }
//    .toList
//
//  private val SPRING_LENGTH = 0.85d
//
//  private val LAYER_COEFFICIENT = 0.325d
//  private val SPRING_COEFFICIENT = 0.265d
//  private val REPULSION_COEFFICIENT = 0.015d
//  private val DIRECTION_COEFFICIENT = 0.900d
//
//  private val SPRING_BOUND = 0.1d
//
//  override val barnesHutCore = new BarnesHut(
//    d => -REPULSION_COEFFICIENT / sqr(0.5 * d),
//    reducerLength = 0.01,
//    reducerBounds = 0.40
//  )
//
//  private def withLayers(action: (NodeWrapper, Double) => Unit) = {
//    def compute(list: List[(LayerObject, Seq[NodeWrapper])]): Unit = {
//      val border_Y = list.head._2.map { _.bounds.max_Y }.max + 2 * SPRING_LENGTH
//      for { t <- list.tail; n <- t._2 } {
//        val displacement = n.bounds.min_Y - border_Y
//        if (displacement < 0) { action(n, displacement) }
//      }
//    }
//    @tailrec def core(list: List[(LayerObject, Seq[NodeWrapper])]): Unit = {
//      if (list.nonEmpty) {
//        compute(list)
//        core(list.tail)
//      }
//    }
//    core(layeredNodesList)
//  }
//
//  private def computeLayers(quadTree: QuadTree.Quad, node: NodeWrapper): Double = {
//    var result = 0d
//    withLayers { (_, displacement) =>
//      result = result + LAYER_COEFFICIENT * springCoeff(displacement)
//    }
//    result
//  }
//
//  private def springCoeff(displacement: Double) = {
//    if (displacement < 0.0) {
//      sqr(displacement - 0.1) * displacement
//    } else {
//      displacement
//    }
//  }
//
//  private def computeDirections(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double): Double = {
//    val border = MIN_DISTANCE * (1.0d + temperature)
//    nodesEdges(node).map { edge =>
//      edge.edge match {
//        case vr: ViewRelationship[_] => vr.concept match {
//          case _: DynamicRelationship => {
//            val d = edge.target.mass.center - edge.source.mass.center
//            if (d.x < border) {
//              val displacement = d.x - border
//              DIRECTION_COEFFICIENT * springCoeff(displacement) * temperature
//            } else {
//              0d
//            }
//          }
//          case _ => 0d
//        }
//        case _ => 0d
//      }
//    }
//    .fold(0d) { _+_ }
//  }
//
//  private def computeSprings(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double): Double = {
//    nodesEdges(node).map { edge =>
//      val lt = layerObject(edge.target).orNull
//      val ls = layerObject(edge.source).orNull
//
//      if (null != lt && null != ls && lt != ls) {
//
//        val d = edge.target.mass.center - edge.source.mass.center
//        val l = 0.45 * Math.abs(d.x) + 0.55 * Math.abs(d.y)
//        val displacement = l - SPRING_LENGTH
//        if (Math.abs(displacement) > MIN_DISTANCE) {
//          SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
//        } else {
//          0d
//        }
//
//      } else {
//
//        val d = reduce(
//          vector = edge.target.mass.center - edge.source.mass.center,
//          x = SPRING_BOUND * (edge.source.bounds.width + edge.target.bounds.width),
//          y = SPRING_BOUND * (edge.source.bounds.height + edge.target.bounds.height)
//        )
//
//        val l = math.sqrt(l2(d))
//        val displacement = l - SPRING_LENGTH
//        if (Math.abs(displacement) > MIN_DISTANCE) {
//          SPRING_COEFFICIENT * 0.5 * springCoeff(displacement)
//        } else {
//          0
//        }
//      }
//    }
//    .fold(0d) { _+_ }
//  }
//
//
//  override def computeEnergy(quadTree: QuadTree.Quad, node: NodeWrapper, temperature: Double): Double = {
//    computeLayers(quadTree, node) +
//      computeSprings(quadTree, node, temperature) +
//      computeRepulsion(quadTree, node, temperature) +
//      computeDirections(quadTree, node, temperature) +
//      computeGravityToCenter(quadTree, node)
//  }
//
//  override private[layout] def step(temperature: Double): Unit = {
//    //withLayers { (n, displacement) => n.move(Vector(0, -temperature*displacement)) }
//    super.step(temperature)
//  }
//
//
//}
