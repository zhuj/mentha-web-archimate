package org.mentha.tools.archimate.model

sealed trait Derivation {
  def derived: Boolean
}

case object NonDerived extends Derivation {
  override def derived = false
}

case object Derived extends Derivation {
  override def derived = true
}

case class DerivedFrom(cycle: Seq[Relationship]) extends Derivation {
  override def derived = true
}

object Derivation {

  def apply(rel: Relationship): Derivation = {
    val derived = edges.validator.derived(rel.source.meta, rel.target.meta, rel.meta)
    if (derived) Derived else NonDerived
  }

}