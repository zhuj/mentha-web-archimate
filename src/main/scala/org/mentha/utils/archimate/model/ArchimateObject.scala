package org.mentha.utils.archimate.model

trait ArchimateObject {
}

trait IdentifiedArchimateObject extends ArchimateObject with Identifiable {

}

trait NamedArchimateObject extends ArchimateObject {

  private[model] var _name: String = ""
  @inline def name: String = _name

  def withName(name: String): this.type = {
    this._name = name
    this
  }

}

trait PropsArchimateObject extends ArchimateObject {

  private[model] var _properties: json.JsonObject = json.JsonObject.empty
  @inline def properties: json.JsonObject = _properties

  def withProperties(properties: json.JsonObject): this.type = {
    this._properties = properties
    this
  }

  def withProperty(name: String, value: json.JsonValue): this.type = {
    this._properties =
      if (null != value) { this._properties + (name -> value) }
      else { this._properties - name }
    this
  }

  def withProperty(name: String, value: String): this.type =
    withProperty(name, json.JsonString(value))

  def withProperty(name: String, value: Boolean): this.type =
    withProperty(name, json.JsonBoolean(value))

  def withProperty(name: String, value: scala.math.BigDecimal): this.type =
    withProperty(name, json.JsonNumber(value))

  def withProperty(name: String, value: Int): this.type =
    withProperty(name, BigDecimal(value))

  def withProperty(name: String, value: Long): this.type =
    withProperty(name, BigDecimal(value))

  def withProperty(name: String, value: Double): this.type =
    withProperty(name, BigDecimal(value))

}

/** Something which could be linked withing the edges */
trait Vertex {

}

/** Links two vertexes */
trait Edge[V <: Vertex] {
  def source: V
  def target: V
}