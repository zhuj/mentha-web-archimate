import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.view._

import scala.collection.mutable
import scala.util._

object clientRjdGenerator {

  def main(args: Array[String]): Unit = {

    // writeLinks()
    // writeNodes()
    // writeNodesScss()

  }

  private def writeNodesScss(): Unit = {

    if (true) { return }

    import scala.reflect.runtime.{universe => ru}

    val runtimeMirror = ru.runtimeMirror(this.getClass.getClassLoader)

    import org.mentha.utils.archimate.model.nodes

    val stream = new StringBuilderWriter(4096)
    val writer = new PrintWriter(stream)


    val processed = mutable.Set[ru.ClassSymbol]()
    def process(classSymbol: ru.ClassSymbol, write: Boolean = true): Unit = if (!processed.contains(classSymbol)) {


      val symbols = classSymbol
        .baseClasses
        .collect { case c: ru.ClassSymbol if c != classSymbol => c }
        .filter { case c => c.toType <:< ru.typeOf[Element] || c.toType <:< ru.typeOf[Layer] }
        .foldLeft(List[ru.ClassSymbol]()) {
          (list, c) =>
            list.collectFirst { case e if e.toType <:< c.toType => true } match {
              case Some(true) => list
              case _ => c :: list
            }
        }

      for { s <- symbols } { process(s) }

      if (write) {
        processed += classSymbol
        writer.println(s".${StringUtils.uncapitalize(classSymbol.name.toString)} {")
        for {s <- symbols} {
          writer.println(s"  @extend .${StringUtils.uncapitalize(s.name.toString)};")
        }
        writer.println(s"}")
        writer.println("")
      }
    }

    // requirements
    for {nod <- nodes.allNodes} {
      process(runtimeMirror.classSymbol(nod.runtimeClass), write = false)
    }

    // nodes
    writer.println(".archimate-diagrams-canvas {")
    writer.println("")

    for {nod <- nodes.allNodes} {
      process(runtimeMirror.classSymbol(nod.runtimeClass), write = true)
    }

    writer.println("")
    writer.println("}")
    writer.flush()
    FileUtils.write(
      new java.io.File(s"client/src/components/rjd/nodes/nodes.scss"),
      stream.toString,
      "UTF-8"
    )

  }


  private def writeNodes(): Unit = {

    if (true) { return }

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
      writeNodeFile(writer, rname, s"model_${nod.prefix}", model = false)
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/rjd/nodes/model_${nod.prefix}/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

//    // ViewNotes
//    {
//      val stream = new StringBuilderWriter(4096)
//      val writer = new PrintWriter(stream)
//      val rname = StringUtils.uncapitalize(classOf[ViewNotes].getSimpleName)
//      writeNodeFile(writer, rname)
//      writer.flush()
//      FileUtils.write(
//        new java.io.File(s"client/src/components/rjd/nodes/view/${rname}.js"),
//        stream.toString,
//        "UTF-8"
//      )
//    }
//
//    // ViewNodeConcept
//    {
//      val stream = new StringBuilderWriter(4096)
//      val writer = new PrintWriter(stream)
//      val rname = StringUtils.uncapitalize(classOf[ViewNodeConcept[_]].getSimpleName)
//      writeNodeFile(writer, rname)
//      writer.flush()
//      FileUtils.write(
//        new java.io.File(s"client/src/components/rjd/nodes/view/${rname}.js"),
//        stream.toString,
//        "UTF-8"
//      )
//    }

//    val stream = new StringBuilderWriter(4096)
//    val writer = new PrintWriter(stream)
//
//    for {nod <- nodes.allNodes} {
//      val cname = StringUtils.capitalize(nod.name)
//      writer.println(s"import { register${cname}Node } from './model_${nod.prefix}/${nod.name}'")
//    }
//    writer.println("")
//
//    writer.println(s"import { registerViewNotesNode } from './view/viewNodeConcept'")
//    writer.println(s"import { registerViewNodeConceptNode } from './view/viewNodeConcept'")
//    writer.println("")
//
//    writer.println(s"export const registerNodes = (diagramEngine) => {")
//    for {nod <- nodes.allNodes} {
//      val cname = StringUtils.capitalize(nod.name)
//      writer.println(s" register${cname}Node(diagramEngine);")
//    }
//    writer.println(s" registerViewConnectionNode(diagramEngine);")
//    writer.println(s" registerViewRelationshipNode(diagramEngine);")
//    writer.println(s"}")
//    writer.println("")
//
//
//    writer.flush()
//    FileUtils.write(
//      new java.io.File(s"client/src/components/rjd/nodes/engine.js"),
//      stream.toString,
//      "UTF-8"
//    )

  }


