package org.mentha.tools.archimate.wiki

/**
  * Abstraction over Wiki internal storage format
  */
trait WikiTextSection {

  // the main text of the section (looks like a caption if there is either header, body or footer)
  private[wiki] var _value: String = ""
  def value: String = _value
  def withValue(block: String=>String): this.type = { _value = block(_value); this }
  def withValue(value: String): this.type = withValue { _ => value }

  // a children part of the section, goes just after the main text
  private[wiki] var _header: Option[WikiTextSection] = None
  def header: Option[WikiTextSection] = _header
  def withHeader(block: Option[WikiTextSection]=>Option[WikiTextSection]): this.type = { _header = block(_header); this }
  def withHeader(header: WikiTextSection): this.type = withHeader { _ => Option(header) }

  // a children part of the section, goes just after the header
  private[wiki] var _body: Seq[WikiTextSection] = Nil
  def body: Seq[WikiTextSection] = _body
  def withBody(block: Seq[WikiTextSection]=>Seq[WikiTextSection]): this.type = { _body = block(_body); this }
  def withBody(body: Seq[WikiTextSection]): this.type = withBody { _ => body }

  // a children part of the section, goes just after the body
  private[wiki] var _footer: Option[WikiTextSection] = None
  def footer: Option[WikiTextSection] = _footer
  def withFooter(block: Option[WikiTextSection]=>Option[WikiTextSection]): this.type = { _footer = block(_footer); this }
  def withFooter(footer: WikiTextSection): this.type = withFooter { _ => Option(footer) }

}


trait WikiText extends WikiTextSection  {

}
