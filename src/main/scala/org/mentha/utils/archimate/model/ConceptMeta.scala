package org.mentha.utils.archimate.model

import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

import scala.annotation.unchecked.uncheckedVariance
import scala.reflect.ClassTag

object ConceptMeta {

  private[model] val name2meta: Map[String, ConceptMeta[_]] =
    nodes.mapElements ++
    nodes.mapRelationshipConnectors ++
    edges.mapRelations

  def byName(name: String): Option[ConceptMeta[_]] = name2meta.get(name)

}

/** */
abstract class ConceptMeta[+T <: Concept: ClassTag] {
  private[model] val log = LoggerFactory.getLogger(classOf[ConceptMeta[_]])
  private[model] val classTag: ClassTag[T @uncheckedVariance] = implicitly[ClassTag[T]]
  private[model] val runtimeClass: Class[T @uncheckedVariance] = classTag.runtimeClass.asInstanceOf[Class[T]]

  def name: String = StringUtils.uncapitalize(runtimeClass.getSimpleName)

  override def toString: String = s"ConceptMeta(${name})"

  {
    import java.lang.reflect.Modifier
    val modifiers = runtimeClass.getModifiers
    require(!Modifier.isAbstract(modifiers))
    require(Modifier.isFinal(modifiers))
    log.info(s"ConceptMeta: ${this.getClass.getName}: ${name}")
  }

//  val properties = {
//    import scala.reflect.runtime.{universe => ru}
//
//    val cls = runtimeClass
//    val classSymbol = ConceptMeta.runtimeMirror.classSymbol(cls)
//
//    classSymbol.toType.members.collect {
//      case t: ru.TermSymbol if (t.isVar || t.isVal) && t.isPublic => t
//    }
//
//  }
//  if (properties.nonEmpty) {
//    println(name + ":" + properties.mkString(", "))
//  }

}
