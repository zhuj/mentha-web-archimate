package org.mentha.utils.archimate.model

/**
  *
  */
trait ArchimateObject {

}

/**
  *
  */
trait IdentifiedArchimateObject extends ArchimateObject with Identifiable {

}

/**
  *
  */
trait NamedArchimateObject extends ArchimateObject {

  private[model] var _name: String = ""
  @inline def name: String = _name

  def withName(name: String): this.type = {
    this._name = name
    this
  }

}

/**
  *
  */
trait VersionedArchimateObject extends ArchimateObject {

  private[model] var _version: Long = -1L
  @inline def version: Long = _version

  def withVersion(version: Long): this.type = {
    this._version = version
    this
  }

}

/**
  *
  */
trait PathBasedArchimateObject extends ArchimateObject {

  private[model] var _path: List[String] = Nil
  @inline def path: List[String] = _path

  def withPath(path: List[String]): this.type = {
    this._path = path
    this
  }

}

/**
  *
  */
trait ValidArchimateObject extends ArchimateObject {
  def validationErrors: List[String] = Nil
  @inline def valid: Boolean = validationErrors.isEmpty
  @inline @throws[IllegalStateException] def validate: this.type = {
    val errors = validationErrors
    if (errors.nonEmpty) {
      throw new IllegalStateException(s"${this} is not valid: ${errors.mkString(", ")}")
    }
    this
  }
}


/**
  *
  */
trait ArchimateObjectExtension extends Product {

}

/**
  *
  */
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

   import scala.reflect.ClassTag
   def extension[E <: ArchimateObjectExtension: ClassTag](implicit fjs: json.JsonReader[E]): Option[E] = properties
     .value.get(implicitly[ClassTag[E]].runtimeClass.getName)
     .map { v => v.as[E](fjs) }

   def withExtension[E <: ArchimateObjectExtension: ClassTag](e: E)(implicit tjs: json.JsonWriter[E]): this.type =
      withProperty(implicitly[ClassTag[E]].runtimeClass.getName, json.toJson(e)(tjs))

}

/**
  * Something which could be linked withing the edges
  */
trait Vertex {

}

/**
  * Links two vertexes
  */
trait Edge[+V <: Vertex] {
  def source: V
  def target: V
}