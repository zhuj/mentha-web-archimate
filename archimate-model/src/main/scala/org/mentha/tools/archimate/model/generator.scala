package org.mentha.tools.archimate.model

import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils

import scala.collection.mutable
import scala.xml.XML

object generator {

  private val generated = {
    "@javax.annotation.Generated(Array(\""+this.getClass.getName+"\"))"
  }

  private val xml = XML.load(this.getClass.getClassLoader.getResource("archimate/model.xml"))

  private val relationships = (xml \ "relationship")
    .map { el => (el \@ "name") -> ((el \@ "kind"), el) }
    .toMap

  private val relationshipKeys = (xml \ "relationship")
    .map { el => (el \@ "key").charAt(0) -> (el \@ "name") }
    .toMap

  private val elements = (xml \ "element")
    .map { el => (el \@ "name") -> ((el \@ "layer"), (el \@ "parent"), el) }

  private val association: Char = 'o' // OtherRelationships.association.key
  private val rels = (xml \ "relations" \ "source")
    .flatMap {
      s => (s \ "target").map {
        t => (
          (s \@ "concept"),
          (t \@ "concept"),
          (t \@ "relations" + t \@ "derived").filterNot(_ == association).toSet.mkString("").sorted,
          Set[Char]() /*(t \@ "derived").filterNot(_ == association).toSet*/ // TODO: keep only derived relations
        )
      }
    }
    .filterNot {
      case (_, _, r, _) => r.isEmpty
    }
    .filterNot {
      case (_, d, _, _) => d == "Junction"
    }

  def mkElements(): Unit = {

    def impl_stream(layer: String): StringBuilderWriter = {

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.tools.archimate.model.nodes.impl")
      writer.println()

      writer.println("import org.mentha.tools.archimate.model._")
      writer.println("import org.mentha.tools.archimate.model.nodes._")
      writer.println("import org.mentha.tools.archimate.model.edges._")
      writer.println()

      writer.println(generated)
      writer.println(s"sealed trait ${layer}Element extends ${layer}Layer {}")

      writer.flush()

      stream
    }

    def dsl_stream(name: String): StringBuilderWriter = {

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.tools.archimate.model.nodes.dsl")
      writer.println()

      writer.println("import org.mentha.tools.archimate.model._")
      writer.println("import org.mentha.tools.archimate.model.nodes._")
      writer.println("import org.mentha.tools.archimate.model.nodes.impl._")
      writer.println("import org.mentha.tools.archimate.model.edges._")
      writer.println("import org.mentha.tools.archimate.model.edges.impl._")
      writer.println()

      writer.println(generated)
      writer.println(s"object ${name} {")
      writer.flush()

      stream
    }

    val relsMap = rels
      .groupBy { case (src, _, _, _) => src }

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
        (el \ "summ").foreach { summ => writer.println(s" * ${summ.text}") }
        writer.println(s" * ==Overview==")
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
          writer.println(s"    override final def key: String = ${"\""}${(el \@ "key")}${"\""}")
          writer.println(s"    override final def name: String = ${"\""}${variable}${"\""}")
          writer.println(s"    override final def layerObject: LayerObject = ${layer}Layer")
          writer.println(s"    override final def newInstance(): ${name} = new ${name}")
          writer.println(s"  }")
          writer.flush()
          variables += (layer -> variable)
        }

