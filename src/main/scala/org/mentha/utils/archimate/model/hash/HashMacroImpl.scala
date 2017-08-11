package org.mentha.utils.archimate.model.hash

import scala.language.experimental.macros
import scala.language.higherKinds
import scala.reflect.macros.blackbox

@macrocompat.bundle class HashMacroImpl(val c: blackbox.Context) {
  import c.universe._

}
