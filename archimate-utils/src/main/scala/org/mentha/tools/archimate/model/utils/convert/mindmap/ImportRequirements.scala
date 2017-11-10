package org.mentha.tools.archimate.model.utils.convert.mindmap

import java.io.{File, StringReader}

import org.apache.commons.io.FileUtils
import org.mentha.tools.archimate.model._
import org.mentha.tools.archimate.model.edges._
import org.mentha.tools.archimate.model.nodes.dsl._
import org.mentha.tools.archimate.model.nodes.impl.Resource
import org.mentha.tools.archimate.model.utils.MkModel
import org.mentha.tools.archimate.model.view._
import org.mentha.tools.archimate.model.view.dsl._
import org.mentha.utils.uuid.FastTimeBasedIdGenerator

import scala.collection.concurrent._
import scala.xml._

/**
  * It imports requirements from FreeMind file to Model Json
  * @see [[http://freemind.sourceforge.net/wiki/index.php/File_format]]
  */
object ImportRequirements extends MkModel {

  private val processIds: Boolean = true
  private val generateIdsViews: Boolean = true
  private val generateIdsViewsAlways: Boolean = true

  def _id(base: Long): String = FastTimeBasedIdGenerator.generateId(0, Identifiable.timeSource.value.getAsLong << 8 | (base & 0xff))

  def main(args: Array[String]): Unit = {
    val name = args(0)
    implicit val model = new Model withId s"convert-${name.replace('/', '-')}"

    val resources = TrieMap.empty[String, Resource]

    val xmlFile = new File(s"${name}.mm")
    val xmlContent = FileUtils.readFileToString(xmlFile, "UTF-8")
    val xml = XML.load(new StringReader(xmlContent))

    def getTitle(node: Node) = node.attributes.collectFirst { case a: Attribute if "TEXT".equalsIgnoreCase(a.key) => a.value.head.text } getOrElse ("")

    def isRole(title: String) =  title.startsWith("(") && title.endsWith(")")
    def isMeta(title: String) = title.startsWith("[")
    def isEstimate(title: String) = title.startsWith("estimate=")
    def isCommand(title: String) = isRole(title) || isMeta(title) || isEstimate(title)

    def withNode(parent: Option[ViewNodeConcept[Element]], top: Double, level: Double)(reqId: Option[String], node: Node)(implicit view: View): Double = {

      def addCurrent(el: Element, e: (Concept, Concept) => Relationship): ViewNodeConcept[Element] = {
        val current: ViewNodeConcept[Element] = in(view) node { el } withPosition { Vector(x = level * 140, y = top * 40) }
        for {p <- parent} {
          val edge = in(view) edge { e(p.concept, current.concept) }
          val point = Vector(x = p.position.x, y = current.position.y)
          if ( (Math.abs(point.x - p.position.x) > 0.75 * p.size.width) || (Math.abs(point.y - p.position.y) > 0.75 * p.size.height) ) {
            edge withPoints { Seq(point - Vector(0, 5), point, point + Vector(5, 0)) }
          }
        }
        current
      }

      val title = getTitle(node)
      if (isCommand(title)) {
        val p = parent.get // or else die
        if (isEstimate(title)) {

          val notes = in(view).notes(title)
          notes.withPosition { Vector(x = level * 140, y = top * 40) } withSize { Size(120, 32) }
          in(view).connect(p, notes)
          1 + top

        } else if (isRole(title)) {

          val role = title.substring(1, title.length-1)
//          val res = resources.getOrElseUpdate(role, resource withName role)
//          val i = resources.keys.toIndexedSeq.indexOf(role)
//          val current: ViewNodeConcept[Element] = in(view) node { res } withPosition { Vector(x = 6 * 140, y = -40*(1 + i)) }
//          val edge = in(view) edge { _associated_with(current.concept, p.concept) }

          val seq = (node \ "node")
          Math.max(
            seq.foldLeft(top) { (v, node) => withNode(parent, v, level)(reqId, node) },
            1 + top
          )

        } else {

          top // just skip
        }
      } else {



        if (processIds && parent.isDefined && (title.startsWith("{") && title.endsWith("}"))) {

          val id = title.substring(1, title.length - 1)
          withNode(None, 0, 0)(None, node)( model.add( _id(node.hashCode()) ) { new View withName s"${id}" } )
          val current = addCurrent( requirement withName title, _composes )

          if (generateIdsViews) {

            def descendant(node: Node): List[Node] = node.child.filter { x => !isCommand(getTitle(x)) }.toList.flatMap { x => x :: descendant(x) }
            if (generateIdsViewsAlways || node.flatMap(descendant).length > 2) {
              1 + top
            } else {
              val seq = (node \ "node").filter { n => !isCommand(getTitle(n)) }
              val shift = if (seq.length > 1) { 1 } else { 0 }
              Math.max(
                seq.foldLeft(top + shift) { (v, node) => withNode(Some(current), v, 1 + level)(reqId, node) },
                1 + top
              )
            }

          } else {
            1 + top
          }

        } else {
          val current = addCurrent( requirement withName title, _composes )
          val seq = (node \ "node")
          val shift = if (seq.filter { n => !isCommand(getTitle(n)) }.length > 1) { 1 } else { 0 }
          Math.max(
            seq.foldLeft(top + shift) { (v, node) => withNode(Some(current), v, 1 + level)(reqId, node) },
            1 + top
          )
        }
      }
    }

    for { node <- xml \ "node" \ "node" } {
      implicit val view  = model.add( _id(node.hashCode()) ) { new View withName s"BLOCK: ${getTitle(node)}" }
      withNode(None, 0, 0)(None, node)
      println(s"view  = ${view.name}")
      println(s"nodes = ${view.nodes.size}")
      println(s"edges = ${view.edges.size}")
      // in(view) layoutSimple()
      println()
    }

    println(s"nodes = ${model.nodes.size}")
    println(s"edges = ${model.edges.size}")

    val str = json.toJsonString(model, pretty = true)
    json.fromJsonString(str) // check
    // println(str)

    val jsonFile = new File(s"${name}.json")
    FileUtils.write(jsonFile, str, "UTF-8")

    if (args.length > 1 && args(1).length > 0) {
      publishModel { model withId args(1) }
    }

  }

}
