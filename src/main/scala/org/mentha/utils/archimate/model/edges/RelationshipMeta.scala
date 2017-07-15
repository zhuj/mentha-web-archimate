package org.mentha.utils.archimate.model.edges

import org.mentha.utils.archimate.model._

import scala.reflect.ClassTag

/** */
class RelationshipMeta[T <: Relationship](implicit override val classTag: ClassTag[T]) extends ConceptMeta[T] {

  def isLinkPossible(sourceMeta: ConceptMeta[_], targetMeta: ConceptMeta[_]): Boolean = true // TODO: implement me: use generator & matrices

  private[model] val reflectConstructor = {
    import scala.reflect.runtime.{universe => ru}
    val runtimeMirror = ru.runtimeMirror(this.getClass.getClassLoader)

    val ConceptClass = classOf[Concept]

    val cls = runtimeClass
    val classSymbol = runtimeMirror.classSymbol(cls)

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
