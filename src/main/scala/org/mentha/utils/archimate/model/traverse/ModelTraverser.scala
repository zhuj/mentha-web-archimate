package org.mentha.utils.archimate.model.traverse

import org.mentha.utils.archimate.model.Identifiable.ID
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl._

import scala.annotation.tailrec
import scalaz.Scalaz._

object ModelTraverser {

  sealed trait Direction {
    def nextLevelCollector(c: Concept): PartialFunction[EdgeConcept, (EdgeConcept, Concept)]
  }


  /**
    * TODO: add http://pubs.opengroup.org/architecture/archimate3-doc/chap12.html#_Toc489946115
    * changes influence (from specific to general)
    */
  object ChangesInfluence extends Direction {
    override def nextLevelCollector(c: Concept): PartialFunction[EdgeConcept, (EdgeConcept, Concept)] = {
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
  def visitConcept(c: Concept): Unit = {}
  def visitEdge(from: Concept, edge: EdgeConcept, to: Concept): Unit = {}
}

class ModelTraverser(direction: ModelTraverser.Direction)(implicit val model: Model) {

  private val modelEdges = model.edges.toList

  private val incomingEdges: Map[ID, List[EdgeConcept]] = modelEdges.groupBy { e => e.target.id }
  private val outgoingEdges: Map[ID, List[EdgeConcept]] = modelEdges.groupBy { e => e.source.id }
  private val allEdges: Map[ID, List[EdgeConcept]] = incomingEdges |+| outgoingEdges

  private def next(c: Concept) = allEdges(c.id).collect { direction.nextLevelCollector(c) }

  def apply(from: Concept*)(visitor: ModelVisitor): Unit = {

    @tailrec def collectEdges(
      todo: List[(Concept, EdgeConcept, Concept)],
      result: List[Concept],
      visited: Set[Identifiable.ID]
    ): (List[Concept], Set[Identifiable.ID]) = todo match {
      case (source, edge, target) :: tail => {
        if (visited.contains(target.id)) {
          collectEdges( tail, result, visited )
        } else {
          visitor.visitEdge(source, edge, target)
          collectEdges( tail, target :: result, visited + target.id )
        }
      }
      case Nil => (result, visited)
    }

    @tailrec def processNodes(
      todo: List[Concept],
      result: List[(Concept, EdgeConcept, Concept)],
      visited: Set[Identifiable.ID]
    ): (List[(Concept, EdgeConcept, Concept)], Set[Identifiable.ID]) = todo match {
      case head :: tail => {
        visitor.visitConcept(head)
        processNodes(tail, result ++ next(head).map { case (e, t) => (head, e, t) }, visited + head.id)
      }
      case Nil => (result, visited)
    }

    @tailrec def core(l0: List[Concept], v0: Set[Identifiable.ID]): Unit = {
      if (l0 != Nil) {
        val (l1, v1) = processNodes(l0, Nil, v0)
        val (l2, v2) = collectEdges(l1, Nil, v1)
        core(l2, v2)
      }
    }

    core(from.toList, Set.empty)
  }

}
