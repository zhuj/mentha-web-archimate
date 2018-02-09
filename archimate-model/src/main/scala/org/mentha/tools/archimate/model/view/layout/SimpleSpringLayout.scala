package org.mentha.tools.archimate.model.view.layout

import org.mentha.tools.archimate.model.view._

class SimpleSpringLayoutF(view: View) extends SpringLayoutF(view) {
  override private[layout] val SIZE_BOUND = 0.25d
}
