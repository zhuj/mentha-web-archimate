package org.mentha.utils.archimate.extensions

import org.mentha.utils.archimate.model._

/**
  *
  * @param o
  * @param m
  * @param p
  */
final case class Estimation(o: Double, m: Double, p: Double) extends ArchimateObjectExtension {
  require(!o.isNaN && 0 <= o)
  require(!m.isNaN && o <= m)
  require(!p.isNaN && m <= p)

}
