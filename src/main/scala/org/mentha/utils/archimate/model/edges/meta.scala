package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

import scala.reflect.ClassTag

/** */
abstract class RelationshipMeta[+T <: Relationship: ClassTag] extends ConceptMeta[T] {

  def key: Char = ???
  def isLinkPossible(sourceMeta: ConceptMeta[Concept], targetMeta: ConceptMeta[Concept]): Boolean = {
    validator.validate(sourceMeta, targetMeta, this)
  }

  override def toString: String = s"RelationshipMeta(${name})"

  private[model] val reflectConstructor = {
    import scala.reflect.runtime.{universe => ru}
    val runtimeMirror = ru.runtimeMirror(this.getClass.getClassLoader)

    val ConceptClass = classOf[Concept]
    val classSymbol = runtimeMirror.classSymbol(runtimeClass)

    val constructor = classSymbol.toType.member(ru.termNames.CONSTRUCTOR).asMethod
    val pls = constructor.paramLists

    require(pls.nonEmpty, s"${name} should have non-default constructor.")

    // check if the first group contains only two aspects (from and to)
    require(pls.head.size == 2, s"${name} should have constructor with 2 arguments.")
    require(pls.head.forall(param => {
      ConceptClass.isAssignableFrom(runtimeMirror.runtimeClass(param.typeSignature))
    }), s"${name} should have constructor (Concept -> Concept).")

    // check if all following groups have only parameters with default values
    for { secondaryParams <- pls.tail } {
      require(
        secondaryParams.forall( param => param.asTerm.isParamWithDefault ),
        s"${name} should have only parameters with default values in a second part"
      )
    }

    runtimeMirror
      .reflectClass(classSymbol)
      .reflectConstructor(constructor)
  }

  def newInstance(source: Concept, target: Concept): T = reflectConstructor(source, target).asInstanceOf[T]

}
