package trash

import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.view._

object clientGenerator {

  def main(args: Array[String]): Unit = {

//    writeLinks()
//    writeNodes()
    // writeNodesScss()

  }

//  private def writeNodesScss(): Unit = {
//
//    if (true) { return }
//
//    import scala.reflect.runtime.{universe => ru}
//
//    val runtimeMirror = ru.runtimeMirror(this.getClass.getClassLoader)
//
//    import org.mentha.utils.archimate.model.nodes
//
//    val stream = new StringBuilderWriter(4096)
//    val writer = new PrintWriter(stream)
//
//
//    val processed = mutable.Set[ru.ClassSymbol]()
//    def process(classSymbol: ru.ClassSymbol, write: Boolean = true): Unit = if (!processed.contains(classSymbol)) {
//
//
//      val symbols = classSymbol
//        .baseClasses
//        .collect { case c: ru.ClassSymbol if c != classSymbol => c }
//        .filter { case c => c.toType <:< ru.typeOf[Element] || c.toType <:< ru.typeOf[Layer] }
//        .foldLeft(List[ru.ClassSymbol]()) {
//          (list, c) =>
//            list.collectFirst { case e if e.toType <:< c.toType => true } match {
//              case Some(true) => list
//              case _ => c :: list
//            }
//        }
//
//      for { s <- symbols } { process(s) }
//
//      if (write) {
//        processed += classSymbol
//        writer.println(s".${StringUtils.uncapitalize(classSymbol.name.toString)} {")
//        for {s <- symbols} {
//          writer.println(s"  @extend .${StringUtils.uncapitalize(s.name.toString)};")
//        }
//        writer.println(s"}")
//        writer.println("")
//      }
//    }
//
//    // requirements
//    for {nod <- nodes.allNodes} {
//      process(runtimeMirror.classSymbol(nod.runtimeClass), write = false)
//    }
//
//    // nodes
//    writer.println(".archimate-diagrams-canvas {")
//    writer.println("")
//
//    for {nod <- nodes.allNodes} {
//      process(runtimeMirror.classSymbol(nod.runtimeClass), write = true)
//    }
//
//    writer.println("")
//    writer.println("}")
//    writer.flush()
//    FileUtils.write(
//      new java.io.File(s"client/src/components/view/nodes/nodes.scss"),
//      stream.toString,
//      "UTF-8"
//    )
//
//  }


