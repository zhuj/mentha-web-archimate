package org.mentha.tools.archimate.extensions

import org.mentha.tools.archimate.model._

/**
  * @param resources { resource/role -> usage estimation }
  */
final case class Estimations(resources: Map[String, Estimation]) extends ArchimateObjectExtension {

}

/**
  * Project Evaluation and Review Techniques (PERT) Three-point estimation.
  * Based on the assumption that a double-triangular distribution governs the data, several estimates are possible.
  * E = (p + 4m + p) / 6, SD = (p − o) / 6
  *
  * @see [[https://en.wikipedia.org/wiki/Three-point_estimation Three-point estimation (Wikipedia)]]
  * @see [[https://en.wikipedia.org/wiki/Program_evaluation_and_review_technique PERT (Wikipedia)]]
  * @see [[https://en.wikipedia.org/wiki/Estimation_theory Estimation theory (Wikipedia)]]
  * @see [[https://www.riskamp.com/beta-pert The beta-PERT Distribution]]
  * @see [[https://www.tutorialspoint.com/estimation_techniques/estimation_techniques_project_evaluation_review.htm Estimation Techniques - PERT]]
  *
  * @param o optimistic - the best-case estimate
  * @param m most-likely - the most likely estimate
  * @param p pessimistic - the worst-case estimate
  */
final case class Estimation(o: Double, m: Double, p: Double) {
  require(!o.isNaN && 0 <= o)
  require(!m.isNaN && o <= m)
  require(!p.isNaN && m <= p)
}

object Estimation {




}
