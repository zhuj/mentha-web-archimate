package org.mentha.utils.archimate.model

import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.edges.OtherRelationships

import scala.collection.mutable
import scala.xml.XML

object generator {

  private val generated = {
    "@javax.annotation.Generated(Array(\""+this.getClass.getName+"\"))"
  }

  private val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

  private val keys = (xml \ "relations" \ "key")
    .map { el => (el \@ "char").charAt(0) -> ( (el \@ "relationship"), (el \@ "verbs" ) ) }
    .toMap

  private val association: Char = OtherRelationships.association.key
  private val rels = (xml \ "relations" \ "source")
    .flatMap {
      s => (s \ "target").map {
        t => (
          (s \@ "concept"),
          (t \@ "concept"),
          (t \@ "relations" + t \@ "derived").filterNot(_ == association).toSet.mkString("").sorted,
          (t \@ "derived").filterNot(_ == association).toSet
        )
      }
    }
    .filterNot {
      case (_, _, r, _) => r.isEmpty
    }
    .filterNot {
      case (_, d, _, _) => d == "Junction"
    }

  def elements(): Unit = {

    def impl_stream(layer: String): StringBuilderWriter = {

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.utils.archimate.model.nodes.impl")
      writer.println()

      writer.println("import org.mentha.utils.archimate.model._")
      writer.println("import org.mentha.utils.archimate.model.nodes._")
      writer.println("import org.mentha.utils.archimate.model.edges._")
      writer.println()

      writer.println(generated)
      writer.println(s"sealed trait ${layer}Element extends ${layer}Layer {}")

      writer.flush()

      stream
    }

    def dsl_stream(name: String): StringBuilderWriter = {

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.utils.archimate.model.nodes.dsl")
      writer.println()

      writer.println("import org.mentha.utils.archimate.model._")
      writer.println("import org.mentha.utils.archimate.model.nodes._")
      writer.println("import org.mentha.utils.archimate.model.nodes.impl._")
      writer.println("import org.mentha.utils.archimate.model.edges._")
      writer.println()

      writer.println(generated)
      writer.println(s"object ${name} {")
      writer.flush()

      stream
    }

    val relsMap = rels
      .groupBy { case (src, _, _, _) => src }

    val elements = (xml \ "element")
      .map { el => (el \@ "name") -> ((el \@ "layer"), (el \@ "parent"), el) }

    val layers = elements
      .map { case (_, (layer, _, _)) => layer }
      .toSet

    // element classes
    {
      val streams = layers
        .map { layer => (layer, impl_stream(layer)) }
        .toMap

      // elements
      for { (name, (layer, parent, el)) <- elements } {
        val writer = new PrintWriter(streams(layer))
        val fixedParent = StringUtils.replace(parent, "?", "")
        writer.println()
        writer.println("/**")
        (el \ "info").foreach { info => writer.println(s" * ${info.text}") }
        (el \ "text").foreach { text => writer.println(s" * @note ${text.text}") }
        (el \ "link").foreach { link => writer.println(s" * @see [[${(link \@ "src")} ${name} ArchiMate® 3.0 Specification ]]") }
        writer.println(" */")
        writer.println(generated)
        writer.println(s"final class ${name} extends ${fixedParent} with ${layer}Element {")
        writer.println(s"  @inline override def meta: ElementMeta[${name}] = ${layer}Elements.${StringUtils.uncapitalize(name)}")
        writer.println("}")
        writer.flush()
      }

      // meta
      {
        for {(layer, stream) <- streams} {
          val writer = new PrintWriter(stream)
          writer.println()
          writer.println(generated)
          writer.println(s"object ${layer}Elements {")
          writer.println()
          writer.flush()
        }

        val variables = mutable.ListBuffer[(String, String)]()
        for {(name, (layer, _, el)) <- elements} {
          val writer = new PrintWriter(streams(layer))
          val variable = StringUtils.uncapitalize(name)
          writer.println(s"  case object ${variable} extends ElementMeta[${name}] {")
          writer.println(s"    override def newInstance(): ${name} = new ${name}")
          writer.println(s"    override def layerObject: LayerObject = ${layer}Layer")
          writer.println(s"    override def key: String = ${"\""}${(el \@ "key")}${"\""}")
          writer.println(s"    override def name: String = ${"\""}${variable}${"\""}")

          writer.println(s"  }")
          writer.flush()
          variables += (layer -> variable)
        }


        for {(layer, stream) <- streams} {
          val writer = new PrintWriter(stream)
          writer.println()
          writer.println(s"  val ${StringUtils.uncapitalize(layer)}Elements: Seq[ElementMeta[_]] = Seq(${ variables.collect { case (l, v) if layer == l => v }.mkString(", ") })")
          writer.println()
          writer.println("}")
          writer.flush()
        }
      }


      for {(layer, stream) <- streams} {
        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/utils/archimate/model/nodes/impl/${layer}Elements.scala"),
          stream.toString,
          "UTF-8"
        )
      }
    }

    // implicit constructor for all elements
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.utils.archimate.model.nodes")
      writer.println()

      writer.println("import org.mentha.utils.archimate.model._")
      writer.println("import org.mentha.utils.archimate.model.nodes.impl._")
      writer.println()

      writer.println("// "+generated)
      writer.println("package object dsl {")

