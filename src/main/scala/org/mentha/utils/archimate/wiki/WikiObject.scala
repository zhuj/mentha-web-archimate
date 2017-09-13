package org.mentha.utils.archimate.wiki

trait WikiOps {
  def commit: this.type
  def markAsDeleted: this.type
}

trait WikiTitled {

  private[wiki] var _title: String = ""
  def title: String = _title
  def withTitle(block: String=>String): this.type = { _title = block(_title); this }
  def withTitle(title: String): this.type = withTitle { _ => title }

}


/**
  * Base Wiki-stored object trait
  */
trait WikiObject extends WikiTitled with WikiOps {

  def id: String

  def wikiId: String
  def openUrl: String
  def viewUrl: String

  def path: Seq[String]
  def moveTo(id: String): this.type

  private[wiki] var _meta: Map[String, String] = Map()
  def meta: Map[String, String] = _meta
  def withMeta(key: String, value: String): this.type = { _meta += (key -> value); this }
  def withMeta(meta: Map[String, String]): this.type = { meta.foreach { case (k, v) => withMeta(k, v) }; this }

}


trait WikiPageContainerChildren {
  def all: Seq[WikiPage]
  def direct: Seq[WikiPage]
}

trait WikiPageContainer {
  def children: WikiPageContainerChildren
}

/**
  * Page (an object which has a text)
  */
trait WikiPage extends WikiObject with WikiPageContainer {
  def text: WikiText
  def withText(block: WikiText=>WikiText): this.type
}

/**
  * Space (an object which keeps a tree of pages)
  */
trait WikiSpace extends WikiObject with WikiPageContainer {
  def getPage(id: String, orCreate: Boolean = false): WikiPage
}

/**
  * Wiki (interface)
  */
trait Wiki {
  def getSpace(id: String, orCreate: Boolean = false): WikiSpace

  def emptyTextSection: WikiTextSection
  def textSection(value: String) = emptyTextSection.withValue(value)

}

object Wiki {


}
