import java.io.PrintWriter

import org.apache.commons.io.FileUtils
import org.apache.commons.io.output.StringBuilderWriter
import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.view.{ViewConnection, ViewRelationship}

object clientRjdGenerator {

  def main(args: Array[String]): Unit = {

    if (true) { return }

    import org.mentha.utils.archimate.model.edges

    // edges
    for { rel <- edges.allRelations } {
      val stream = new StringBuilderWriter(4096)
      val writer = new PrintWriter(stream)
      val rname = rel.name
      writeLinkFile(writer, rname)
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

    for { rel <- edges.allRelations } {
      val cname = StringUtils.capitalize(rel.name)
      writer.println(s"import { register${cname}Link } from './model/${rel.name}'")
    }
    writer.println("")

    writer.println(s"import { registerViewConnectionLink } from './view/viewConnection'")
    writer.println(s"import { registerViewRelationshipLink } from './view/viewRelationship'")
    writer.println("")

    writer.println(s"export const registerEdgeLinks = (diagramEngine) => {")
    for { rel <- edges.allRelations } {
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

  private def writeLinkFile(writer: PrintWriter, rname: String) = {
    val cname = StringUtils.capitalize(rname)

    writer.println("import React from 'react'")
    writer.println("import _ from 'lodash'")
    writer.println("")

    writer.println("import { BaseLinkModel } from '../../base/BaseLinkModel'")
    writer.println("import { BaseLinkWidget } from '../../base/BaseLinkWidget'")
    writer.println("import * as RJD from '../../rjd'")
    writer.println("")

    writer.println(s"export const TYPE='${rname}';")
    writer.println(s"export class ${cname}LinkModel extends BaseLinkModel {")
    writer.println(s"  constructor(linkType = TYPE) { super(linkType); }")
    writer.println(s"  getLinkType() { return TYPE; }")
    writer.println(s"}")
    writer.println("")

    writer.println(s"export class ${cname}LinkInstanceFactory extends RJD.AbstractInstanceFactory {")
    writer.println(s"  constructor() { super(TYPE); }")
    writer.println(s"  getInstance() { return new ${cname}LinkModel(); }")
    writer.println(s"}")
    writer.println("")

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
    writer.println(s"  diagramEngine.registerInstanceFactory(new ${cname}LinkInstanceFactory());")
    writer.println(s"}")
    writer.println("")
  }
}
