package org.mentha.utils.archimate.model

import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.edges.impl.{AccessRelationship, AssociationRelationship, FlowRelationship, InfluenceRelationship}
import org.mentha.utils.archimate.model.hash.Hash
import org.mentha.utils.archimate.model.view._

import scala.util.control.NonFatal

package object json {

  import play.api.libs.json._
  import play.api.libs.json.Json.JsValueWrapper

  type JsonReader[A] = Reads[A]
  type JsonWriter[A] = Writes[A]

  type JsonValue = play.api.libs.json.JsValue

  type JsonObject = play.api.libs.json.JsObject
  val JsonObject = play.api.libs.json.JsObject

  type JsonString = play.api.libs.json.JsString
  val JsonString = play.api.libs.json.JsString

  type JsonNumber = play.api.libs.json.JsNumber
  val JsonNumber = play.api.libs.json.JsNumber

  type JsonBoolean = play.api.libs.json.JsBoolean
  val JsonBoolean = play.api.libs.json.JsBoolean

  type JsonArray = play.api.libs.json.JsArray
  val JsonArray = play.api.libs.json.JsArray

  def toJson[T](o: T)(implicit tjs: JsonWriter[T]): JsonValue = Json.toJson(o)(tjs)

  private[json] object hash extends Hash {

    @inline def _bool(b: JsonBoolean): Int = _bool(b.value)
    @inline def _str(s: JsonString): Int = _str(s.value)
    @inline def _num(n: JsonNumber): Int = _num(n.value)

    def _obj(o: JsonObject): Int = _obj(o.fields:_*)
    def _arr(a: JsonArray): Int = _arr(a.value)

    override def hash(v: Any): Int = v match {
      case o: JsonObject => _obj(o)
      case a: JsonArray => _arr(a)
      case s: JsonString => _str(s)
      case n: JsonNumber => _num(n)
      case b: JsonBoolean => _bool(b)
      case play.api.libs.json.JsNull => 0
      case _ => super.hash(v)
    }

  }

  private[json] def concept(id: Identifiable.ID)(implicit model: Model): Concept = model._concepts[Concept](id)
  private[json] def edge(id: Identifiable.ID)(implicit model: Model): Relationship = model._concepts[Relationship](id)
  private[json] def node(id: Identifiable.ID)(implicit model: Model): NodeConcept = model._concepts[NodeConcept](id)

  private[json] def viewConcept(id: Identifiable.ID)(implicit view: View): ViewObject with ViewConcept[_] = view._objects[ViewObject with ViewConcept[_]](id)
  private[json] def viewObject(id: Identifiable.ID)(implicit view: View): ViewObject = view._objects[ViewObject](id)

  private[json] def tp(o: Any): String = o match {
    case c: Concept => c.meta.name
    case _ => StringUtils.uncapitalize(o.getClass.getSimpleName)
  }

  object names {

    val `tp` = "_tp"
    val `name` = "name"
    val `desc` = "desc"
    val `props` = "props"

    val `rel` = "rel"
    val `src` = "src"
    val `dst` = "dst"
    val `path` = "path"
    val `version` = "version"
    val `viewpoint` = "viewpoint"

    val `nodes` = "nodes"
    val `edges` = "edges"
    val `views` = "views"
    
    val `concept` = "concept"
    val `pos` = "pos"
    val `size` = "size"
    val `points` = "points"
    
    val `deleted` = "deleted"
    val `invalid` = "invalid"
    val `derived` = "derived"
  }

  implicit val pointWrites: Writes[Vector] = new Writes[Vector] {
    override def writes(o: Vector): JsonValue = Json.obj(
      "x" -> Math.floor(o.x),
      "y" -> Math.floor(o.y)
    )
  }
  implicit val pointReads: Reads[Vector] = Json.reads[Vector]

  def readPoint(json: JsonObject): Vector = json.as[Vector]
  def readPoints(json: JsonArray): List[Vector] = json.as[List[Vector]]

  implicit val sizeWrites: Writes[Size] = Json.writes[Size]
  implicit val sizeReads: Reads[Size] = Json.reads[Size]

  def readSize(json: JsonObject): Size = json.as[Size]

  implicit val accessTypeRW = new Reads[AccessType] with Writes[AccessType] {
    override def writes(o: AccessType): JsValue = o match {
      case ReadAccess => JsString("r")
      case WriteAccess => JsString("w")
      case ReadWriteAccess => JsString("rw")
    }
    override def reads(json: JsValue): JsResult[AccessType] = json.as[String] match {
      case "r" => JsSuccess(ReadAccess)
      case "w" => JsSuccess(WriteAccess)
      case "rw" => JsSuccess(ReadWriteAccess)
      case x => JsError(s"Unexpected access type: ${x}")
    }
  }

  private[json] def fillArchimateObject[T<:ArchimateObject](obj: T, json: JsValue): T = {

    (json \ names.`tp`).validate[String].foreach { t => require(tp(obj) == t) }

    obj match {
      case n: NamedArchimateObject =>
        (json \ names.`name`).validate[String].foreach { name => n.withName(name) }
      case _ =>
    }
    obj match {
      case n: DescribedArchimateObject =>
        (json \ names.`desc`).validate[String].foreach { name => n.withDescription(name) }
      case _ =>
    }
    obj match {
      case v: VersionedArchimateObject =>
        (json \ names.`version`).validate[Long].foreach { version => v.withVersion(version) }
      case _ =>
    }
    obj match {
      case p: PathBasedArchimateObject =>
        (json \ names.`path`).validate[List[String]].foreach { path => p.withPath(path) }
      case _ =>
    }
    obj match {
      case p: PropsArchimateObject =>
        (json \ names.`props`).validate[JsonObject].foreach { props => p.withProperties(props) }
      case _ =>
    }

    obj
  }

  private[json] def writeArchimateObject(obj: ArchimateObject, fields: (String, JsValueWrapper)*): JsonObject = {

    val builder = Seq.newBuilder[(String, JsValueWrapper)]

    builder += (names.`tp` -> tp(obj))

    Some(obj)
      .collect { case i: IdentifiedArchimateObject if i.isDeleted => true }
      .foreach { deleted => builder += (names.`deleted` -> deleted) }

    Some(obj)
      .collect { case n: NamedArchimateObject => n.name }
      .filterNot { _.isEmpty }
      .foreach { name => builder += (names.`name` -> name) }

    Some(obj)
      .collect { case n: DescribedArchimateObject => n.description }
      .filterNot { _.isEmpty }
      .foreach { name => builder += (names.`desc` -> name) }

    Some(obj)
      .collect { case v: VersionedArchimateObject => v.version }
      .filter { _ > 0 }
      .foreach { version => builder += (names.`version` -> version) }

    Some(obj)
      .collect { case p: PathBasedArchimateObject => p.path }
      .filter { _.nonEmpty }
      .foreach { path => builder += (names.`path` -> path) }

    Some(obj)
      .collect { case p: PropsArchimateObject => p.properties }
      .filter { _.keys.nonEmpty }
      .foreach { properties => builder += (names.`props` -> properties) }

    Some(obj)
      .collect { case v: ValidArchimateObject => v.validationErrors }
      .filter { _.nonEmpty }
      .foreach { errors => builder += (names.`invalid` -> errors) }


    builder ++= fields.filter { _ != null }

    Json.obj(builder.result():_*)
  }

  private[json] implicit def storageWrites[T <: Identifiable](implicit w: Writes[T]): Writes[Iterable[T]] = {
    Writes[Iterable[T]] { ts => JsonObject( ts .filterNot { v => v.isDeleted } .map { v => (v.id -> w.writes(v)) } .toSeq ) }
  }

  @inline
  private[json] def fields(js: JsLookupResult): Seq[(String, JsValue)] = {
    fields(js.getOrElse(JsonObject.empty))
  }

  @inline
  private[json] def fields(js: JsValue): Seq[(String, JsValue)] = js match {
    case o: JsonObject => o.fields
    case _ => Seq.empty
  }

  def fillElement(el: Element, json: JsValue): Element =
    fillArchimateObject(el, json)

  def readElement(tp: String, json: JsValue): Element = nodes
    .mapElements.get(tp)
    .map { meta => fillElement(meta.newInstance(), json) }
    .getOrElse( throw new IllegalStateException(s"Unexpected element type: ${tp}") )

  implicit val elementRW = new Reads[Element] with Writes[Element] {

    override def reads(json: JsValue): JsResult[Element] = try {
      JsSuccess(readElement( (json \ names.`tp`).as[String], json))
    } catch {
      case NonFatal(e) => JsError(e.getMessage)
    }

    override def writes(o: Element): JsValue = writeArchimateObject(
      o
    )

  }

  def fillRelationshipConnector(rc: RelationshipConnector, json: JsValue): RelationshipConnector =
    fillArchimateObject(rc, json)

  def readRelationshipConnector(tp: String, rel: String, json: JsValue): RelationshipConnector = nodes
    .mapRelationshipConnectors.get(tp)
    .map { meta => fillRelationshipConnector(meta.newInstance( edges.mapRelations(rel) ), json ) }
    .getOrElse( throw new IllegalStateException(s"Unexpected relationship connector type: ${tp}") )

  implicit val relationshipConnectorRW = new Reads[RelationshipConnector] with Writes[RelationshipConnector] {

    override def reads(json: JsValue): JsResult[RelationshipConnector] = try {
      JsSuccess(readRelationshipConnector( (json \ names.`tp`).as[String], (json \ names.`rel`).as[String], json))
    } catch {
      case NonFatal(e) => JsError(e.getMessage)
    }

    override def writes(o: RelationshipConnector): JsValue = writeArchimateObject(
      o,
      names.`rel` -> o.relationship.name
    )

  }

  implicit val nodeConceptRW = new Reads[NodeConcept] with Writes[NodeConcept] {

    override def reads(json: JsValue): JsResult[NodeConcept] =
      ConceptMeta.byName((json \ names.`tp`).as[String])
        .map {
          case e: ElementMeta[_] => elementRW.reads(json)
          case c: RelationshipConnectorMeta[_] => relationshipConnectorRW.reads(json)
        }
        .getOrElse( JsError(s"Unexpected node type: ${(json \ names.`tp`)}") )

    override def writes(o: NodeConcept): JsValue = o match {
      case e: Element => elementRW.writes(e)
      case c: RelationshipConnector => relationshipConnectorRW.writes(c)
    }

  }

  implicit val edgeConceptW = new Writes[EdgeConcept] {

    val jsTrueWrapper: JsValueWrapper = true
    override def writes(o: EdgeConcept): JsValue = writeArchimateObject(
      o,
      names.`src` -> o.source.id,
      names.`dst` -> o.target.id,
      Some(o).collect { case r: Relationship if r.derivation.derived => names.`derived` -> jsTrueWrapper }.orNull
    ) ++ {
      o match {
        case a: AccessRelationship if a.access != null => Json.obj("access" -> a.access)
        case i: InfluenceRelationship if i.influence != null => Json.obj("influence" -> i.influence)
        case f: FlowRelationship if f.flow != null => Json.obj("flows" -> f.flow)
        case f: AssociationRelationship if f.predicate != null => Json.obj("predicate" -> f.predicate)
        case _ => JsonObject.empty
      }
    }
  }

  def fillRelationship(rel: Relationship, json: JsValue): Relationship = fillArchimateObject(rel, json) match {
    case a: AccessRelationship => (json \ "access").validate[AccessType].foreach { a.withAccess }; a
    case i: InfluenceRelationship => (json \ "influence").validate[String].foreach { i.withInfluence }; i
    case f: FlowRelationship => (json \ "flows").validate[String].foreach { f.withFlow }; f
    case f: AssociationRelationship => (json \ "predicate").validate[String].foreach { f.withPredicate }; f
    case r => r
  }

  def readRelationship(tp: String, source: Concept, target: Concept, json: JsValue): Relationship = edges
    .mapRelations.get(tp)
    .map { meta => fillRelationship(meta.newInstance(source, target), json) }
    .getOrElse( throw new IllegalThreadStateException(s"Unexpected edge type: ${tp}") )

  implicit def edgeConceptR(implicit model: Model): Reads[EdgeConcept] = new Reads[EdgeConcept] {

    override def reads(json: JsValue): JsResult[EdgeConcept] = try {
      JsSuccess {
        // TODO: do something with relations in src and dst
        val source = concept((json \ names.`src`).as[String])
        val target = concept((json \ names.`dst`).as[String])
        readRelationship((json \ names.`tp`).as[String], source, target, json)
      }
    } catch {
      case NonFatal(e) => JsError(e.getMessage)
    }

  }

  implicit val viewObjectWrites = new Writes[ViewObject] {
    override def writes(o: ViewObject): JsValue = {
      o match {
        case e: ViewNodeConcept[_] => writeArchimateObject(
          o,
          names.`concept` -> e.concept.id,
          names.`pos` -> o.position,
          names.`size` -> e.size
        )
        case r: ViewRelationship[_] => writeArchimateObject(
          o,
          names.`concept` -> r.concept.id,
          names.`src` -> r.source.id,
          names.`dst` -> r.target.id,
          names.`points` -> r.points
        )
        case e: ViewNotes => writeArchimateObject(
          o,
          "text" -> e.text,
          names.`pos` -> o.position,
          names.`size` -> e.size
        )
        case c: ViewConnection => writeArchimateObject(
          o,
          names.`src` -> c.source.id,
          names.`dst` -> c.target.id,
          names.`points` -> c.points
        )
        case g: ViewGroup => writeArchimateObject(
          o,
          names.`pos` -> o.position,
          names.`size` -> g.size
        )
      }
    }
  }

  private[json] def fillPosAndSize[T <: ViewNode](res: T, json: JsonValue): T = {
    (json \ names.`pos`).validate[Vector].foreach { pt => res.withPosition(pt) }
    (json \ names.`size`).validate[Size].foreach { sz => res.withSize(sz) }
    res
  }

  private[json] def fillPoints[T <: ViewEdge](res: T, json: JsonValue): T = {
    (json \ names.`points`).validate[Seq[Vector]].foreach { pts => res.withPoints(pts) }
    res
  }

  def fillViewNodeConcept[T<:NodeConcept](vnc: ViewNodeConcept[T], json: JsValue): ViewNodeConcept[T] = {
    val res = fillArchimateObject(vnc, json)
    fillPosAndSize(res, json)
  }

  def readViewNodeConcept[T<:NodeConcept](concept: T, json: JsValue): ViewNodeConcept[T] = {
    fillViewNodeConcept(new ViewNodeConcept[T](concept), json)
  }

  def fillViewRelationship[T<:Relationship](vrs: ViewRelationship[T], json: JsValue): ViewRelationship[T] = {
    val res = fillArchimateObject(vrs, json)
    fillPoints(res, json)
  }

  def readViewRelationship[T<:Relationship](source: ViewObject with ViewConcept[_], target: ViewObject with ViewConcept[_], concept: T, json: JsValue): ViewRelationship[T] = {
    fillViewRelationship(new ViewRelationship[T](source, target)(concept), json)
  }

  def fillViewNotes(vn: ViewNotes, json: JsValue): ViewNotes = {
    val res = fillArchimateObject(vn, json)
    (json \ "text").validate[String].foreach { res.withText }
    fillPosAndSize(res, json)
  }

  def readViewNotes(json: JsValue): ViewNotes = {
    fillViewNotes(new ViewNotes, json)
  }

  def fillViewConnection(vc: ViewConnection, json: JsValue): ViewConnection = {
    val res = fillArchimateObject(vc, json)
    fillPoints(res, json)
  }

  def readViewConnection(source: ViewObject, target: ViewObject, json: JsValue): ViewConnection = {
    fillViewConnection(new ViewConnection(source, target), json)
  }

  def fillViewGroup(vn: ViewGroup, json: JsValue): ViewGroup = {
    val res = fillArchimateObject(vn, json)
    fillPosAndSize(res, json)
  }

  def readViewGroup(json: JsValue): ViewGroup = {
    fillViewGroup(new ViewGroup, json)
  }

  implicit def viewObjectReads(implicit model: Model, view: View) = new Reads[ViewObject] {
    override def reads(json: JsValue): JsResult[ViewObject] = (json \ names.`tp`).as[String] match {
      case "viewNodeConcept" => {
        val concept = node((json \ names.`concept`).as[String])
        JsSuccess(readViewNodeConcept(concept, json))
      }
      case "viewRelationship" => {
        val concept = edge((json \ names.`concept`).as[String])
        val source = viewConcept((json \ names.`src`).as[String])
        val target = viewConcept((json \ names.`dst`).as[String])
        JsSuccess(readViewRelationship(source, target, concept, json))
      }
      case "viewNotes" => {
        JsSuccess(readViewNotes(json))
      }
      case "viewConnection" => {
        val source = viewObject((json \ names.`src`).as[String])
        val target = viewObject((json \ names.`dst`).as[String])
        JsSuccess(readViewConnection(source, target, json))
      }
      case "viewGroup" => {
        JsSuccess(readViewGroup(json))
      }
    }
  }

  private[json] def fillViewChildren(vv: View, json: JsValue)(implicit model: Model): View = {
    implicit val view: View = vv
    fields(json \ names.`nodes`).foreach { case (id, v) => view._objects store(v.as[ViewObject], id) }
    fields(json \ names.`edges`).foreach { case (id, v) => view._objects.store(v.as[ViewObject], id) }
    view
  }

  def fillView(view: View, json: JsValue): View =
    fillArchimateObject(new View(), json)

  def readView(viewpoint: String, json: JsValue): View =
    fillView(new View(
      ViewPoints.mapViewPoints.getOrElse(viewpoint, throw new IllegalStateException(s"Unexpected viewpoint: ${viewpoint}"))
    ), json)

  implicit def viewReads(implicit model: Model): Reads[View] = new Reads[View] {
    override def reads(json: JsValue): JsResult[View] = {
      JsSuccess(fillViewChildren(readView((json \ names.`viewpoint`).as[String], json), json))
    }
  }

  implicit object ViewWrites extends Writes[View] {
    override def writes(o: View): JsValue = writeArchimateObject(
      o,
      names.`viewpoint` -> o.viewpoint.name,
      names.`nodes` -> Json.toJson(o.nodes),
      names.`edges` -> Json.toJson(o.edges)
    )
  }

  implicit object ModelRW extends Reads[Model] with Writes[Model] {
    
    override def reads(json: JsValue): JsResult[Model] = {
      implicit val model = fillArchimateObject(new Model(), json)
      fields(json \ names.`nodes`).foreach { case (id, v) => model._concepts.store(v.as[NodeConcept], id) }
      fields(json \ names.`edges`).foreach { case (id, v) => model._concepts.store(v.as[EdgeConcept], id) }
      fields(json \ names.`views`).foreach { case (id, v) => model._views.store(v.as[View], id) }
      JsSuccess(model)
    }

    override def writes(o: Model): JsValue = writeArchimateObject(
      o,
      names.`nodes` -> Json.toJson(o.nodes),
      names.`edges` -> Json.toJson(o.edges),
      names.`views` -> Json.toJson(o.views)
    )

  }

  def toJsonPair(obj: ArchimateObject, id: Identifiable.ID => String = id => id): JsonObject = obj match {
    case m: Model => Json.obj(id(m.id) -> m)
    case n: NodeConcept => Json.obj(id(n.id) -> n)
    case r: Relationship => Json.obj(id(r.id) -> r)
    case v: View => Json.obj(id(v.id) -> v)
    case o: ViewObject => Json.obj(id(o.id) -> o)
  }

  def fromJsonPair(json: JsonValue): Model =
    fields(json).collectFirst { case (id, v) => v.as[Model].withId(id) }.get

  def fromJsonString(jsonString: String): Model =
    fromJsonPair(Json.parse(jsonString))

  def toJsonString(model: Model): String =
    toJsonPair(model).toString()

}