  private def writeNodes(): Unit = {

    import org.mentha.utils.archimate.model.nodes

    implicit class Meta(meta: ConceptMeta[_]) {
      def prefix: String = meta match {
        case e: ElementMeta[_] => e.layerObject.letter.toString.toLowerCase
        case _: RelationshipConnectorMeta[_] => "x"
        case _ => "z"
      }
    }

    // nodes
    for {nod <- nodes.allNodes} {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = nod.name
      writeNodeFile(writer, rname, s"model_${nod.prefix}")
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/nodes/model_${nod.prefix}/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // ViewNotes
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewNotes].getSimpleName)
      writeNodeFile(writer, rname, base = "ViewNodeWidget")
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/nodes/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // ViewNodeConcept
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewNodeConcept[_]].getSimpleName)
      writeNodeFile(
        writer,
        rname,
        base = "ViewNodeWidget",
        header = (writer) => {
          for {nod <- nodes.allNodes} {
            val cname = StringUtils.capitalize(nod.name)
            writer.println(s"import { ${cname}Widget } from '../model_${nod.prefix}/${nod.name}.js'")
          }
          writer.println("")
        },
        body = (writer) => {
          writer.println(s"  render() {")
          writer.println(s"    const { conceptInfo } = this.props;")
          writer.println(s"    switch(conceptInfo['_tp']) {")

          for {nod <- nodes.allNodes} {
            val cname = StringUtils.capitalize(nod.name)
            writer.println(s"      case '${nod.name}': return (<${cname}Widget {...this.props}/>);")
          }

          writer.println(s"    }")
          writer.println(s"    return super.render();")
          writer.println(s"  }")
        }
      )
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/nodes/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    val stream = new StringBuilderWriter(4096)
    val writer = new PrintWriter(stream)

    writer.println("import React from 'react'")
    writer.println("")

    writer.println(s"import { ViewNodeConceptWidget } from './view/viewNodeConcept'")
    writer.println(s"import { ViewNotesWidget } from './view/viewNotes'")
    writer.println("")

    writer.println(s"export class ViewNodeWidget extends React.Component {")
    writer.println(s"  render() {")
    writer.println(s"    const { viewObject } = this.props.node;")
    writer.println(s"    switch(viewObject['_tp']) {")
    writer.println(s"      case 'viewNodeConcept': return (<ViewNodeConceptWidget conceptInfo={viewObject.conceptInfo} {...this.props}/>)")
    writer.println(s"      case 'viewNotes': return (<ViewNotesWidget {...this.props}/>)")
    writer.println(s"    }")
    writer.println(s"  }")
    writer.println(s"}")
    writer.println("")


    writer.flush()
    FileUtils.write(
      new java.io.File(s"client/src/components/view/nodes/ViewNodeWidget.js"),
      stream.toString,
      "UTF-8"
    )
  }

  private def writeNodeFile(
                             writer: PrintWriter,
                             rname: String,
                             level: String = "",
                             base: String = "ModelNodeWidget",
                             header: PrintWriter => Unit = (_) => {},
                             body: PrintWriter => Unit = (_) => {}
                           ) = {

    val cname = StringUtils.capitalize(rname)

    writer.println("import React from 'react'")
    writer.println("import _ from 'lodash'")
    writer.println("")

    header(writer)

    writer.println(s"import { ${base} } from '../BaseNodeWidget'")
    writer.println("")

    writer.println(s"export const TYPE='${rname}';")
    writer.println("")

    writer.println(s"export class ${cname}Widget extends ${base} {")
    writer.println(s"  constructor(props) { super(props); }")
    writer.println(s"  getClassName(node) { return 'a-node ${level} ${rname}'; }")

    body(writer)

    writer.println(s"}")
    writer.println("")
  }



  private def writeLinks(): Unit = {

    import org.mentha.utils.archimate.model.edges

    // edges
    for {rel <- edges.allRelations} {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = rel.name
      writeLinkFile(writer, rname)
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/edges/model/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // viewConnection
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewConnection].getSimpleName)
      writeLinkFile(writer, rname, base = "ViewLinkWidget")
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/edges/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // viewRelationship
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewRelationship[_]].getSimpleName)
      writeLinkFile(
        writer,
        rname,
        base = "ViewLinkWidget",
        header = (writer) => {
          for {rel <- edges.allRelations} {
            val cname = StringUtils.capitalize(rel.name)
            writer.println(s"import { ${cname}Widget } from '../model/${rel.name}'")
          }
          writer.println("")
        },
        body = (writer) => {
          writer.println(s"  render() {")
          writer.println(s"    const { conceptInfo } = this.props;")
          writer.println(s"    switch(conceptInfo['_tp']) {")

          for {rel <- edges.allRelations} {
            val cname = StringUtils.capitalize(rel.name)
            writer.println(s"      case '${rel.name}': return (<${cname}Widget {...this.props}/>);")
          }

          writer.println(s"    }")
          writer.println(s"    return super.render();")
          writer.println(s"  }")
        }
      )
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/view/edges/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    val stream = new StringBuilderWriter(4096)
    val writer = new PrintWriter(stream)

    writer.println("import React from 'react'")
    writer.println("")

    writer.println(s"import { ViewConnectionWidget } from './view/viewConnection'")
    writer.println(s"import { ViewRelationshipWidget } from './view/viewRelationship'")
    writer.println("")

    writer.println(s"export class ViewEdgeWidget extends React.Component {")
    writer.println(s"  render() {")
    writer.println(s"    const { viewObject } = this.props.link;")
    writer.println(s"    switch(viewObject['_tp']) {")
    writer.println(s"      case 'viewRelationship': return (<ViewRelationshipWidget conceptInfo={viewObject.conceptInfo} {...this.props}/>)")
    writer.println(s"      case 'viewConnection': return (<ViewConnectionWidget {...this.props}/>)")
    writer.println(s"    }")
    writer.println(s"  }")
    writer.println(s"}")
    writer.println("")


    writer.flush()
    FileUtils.write(
      new java.io.File(s"client/src/components/view/edges/ViewEdgeWidget.js"),
      stream.toString,
      "UTF-8"
    )
  }

  private def writeLinkFile(
                             writer: PrintWriter,
                             rname: String,
                             base: String = "ModelLinkWidget",
                             header: PrintWriter => Unit = (_) => {},
                             body: PrintWriter => Unit = (_) => {}
                           ) = {
    val cname = StringUtils.capitalize(rname)

    writer.println("import React from 'react'")
    writer.println("import _ from 'lodash'")
    writer.println("")

    header(writer)


    writer.println(s"import { ${base} } from '../BaseLinkWidget'")
    writer.println("")

    writer.println(s"export const TYPE='${rname}';")
    writer.println("")

    writer.println(s"export class ${cname}Widget extends ${base} {")
    writer.println(s"  getClassName(link) { return '${rname}'; }")

    body(writer)

    writer.println(s"}")
    writer.println("")
  }
}