        for {(layer, stream) <- streams} {
          val writer = new PrintWriter(stream)
          writer.println()
          writer.println(s"  val ${StringUtils.uncapitalize(layer)}Elements: Seq[ElementMeta[Element]] = Seq(${ variables.collect { case (l, v) if layer == l => v }.mkString(", ") })")
          writer.println()
          writer.println("}")
          writer.flush()
        }
      }

      for {(layer, stream) <- streams} {
        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/tools/archimate/model/nodes/impl/${layer}Elements.scala"),
          stream.toString,
          "UTF-8"
        )
      }
    }

    // implicit constructor for all elements
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.tools.archimate.model.nodes")
      writer.println()

      writer.println("import org.mentha.tools.archimate.model._")
      writer.println("import org.mentha.tools.archimate.model.nodes.impl._")
      writer.println()

      writer.println("// "+generated)
      writer.println("package object dsl {")

      writer.println()
      writer.println("  /** @see [[http://pubs.opengroup.org/architecture/archimate3-doc/chap05.html#_Toc451757969 Derivation Rules ArchiMate® 3.0 Specification ]] */")
      writer.println("  class derived extends scala.annotation.Annotation { }")
      writer.println()

      for { (name, (layer, _, el)) <- elements } {
        writer.println(s"  /** ${layer}: ${ (el\"summ").map{ s => StringUtils.stripEnd(s.text, ".,;") }.mkString(". ") } */")
        writer.println(s"  def ${StringUtils.uncapitalize(name)}(implicit model: Model): ${name} = model.add(new ${name})")
        writer.println()
      }

      writer.println()
      writer.println(s"  import org.mentha.tools.archimate.model.edges.RelationshipMeta")
      writer.println(s"  def andJunction(relationship: RelationshipMeta[Relationship])(implicit model: Model): Junction = model.add(new AndJunction(relationship))")
      writer.println(s"  def orJunction(relationship: RelationshipMeta[Relationship])(implicit model: Model): Junction = model.add(new OrJunction(relationship))")
      writer.println()


      writer.println("}")

      FileUtils.write(
        new java.io.File("src/main/scala/org/mentha/tools/archimate/model/nodes/dsl/package.scala"),
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

        writer.println("")
        writer.println(s"  implicit class Implicit${name}(src: ${name})(implicit val model: Model) {")

        writer.println("")
        writer.println(s"    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)")
        writer.println("")

        val localRels = relsMap(name)
          .flatMap { case (_, dst, rs, der) => rs.map { r => (r, dst, der.contains(r)) } }
          .flatMap { case (r, dst, der) => {
            val rname = relationshipKeys(r)
            val (_, rel) = relationships(rname)
            (rel \ "verb").map { verb => (verb.text, rname, dst, der) }
          } }
          .groupBy { case (verb, _, _, _) => verb }

        writeDslVerbs(writer, localRels)

        writer.println("  }")
        writer.flush()
      }

      {
        val stream = dsl_stream("Junctions")
        val writer = new PrintWriter(stream)

        writer.println("")
        writer.println(s"  implicit class ImplicitJunction(src: Junction)(implicit val model: Model) {")

        writer.println("")
        writer.println(s"    def `associated with`(dst: Concept): AssociationRelationship = _associated_with(src, dst)(model)")
        writer.println("")

        val localRels = if (false) {
          rels
            .flatMap { case (_, dst, rs, _) => rs.map { r => (r, dst) } }
            .flatMap { case (r, dst) => {
              val rname = relationshipKeys(r)
              val (_, rel) = relationships(rname)
              (rel \ "verb").map { verb => (verb.text, rname, dst, false) }
            } }
            .groupBy { case (verb, _, _, _) => verb }
        } else {
          relationships
            .filter { case (_, (_, rel)) => (rel \@ "key").charAt(0) != association  }
            .flatMap { case (rname, (_, rel)) => (rel \ "verb").map { verb => (verb.text, rname, "Concept", false) } }.toSeq
            .groupBy { case (verb, _, _, _) => verb }
        }

        writeDslVerbs(writer, localRels)

        writer.println("  }")

        writer.println("}")
        writer.flush()

        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/tools/archimate/model/nodes/dsl/Junctions.scala"),
          stream.toString,
          "UTF-8"
        )
      }

      for {(name, stream) <- streams} {
        val writer = new PrintWriter(stream)
        writer.println("}")
        writer.flush()

        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/tools/archimate/model/nodes/dsl/${name}.scala"),
          stream.toString,
          "UTF-8"
        )
      }
    }
  }

  private def writeDslVerbs(writer: PrintWriter, localRels: Map[String, Seq[(String, String, String, Boolean)]]): Unit = {
    for { (verb, seq) <- localRels } {
      val constructor = verb.replaceAll("-\\$[^-]*\\$-", "-").replace('-', '_')
      def _process_parts(parts: List[String], params: List[String]): Unit = {
        val prefix = " " * (4 + 2 * params.size)
        parts match {
          case lastPart :: Nil => {
            val rname = seq.iterator.next()._2

            def writeMethod(dst: String, der: Boolean) = {
              writer.print(prefix)
              if (der) { writer.print("@derived ") }
              writer.print(s"def `${lastPart.replace('-', ' ')}`")
              writer.print(s"(dst: ${dst}): ${rname} = _${constructor}(src, dst)")
              if (params.nonEmpty) {
                writer.print("(")
                writer.print(params.reverse.mkString(", "))
                writer.print(")")
              }
              writer.print("(model)")
              writer.println()
            }

            writeMethod("Junction", der = false)
            for {(_, _, dst, der) <- seq.sortBy { case (_, _, _, der) => der } } {
              writeMethod(dst, der)
            }
          }
          case middlePart :: tail => {
            val pts = middlePart.split("-\\$[^:]*[:]", 2)
            writer.print(prefix)
            val param = "$" + params.size
            writer.print(s"def `${pts(0)}`")
            writer.println(s"(${param}: ${pts(1).replace('-', ' ')}) = new {")
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
  }

  def mkRelationships(): Unit = {

    def impl_stream(kind: String): StringBuilderWriter = {

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("package org.mentha.tools.archimate.model.edges.impl")
      writer.println()

      writer.println("import org.mentha.tools.archimate.model._")
      writer.println("import org.mentha.tools.archimate.model.edges._")
      writer.println()

      writer.flush()

      stream
    }

    val kinds = relationships
      .map { case (_, (kind, _)) => kind }
      .toSet

    // relationship classess
    {
      val streams = kinds
        .map { kind => (kind, impl_stream(kind)) }
        .toMap

      // elements
      for { (name, (kind, el)) <- relationships } {
        val writer = new PrintWriter(streams(kind))
        writer.println()
        writer.println("/**")
        (el \ "summ").foreach { summ => writer.println(s" * ${summ.text}") }
        writer.println(s" * ==Overview==")
        (el \ "info").foreach { info => writer.println(s" * ${info.text}") }
        (el \ "text").foreach { text => writer.println(s" * @note ${text.text}") }
        (el \ "link").foreach { link => writer.println(s" * @see [[${(link \@ "src")} ${name} ArchiMate® 3.0 Specification ]]") }

        val params = (el \ "param")
        val paramStr = if (params.isEmpty) "" else s"(${
          params.map {
            el => "var " + (el \@ "name") + ": " + (el \@ "type") + " = " + (el \@ "default")
          }.mkString(", ")
        })"

        writer.println(" */")
        writer.println(generated)
        writer.println(s"final class ${name}(source: Concept, target: Concept)${paramStr}")
        writer.println(s"  extends ${kind}Relationship(source: Concept, target: Concept) {")
        writer.println(s"  @inline override def meta: RelationshipMeta[${name}] = ${kind}Relationships.${StringUtils.uncapitalize(name)}")
        params.foreach { param =>
          val n = param \@ "name"
          val t = param \@ "type"
          writer.println(s"  @inline def with${StringUtils.capitalize(n)}(${n}: ${t}): this.type = {")
          writer.println(s"    this.${n} = ${n}")
          writer.println(s"    this")
          writer.println(s"  }")
        }
        writer.println("}")
        writer.flush()
      }

      // meta
      {
        for {(kind, stream) <- streams} {
          val writer = new PrintWriter(stream)
          writer.println()
          writer.println(generated)
          writer.println(s"object ${kind}Relationships {")
          writer.println()
          writer.flush()
        }

        val variables = mutable.ListBuffer[(String, String)]()
        for {(name, (kind, el)) <- relationships} {
          val params = (el \ "param")
          val writer = new PrintWriter(streams(kind))
          val variable = StringUtils.uncapitalize(name)
          writer.println(s"  case object ${variable} extends RelationshipMeta[${name}] {")
          writer.println(s"    override final def key: Char = ${"'"}${(el \@ "key")}${"'"}")
          writer.println(s"    override final def name: String = ${"\""}${variable}${"\""}")
          writer.println(s"    override final def newInstance(source: Concept, target: Concept): ${name} = new ${name}(source, target)${if(params.isEmpty) "" else "()"}")
          writer.println(s"  }")
          writer.flush()
          variables += (kind -> variable)
        }

        for {(layer, stream) <- streams} {
          val writer = new PrintWriter(stream)
          writer.println()
          writer.println(s"  val ${StringUtils.uncapitalize(layer)}Relationships: Seq[RelationshipMeta[Relationship]] = Seq(${ variables.collect { case (l, v) if layer == l => v }.mkString(", ") })")
          writer.println()
          writer.println("}")
          writer.flush()
        }
      }

      for {(kind, stream) <- streams} {
        FileUtils.write(
          new java.io.File(s"src/main/scala/org/mentha/tools/archimate/model/edges/impl/${kind}Relationships.scala"),
          stream.toString,
          "UTF-8"
        )
      }
    }

    // validator
    {
      val allJunctions = Seq("orJunction", "andJunction").toVector
      val allRelationships = relationshipKeys.values.map { StringUtils.uncapitalize }.toVector
      def expand(concept: String): Seq[String] = concept match {
        case "junction" => allJunctions
        case "relationship" => allRelationships
        case _ => List(concept)
      }

      val relsMap = rels
        .map { case (src, dst, r1, r2) => (
          StringUtils.uncapitalize(src),
          StringUtils.uncapitalize(dst),
          r1.sorted,
          r2.mkString("").sorted
        ) }
        .flatMap { case (src, dst, r1, r2) => expand(src).map { s => (s, dst, r1, r2)} }
        .flatMap { case (src, dst, r1, r2) => expand(dst).map { d => (src, d, r1, r2)} }
        .groupBy { case (src, _, _, _) => src }

      // possible relationships
      {
        val stream = new StringBuilderWriter(4096)
        val writer = new PrintWriter(stream)

        writer.println("package org.mentha.tools.archimate.model.edges.validator")
        writer.println()

        writer.println("import org.mentha.tools.archimate.model._")
        writer.println("import org.mentha.tools.archimate.model.edges._")
        writer.println("import org.mentha.tools.archimate.model.edges.impl._")
        writer.println("import org.mentha.tools.archimate.model.nodes._")
        writer.println("import org.mentha.tools.archimate.model.nodes.impl._")
        writer.println()

        writer.println(generated)
        writer.println("object data {")
        writer.println()

        writer.println("  import StructuralRelationships._")
        writer.println("  import DependencyRelationships._")
        writer.println("  import DynamicRelationships._")
        writer.println("  import OtherRelationships._")
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

        writer.println("  import RelationshipConnectors._")
        writer.println()

        writer.println("  type CMeta = validator.CMeta")
        writer.println("  type RMeta = validator.RMeta")
        writer.println("  type RSet = Set[RMeta]")
        writer.println()

        for { (k, nm) <- relationshipKeys } {
          writer.println(s"  private val ${k} = ${StringUtils.uncapitalize(nm)}")
        }
        writer.println()

        var combinations = Set[String]()
        val dataStream = {
          val stream = new StringBuilderWriter(4096)
          val writer = new PrintWriter(stream)
          for {(src, items) <- relsMap} {
            writer.println(s"  private def ${src}Data: Map[(CMeta, CMeta), (RSet, RSet)] = Map(")
            for {(_, dst, r1, r2) <- items} {
              combinations += r1
              combinations += r2
              writer.println(s"    ((${src}, ${dst}), ($$${r1}, $$${r2})),")
            }
            writer.println("  )")
          }
          writer.flush()
          stream
        }

        for { c <- combinations.toSeq.sorted } {
          writer.println(s"  private val $$${c}: RSet = Set[RMeta](${c.mkString(", ")})")
        }
        writer.println()

        writer.println(dataStream.toString)
        writer.println()

        writer.println("  val data: Map[(CMeta, CMeta), (RSet, RSet)] = Seq(")
        for {(src, _) <- relsMap} {
          writer.println(s"   ${src}Data,")
        }
        writer.println("  ).flatten.toMap")
        writer.println()
        writer.println("}")

        FileUtils.write(
          new java.io.File("src/main/scala/org/mentha/tools/archimate/model/edges/validator/data.scala"),
          stream.toString,
          "UTF-8"
        )
      }

      // client restrictions
      {

        val stream = new StringBuilderWriter(4096)
        val writer = new PrintWriter(stream)

        import edges.validator
        import play.api.libs.json._


        val json = Json.toJsObject(
          validator.data.data.map { case ((s,d), (all, _)) => s"${s.name}-${d.name}" -> all.map { _.key }.mkString("").sorted }
        )

        writer.println("export const constraints = ({")

        for {(src, items) <- relsMap} {
          for {(_, dst, r1, r2) <- items} {
            writer.println(s"  '${src}-${dst}': '${r1}',")
          }
        }

        writer.println("});")

        writer.flush()
        FileUtils.write(
          new java.io.File(s"client/src/components/view/edges/constraints.js"),
          stream.toString,
          "UTF-8"
        )

      }

    }
  }

  private def mkClientHelp(): Unit = {
    import play.api.libs.json._

    // elements
    {
      val json = Json.toJsObject(
        elements.map {
          case (name, (layer, parent, el)) => StringUtils.uncapitalize(name) -> Json.obj(
            "name" -> name,
            "layer" -> layer,
            "help" -> Json.obj(
              "summ" -> (el \ "summ").map { _.text },
              "info" -> (el \ "info").map { _.text },
              //"text" -> (el \ "text").map { _.text },
            )
          )
        }.toMap
      )

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("export const elementMeta = (")
      writer.println(json.toString())
      writer.println(");")

      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/meta/elements.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // relations
    {
      val json = Json.toJsObject(
        relationships.map {
          case (name, (kind, el)) => StringUtils.uncapitalize(name) -> Json.obj(
            "name" -> name,
            "kind" -> kind,
            "help" -> Json.obj(
              "summ" -> (el \ "summ").map { _.text },
              "info" -> (el \ "info").map { _.text },
              //"text" -> (el \ "text").map { _.text },
            )
          )
        }.toMap
      )

      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)

      writer.println("export const relationMeta = (")
      writer.println(json.toString())
      writer.println(");")

      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/meta/relations.js"),
        stream.toString,
        "UTF-8"
      )
    }

  }


  def main(args: Array[String]): Unit = {
    mkElements()
    mkRelationships()
    mkClientHelp()
  }

}