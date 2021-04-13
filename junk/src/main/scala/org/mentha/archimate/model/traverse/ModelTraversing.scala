package org.mentha.archimate.model.traverse

import org.mentha.tools.archimate.model.Identifiable.ID
import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._
import org.mentha.tools.archimate.model.edges.impl._

import scala.annotation.tailrec
import scalaz.Scalaz._

object ModelTraversing {

  trait Direction {

    /**
      *
      * @param c
      * @return
      */
    def nextLevelResolver(c: Concept): PartialFunction[EdgeConcept, (EdgeConcept, Concept)]

    /**
      *
      * @param c
      * @param edges
      * @return
      */
    def resolveNextLevel(c: Concept, edges: List[EdgeConcept]): List[(EdgeConcept, Concept)] = {
      edges.collect { nextLevelResolver(c) }
    }

  }


  /**
    * TODO: add http://pubs.opengroup.org/architecture/archimate3-doc/chap12.html#_Toc489946115
    * changes influence (from specific to general)
    */
  object ChangesInfluence extends Direction {
    override def nextLevelResolver(c: Concept): PartialFunction[EdgeConcept, (EdgeConcept, Concept)] = {
      case r: StructuralRelationship if r.target == c => (r, r.source)

      case r: ServingRelationship if r.source == c => (r, r.target)
      case r: InfluenceRelationship if r.source == c => (r, r.target)
      case r: TriggeringRelationship if r.source == c => (r, r.target)
      case r: FlowRelationship if r.source == c => (r, r.target)

      case r: AccessRelationship if r.access.read && r.target == c => (r, r.source)
      case r: AccessRelationship if r.access.write && r.source == c => (r, r.target)

      case r: OtherRelationship if r.source == c => (r, r.target)
      case r: OtherRelationship if r.target == c => (r, r.source)
    }
  }

}

trait ModelVisitor {
  def visitVertex(vertex: Concept): Boolean = true
  def visitEdge(from: Concept, edge: EdgeConcept, to: Concept): Boolean = true
}

class ModelTraversing(direction: ModelTraversing.Direction)(implicit val model: Model) {

  private val modelEdges = model.edges.toList

  private val incomingEdges: Map[ID, List[EdgeConcept]] = modelEdges.groupBy { e => e.target.id }
  private val outgoingEdges: Map[ID, List[EdgeConcept]] = modelEdges.groupBy { e => e.source.id }
  private val allEdges: Map[ID, List[EdgeConcept]] = incomingEdges |+| outgoingEdges

  private def next(c: Concept) = direction.resolveNextLevel(c, allEdges.getOrElse(c.id, Nil))

  def apply(from: Concept*)(visitor: ModelVisitor): Unit = {

    @tailrec def collectEdges(
      todo: List[(Concept, EdgeConcept, Concept)],
      result: List[Concept],
      visited: Set[Identifiable.ID]
    ): (List[Concept], Set[Identifiable.ID]) = todo match {
      case (source, edge, target) :: tail => {
        if (visited.contains(target.id)) {
          collectEdges(tail, result, visited)
        } else {
          val follow = visitor.visitEdge(source, edge, target)
          if (follow) {
            collectEdges(tail, target :: result, visited + target.id)
          } else {
            collectEdges(tail, result, visited + target.id)
          }
        }
      }
      case Nil => (result, visited)
    }

    @tailrec def processVertexes(
      todo: List[Concept],
      result: List[(Concept, EdgeConcept, Concept)],
      visited: Set[Identifiable.ID]
    ): (List[(Concept, EdgeConcept, Concept)], Set[Identifiable.ID]) = todo match {
      case head :: tail => {
        val follow = visitor.visitVertex(head)
        if (follow) {
          processVertexes(tail, result ++ next(head).map { case (e, t) => (head, e, t) }, visited + head.id)
        } else {
          processVertexes(tail, result, visited + head.id)
        }
      }
      case Nil => (result, visited)
    }

    @tailrec def core(l0: List[Concept], v0: Set[Identifiable.ID]): Unit = {
      if (l0 != Nil) {
        val (l1, v1) = processVertexes(l0, Nil, v0)
        val (l2, v2) = collectEdges(l1, Nil, v1)
        core(l2, v2)
      }
    }

    core(from.toList, Set.empty)
  }

}
