package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model.view._

class SimpleSpringLayoutF(view: View) extends SpringLayoutF(view) {

  override private[layout] val SIZE_BOUND = 0.25d

  //override val barnesHutCore = new BarnesHut( d => -REPULSION_COEFFICIENT / sqr(0.5d * d) )
  override val barnesHutCore = new BarnesHut(
    d => {
      val x = 0.5 * d
      -REPULSION_COEFFICIENT / (sqr(x) * x)
    },
    reducerLength = 0.00,
    reducerBounds = SIZE_BOUND
  )

}