  private def writeNodeFile(writer: PrintWriter, rname: String, level:String="", model: Boolean = true) = {
    val cname = StringUtils.capitalize(rname)

    writer.println("import React from 'react'")
    writer.println("import _ from 'lodash'")
    writer.println("")

    if (model) {
      writer.println("import { BaseNodeModel } from '../../base/BaseNodeModel'")
    }
    writer.println("import { BaseNodeWidget } from '../../base/BaseNodeWidget'")
    writer.println("import * as RJD from '../../rjd'")
    writer.println("")

    writer.println(s"export const TYPE='${rname}';")
    writer.println("")

    if (model) {
      writer.println(s"export class ${cname}NodeModel extends BaseNodeModel {")
      writer.println(s"  constructor(NodeType = TYPE) { super(NodeType); }")
      writer.println(s"  defaultNodeType() { return TYPE; }")
      writer.println(s"}")
      writer.println("")

      writer.println(s"export class ${cname}NodeInstanceFactory extends RJD.AbstractInstanceFactory {")
      writer.println(s"  constructor() { super(TYPE); }")
      writer.println(s"  getInstance() { return new ${cname}NodeModel(); }")
      writer.println(s"}")
      writer.println("")
    }

    writer.println(s"export class ${cname}NodeWidget extends BaseNodeWidget {")
    writer.println(s"  constructor(props) { super(props); }")
    writer.println(s"  getNodeClassName() { return 'a-node ${level} ' + this.props.node.nodeType; }")
    writer.println(s"}")
    writer.println("")

    writer.println(s"export class ${cname}NodeWidgetFactory extends RJD.NodeWidgetFactory {")
    writer.println(s"  constructor() { super(TYPE); }")
    writer.println(s"  generateReactWidget(diagramEngine, node) {")
    writer.println(s"    return (")
    writer.println(s"        <${cname}NodeWidget node={node} diagramEngine={diagramEngine} />")
    writer.println(s"      );")
    writer.println(s"  }")
    writer.println(s"}")
    writer.println("")

    writer.println(s"export const register${cname}Node = (diagramEngine) => {")
    writer.println(s"  diagramEngine.registerNodeFactory(new ${cname}NodeWidgetFactory());")
    if (model) {
      writer.println(s"  diagramEngine.registerInstanceFactory(new ${cname}NodeInstanceFactory());")
    }
    writer.println(s"}")
    writer.println("")
  }



  private def writeLinks(): Unit = {
    if (true) { return; }

    import org.mentha.utils.archimate.model.edges

    // edges
    for {rel <- edges.allRelations} {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = rel.name
      writeLinkFile(writer, rname, model = false)
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/rjd/edges/model/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // viewConnection
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewConnection].getSimpleName)
      writeLinkFile(writer, rname)
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/rjd/edges/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    // viewRelationship
    {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = StringUtils.uncapitalize(classOf[ViewRelationship[_]].getSimpleName)
      writeLinkFile(writer, rname)
      writer.flush()
      FileUtils.write(
        new java.io.File(s"client/src/components/rjd/edges/view/${rname}.js"),
        stream.toString,
        "UTF-8"
      )
    }

    val stream = new StringBuilderWriter(4096)
    val writer = new PrintWriter(stream)

    for {rel <- edges.allRelations} {
      val cname = StringUtils.capitalize(rel.name)
      writer.println(s"import { register${cname}Link } from './model/${rel.name}'")
    }
    writer.println("")

    writer.println(s"import { registerViewConnectionLink } from './view/viewConnection'")
    writer.println(s"import { registerViewRelationshipLink } from './view/viewRelationship'")
    writer.println("")

    writer.println(s"export const registerEdgeLinks = (diagramEngine) => {")
    for {rel <- edges.allRelations} {
      val cname = StringUtils.capitalize(rel.name)
      writer.println(s" register${cname}Link(diagramEngine);")
    }
    writer.println(s" registerViewConnectionLink(diagramEngine);")
    writer.println(s" registerViewRelationshipLink(diagramEngine);")
    writer.println(s"}")
    writer.println("")


    writer.flush()
    FileUtils.write(
      new java.io.File(s"client/src/components/rjd/edges/engine.js"),
      stream.toString,
      "UTF-8"
    )
  }

  private def writeLinkFile(writer: PrintWriter, rname: String, model: Boolean = true) = {
    val cname = StringUtils.capitalize(rname)

    writer.println("import React from 'react'")
    writer.println("import _ from 'lodash'")
    writer.println("")

    if (model) {
      writer.println("import { BaseLinkModel } from '../../base/BaseLinkModel'")
    }
    writer.println("import { BaseLinkWidget } from '../../base/BaseLinkWidget'")
    writer.println("import * as RJD from '../../rjd'")
    writer.println("")

    writer.println(s"export const TYPE='${rname}';")
    writer.println("")

    if (model) {
      writer.println(s"export class ${cname}LinkModel extends BaseLinkModel {")
      writer.println(s"  constructor(linkType = TYPE) { super(linkType); }")
      writer.println(s"  defaultLinkType() { return TYPE; }")
      writer.println(s"}")
      writer.println("")

      writer.println(s"export class ${cname}LinkInstanceFactory extends RJD.AbstractInstanceFactory {")
      writer.println(s"  constructor() { super(TYPE); }")
      writer.println(s"  getInstance() { return new ${cname}LinkModel(); }")
      writer.println(s"}")
      writer.println("")
    }

    writer.println(s"export class ${cname}LinkWidget extends BaseLinkWidget {")
    writer.println(s"  constructor(props) { super(props); }")
    writer.println(s"}")
    writer.println("")

    writer.println(s"export class ${cname}LinkWidgetFactory extends RJD.LinkWidgetFactory {")
    writer.println(s"  constructor() { super(TYPE); }")
    writer.println(s"  generateReactWidget(diagramEngine, link) {")
    writer.println(s"    return (")
    writer.println(s"        <${cname}LinkWidget link={link} diagramEngine={diagramEngine} />")
    writer.println(s"      );")
    writer.println(s"  }")
    writer.println(s"}")
    writer.println("")

    writer.println(s"export const register${cname}Link = (diagramEngine) => {")
    writer.println(s"  diagramEngine.registerLinkFactory(new ${cname}LinkWidgetFactory());")
    if (model) {
      writer.println(s"  diagramEngine.registerInstanceFactory(new ${cname}LinkInstanceFactory());")
    }
    writer.println(s"}")
    writer.println("")
  }
}