      writer.println()
      writer.println("  /** @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757969 Derivation Rules ArchiMate® 3.0 Specification ]] */")
      writer.println("  class derived extends scala.annotation.Annotation { }")
      writer.println()

      for { (name, _) <- elements } {
        writer.println(s"  def ${StringUtils.uncapitalize(name)}(implicit model: Model): ${name} = model.add(new ${name})")
      }

      writer.println("}")

      FileUtils.write(
        new java.io.File("src/main/scala/org/mentha/utils/archimate/model/nodes/dsl/package.scala"),
        stream.toString,
        "UTF-8"
      )
    }

    // implicit dsl verbs
    {
      val streams = layers
        .map { name => (name, dsl_stream(s"${name}")) }
        .toMap

      for { (name, (layer, _, _)) <- elements } {
        val writer = new PrintWriter(streams(layer))

        writer.println(s"  implicit class Implicit${name}(src: ${name}) {")

        val localRels = relsMap(name)
          .flatMap { case (_, dst, rs, der) => rs.map { r => (r, dst, der.contains(r)) } }
          .flatMap { case (r, dst, der) => keys(r) match {
            case (rname, verbs) => verbs.split("\\s+").map { verb => (verb, rname, dst, der) }
          } }
          .groupBy { case (verb, _, _, _) => verb }

        for { (verb, seq) <- localRels } {

          val constructor = verb.replaceAll("-\\$[^-]*\\$-", "-").replace('-', '_')

          def _process_parts(parts: List[String], params: List[String]): Unit = {
            val prefix = " " * (4 + 2 * params.size)
            parts match {
              case lastPart :: Nil => {
                for { (_, rname, dst, der) <- seq.sortBy { case (_,_,_,der) => der } } {
                  writer.print(prefix)
                  if (der) { writer.print("@derived ") }
                  writer.print(s"def `${lastPart}`")

                  writer.print(s"(dst: ${dst})(implicit model: Model): ${rname} = _${constructor}(src, dst)")
                  if (params.nonEmpty) {
                    writer.print("(")
                    writer.print(params.reverse.mkString(", "))
                    writer.print(")")
                  }
                  writer.print("(model)")
                  writer.println()
                }
              }
              case middlePart :: tail => {
                val pts = middlePart.split("-\\$[^:]*[:]", 2)
                writer.print(prefix)
                val param = "$"+params.size
                writer.print(s"def `${pts(0)}`")
                writer.println(s"(${param}: ${pts(1)}) = new {")
                _process_parts(tail, param :: params)
                writer.print(prefix)
                writer.println("}")
              }
              case _ => {
              }
            }
          }

          //val parts = verb.split("-\\$[^-]*\\$-")
          val parts = verb.split("\\$-")
          _process_parts(parts.toList, List())
          writer.println()
        }

        writer.println(" }")
        writer.flush()
      }

      for {(name, stream) <- streams} {
        val writer = new PrintWriter(stream)
        writer.println("}")
        writer.flush()

        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/utils/archimate/model/nodes/dsl/${name}.scala"),
          stream.toString,
          "UTF-8"
        )
      }

    }

    // edges validator
    {
      val rels = (xml \ "relations" \ "source").flatMap {
        s => (s \ "target").flatMap {
          t => (t \@ "relations").toSeq
            .map { keys }
            .map { case (r, _) => (
              (s \@ "concept"),
              (t \@ "concept"),
              r
            ) }
        }
      }

    }
  }

  def relationships(): Unit = {

    val relsMap = rels
      .filter { case (src, dst, _, _) => (src != "Junction") && (dst != "Junction") }
      .groupBy { case (src, _, _, _) => src }

    // possible relationships
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.utils.archimate.model.edges.validator")
      writer.println()

      writer.println("import org.mentha.utils.archimate.model._")
      writer.println("import org.mentha.utils.archimate.model.edges._")
      writer.println("import org.mentha.utils.archimate.model.nodes._")
      writer.println("import org.mentha.utils.archimate.model.nodes.impl._")
      writer.println()

      writer.println("// "+generated)
      writer.println("package object impl extends Validation {")
      writer.println()

      writer.println("  import MotivationElements._")
      writer.println("  import StrategyElements._")
      writer.println("  import BusinessElements._")
      writer.println("  import ApplicationElements._")
      writer.println("  import TechnologyElements._")
      writer.println("  import PhysicalElements._")
      writer.println("  import ImplementationElements._")
      writer.println("  import CompositionElements._")
      writer.println()

      writer.println("  val data: Map[(Meta, Meta), (String, String)] = (")
      for { (src, items) <- relsMap } {
        writer.println(s"    in(${StringUtils.uncapitalize(src)}) { source => ")
        for { (_, dst, r1, r2 ) <- items } {
          writer.println("      source.register("+StringUtils.uncapitalize(dst)+", \""+r1.sorted+"\", \""+r2.mkString("").sorted+"\")")
        }
        writer.println(s"    } ++ ")
      }
      writer.println("    Nil")
      writer.println("  ).toMap")

      writer.println()
      writer.println("}")

      FileUtils.write(
        new java.io.File("src/main/scala/org/mentha/utils/archimate/model/edges/validator/impl/package.scala"),
        stream.toString,
        "UTF-8"
      )
    }

  }



  def main(args: Array[String]): Unit = {
    elements()
    relationships()
  }

}
