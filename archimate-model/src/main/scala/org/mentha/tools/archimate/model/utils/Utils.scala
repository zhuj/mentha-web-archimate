package org.mentha.tools.archimate.model.utils

import org.mentha.tools.archimate.model._

import scala.annotation.tailrec

object Utils {

  // TODO: comment me
  // TODO: optimize me
  def backwardDependencies[V <: Vertex, E <: V with Edge[V]](v: V, edges: Iterable[E]): Set[V] = {
    @tailrec def core(layer: Set[V], visited: Set[V]): Set[V] = {
      if (layer.isEmpty) {
        visited
      } else {
        val next = edges.filter { e => (layer.contains(e.source) || layer.contains(e.target)) && !visited.contains(e) }
        core(next.toSet, visited ++ layer ++ next)
      }
    }
    core(Set(v), Set.empty)
  }


}
