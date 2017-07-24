package org.mentha.utils.archimate.model

import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils

import scala.collection.mutable
import scala.xml.XML

object generator {

  val generated = {
    "@javax.annotation.Generated(Array(\""+this.getClass.getName+"\"))"
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

    val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

    val keys = (xml \ "relations" \ "key")
      .map { el => (el \ "@char").text.charAt(0) -> ( (el \ "@relationship").text, (el \ "@verbs" ).text ) }
      .toMap

    val association: Char = 'o' // <key char="o" relationship="AssociationRelationship" />
    val rels = (xml \ "relations" \ "source")
      .flatMap {
        s => (s \ "target").map {
          t => (
            (s \ "@concept").text,
            (t \ "@concept").text,
            (t \ "@relations").text.filterNot(_ == association).toSet.mkString("").sorted
          )
        }
      }
      .filterNot {
        case (_, _, r) => r.isEmpty
      }
      .filterNot {
        case (_, d, _) => d == "Junction"
      }

    val relsMap = rels
      .groupBy { case (src, _, _) => src }

    val elements = (xml \ "element")
      .map { el => (el \ "@name").text -> ((el \ "@layer").text, (el \ "@parent").text, el) }

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
        (el \ "link").foreach { link => writer.println(s" * @see [[${(link \ "@src").text} ${name} ArchiMate® 3.0 Specification ]]") }
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
          writer.println(s"    override def key: String = ${"\""}${(el \ "@key").text}${"\""}")
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

      writer.println("package object dsl {")

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
          .flatMap { case (_, dst, rs) => rs.map { r => (r, dst) } }
          .flatMap { case (r, dst) => keys(r) match {
            case (rname, verbs) => verbs.split("\\s+").map { verb => (verb, rname, dst) }
          } }
          .groupBy { case (verb, _, _) => verb }

        for { (verb, seq) <- localRels } {

          val constructor = verb.replaceAll("-\\$[^-]*\\$-", "-").replace('-', '_')
          val parts = verb.split("-\\$[^-]*\\$-")

          def _process_parts(parts: List[String], params: List[String]): Unit = {
            val prefix = " " * (4 + 2 * params.size)
            parts match {
              case part :: Nil => {
                for { (_, rname, dst) <- seq } {
                  writer.print(prefix)
                  writer.print(s"def `${part}`")
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
              case part :: tail => {
                writer.print(prefix)
                val param = "$"+params.size
                writer.print(s"def `${part}`")
                writer.println(s"(${param}: String)(implicit model: Model) = new {")
                _process_parts(tail, param :: params)
                writer.print(prefix)
                writer.println("}")
              }
              case _ => {

              }
            }
          }

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
          t => (t \ "@relations").text.toSeq
            .map { keys }
            .map { case (r, _) => (
              (s \ "@concept").text,
              (t \ "@concept").text,
              r
            ) }
        }
      }


    }
  }

  def main(args: Array[String]): Unit = {
    elements()
  }

}
