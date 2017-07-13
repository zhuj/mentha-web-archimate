package org.mentha.utils.archimate.model

import org.apache.commons.lang3.StringUtils
import org.mentha.utils.archimate.model.nodes._
import org.mentha.utils.archimate.model.edges._
import org.mentha.utils.archimate.model.view._
import play.api.libs.json.Json.JsValueWrapper

import scala.util.control.NonFatal

package object json {

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

  private[json] def concept(id: Identifiable.ID)(implicit model: Model): Concept = model.concepts[Concept](id)
  private[json] def edge(id: Identifiable.ID)(implicit model: Model): Relationship = model.concepts[Relationship](id)
  private[json] def node(id: Identifiable.ID)(implicit model: Model): NodeConcept = model.concepts[NodeConcept](id)

  private[json] def viewConcept(id: Identifiable.ID)(implicit view: View): ViewObject with ViewConcept = view.objects[ViewObject with ViewConcept](id)
  private[json] def viewObject(id: Identifiable.ID)(implicit view: View): ViewObject = view.objects[ViewObject](id)

  private[json] def tp(o: Any): String = o match {
    case c: Concept => c.meta.name
    case _ => StringUtils.uncapitalize(o.getClass.getSimpleName)
  }

  val `tp` = "_tp"
  val `name` = "name"
  val `props` = "props"
  val `rel` = "rel"
  val `src` = "src"
  val `dst` = "dst"
  val `viewpoint` = "viewpoint"


  import play.api.libs.json._
  import play.api.libs.json.Reads
  import play.api.libs.json.Writes

  implicit val pointWrites: Writes[Point] = Json.writes[Point]
  implicit val pointReads: Reads[Point] = Json.reads[Point]

  implicit val sizeWrites: Writes[Size] = Json.writes[Size]
  implicit val sizeReads: Reads[Size] = Json.reads[Size]

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

    (json \ `tp`).validate[String].foreach { t => require(tp(obj) == t) }

    obj match {
      case n: NamedArchimateObject =>
        (json \ `name`).validate[String].foreach { name => n.withName(name) }
      case _ =>
    }
    obj match {
      case p: PropsArchimateObject =>
        (json \ `props`).validate[JsObject].foreach { props => p.withProperties(props) }
      case _ =>
    }

    obj
  }

  private[json] def writeArchimateObject(obj: ArchimateObject, fields: (String, JsValueWrapper)*): JsObject = {

    val builder = Seq.newBuilder[(String, JsValueWrapper)]

    builder += (`tp` -> tp(obj))

    obj match {
      case i: IdentifiedArchimateObject if i.isDeleted => builder += ("deleted" -> true)
      case _ =>
    }
    obj match {
      case n: NamedArchimateObject if n.name.nonEmpty => builder += (`name` -> n.name)
      case _ =>
    }
    obj match {
      case p: PropsArchimateObject if p.properties.value.nonEmpty => builder += (`props` -> p.properties)
      case _ =>
    }

    builder ++= fields

    Json.obj(builder.result():_*)
  }

  private[json] implicit def storageWrites[T <: Identifiable](implicit w: Writes[T]): Writes[Iterable[T]] = {
    Writes[Iterable[T]] { ts => JsObject( ts .filterNot { v => v.isDeleted } .map { v => (v.id -> w.writes(v)) } .toSeq ) }
  }

  @inline
  private[json] def fields(js: JsLookupResult): Seq[(String, JsValue)] = {
    fields(js.getOrElse(JsObject.empty))
  }

  @inline
  private[json] def fields(js: JsValue): Seq[(String, JsValue)] = js match {
    case o: JsObject => o.fields
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
      JsSuccess(readElement( (json \ `tp`).as[String], json))
    } catch {
      case NonFatal(e) => JsError(e.getMessage)
    }

    override def writes(o: Element): JsValue = writeArchimateObject(o)

  }

  def fillRelationshipConnector(rc: RelationshipConnector, json: JsValue): RelationshipConnector =
    fillArchimateObject(rc, json)

  def readRelationshipConnector(tp: String, rel: String, json: JsValue): RelationshipConnector = nodes
    .mapRelationshipConnectors.get(tp)
    .map { meta => fillRelationshipConnector(meta.newInstance( edges.mapRelations(rel) ), json ) }
    .getOrElse( throw new IllegalStateException(s"Unexpected relationship connector type: ${tp}") )

  implicit val relationshipConnectorRW = new Reads[RelationshipConnector] with Writes[RelationshipConnector] {

    override def reads(json: JsValue): JsResult[RelationshipConnector] = try {
      JsSuccess(readRelationshipConnector( (json \ `tp`).as[String], (json \ `rel`).as[String], json))
    } catch {
      case NonFatal(e) => JsError(e.getMessage)
    }

    override def writes(o: RelationshipConnector): JsValue = writeArchimateObject(
      o,
      `rel` -> o.relationship.name
    )

  }

  implicit val nodeConceptRW = new Reads[NodeConcept] with Writes[NodeConcept] {

    override def reads(json: JsValue): JsResult[NodeConcept] =
      ConceptMeta.byName((json \ `tp`).as[String])
        .map {
          case e: ElementMeta[_] => elementRW.reads(json)
          case c: RelationshipConnectorMeta[_] => relationshipConnectorRW.reads(json)
        }
        .getOrElse( JsError(s"Unexpected node type: ${(json \ `tp`)}") )

    override def writes(o: NodeConcept): JsValue = o match {
      case e: Element => elementRW.writes(e)
      case c: RelationshipConnector => relationshipConnectorRW.writes(c)
    }

  }

  implicit val edgeConceptW = new Writes[EdgeConcept] {

    override def writes(o: EdgeConcept): JsValue = writeArchimateObject(
      o,
      `src` -> o.source.id,
      `dst` -> o.target.id
    ) ++ {
      o match {
        case a: AccessRelationship => Json.obj("access" -> a.access)
        case i: InfluenceRelationship => Json.obj("influence" -> i.influence)
        case f: FlowRelationship => Json.obj("flows" -> f.what)
        case _ => JsObject.empty
      }
    }

  }

  def fillRelationship(rel: Relationship, json: JsValue): Relationship = fillArchimateObject(rel, json) match {
    case a: AccessRelationship => a.withAccess((json \ "access").as[AccessType])
    case i: InfluenceRelationship => i.withInfluence((json \ "influences").as[String])
    case f: FlowRelationship => f.withFlows((json \ "flows").as[String])
    case r => r
  }

  def readRelationship(tp: String, source: Concept, target: Concept, json: JsValue): Relationship = edges
    .mapRelations.get(tp)
    .map { meta => fillRelationship(meta.newInstance(source, target), json) }
    .getOrElse( throw new IllegalThreadStateException(s"Unexpected edge type: ${tp}") )

  implicit def edgeConceptR(implicit model: Model): Reads[EdgeConcept] = new Reads[EdgeConcept] {

    override def reads(json: JsValue): JsResult[EdgeConcept] = try {
      JsSuccess {
        // TODO: do something with relations as src and dst
        val source = concept((json \ `src`).as[String])
        val target = concept((json \ `dst`).as[String])
        readRelationship((json \ `tp`).as[String], source, target, json)
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
          "concept" -> e.concept.id,
          "pos" -> o.position,
          "size" -> e.size
        )
        case r: ViewRelationship[_] => writeArchimateObject(
          o,
          "concept" -> r.concept.id,
          `src` -> r.source.id,
          `dst` -> r.target.id,
          "points" -> r.points
        )
        case e: ViewNotes => writeArchimateObject(
          o,
          "text" -> e.text,
          "pos" -> o.position,
          "size" -> e.size
        )
        case c: ViewConnection => writeArchimateObject(
          o,
          `src` -> c.source.id,
          `dst` -> c.target.id,
          "points" -> c.points
        )
      }
    }
  }

  private[json] def fillPosAndSize[T <: ViewNode](res: T, json: JsonValue): T = {
    (json \ "pos").validate[Point].foreach { pt => res.withPosition(pt) }
    (json \ "size").validate[Size].foreach { sz => res.withSize(sz) }
    res
  }

  private[json] def fillPoints[T <: ViewEdge](res: T, json: JsonValue): T = {
    (json \ "points").validate[Seq[Point]].foreach { pts => res.withPoints(pts) }
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

  def readViewRelationship[T<:Relationship](source: ViewObject with ViewConcept, target: ViewObject with ViewConcept, concept: T, json: JsValue): ViewRelationship[T] = {
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

  implicit def viewObjectReads(implicit model: Model, view: View) = new Reads[ViewObject] {
    override def reads(json: JsValue): JsResult[ViewObject] = (json \ `tp`).as[String] match {
      case "viewNodeConcept" => {
        val concept = node((json \ "concept").as[String])
        JsSuccess(readViewNodeConcept(concept, json))
      }
      case "viewRelationship" => {
        val concept = edge((json \ "concept").as[String])
        val source = viewConcept((json \ `src`).as[String])
        val target = viewConcept((json \ `dst`).as[String])
        JsSuccess(readViewRelationship(source, target, concept, json))
      }
      case "viewNotes" => {
        JsSuccess(readViewNotes(json))
      }
      case "viewConnection" => {
        val source = viewObject((json \ `src`).as[String])
        val target = viewObject((json \ `dst`).as[String])
        JsSuccess(readViewConnection(source, target, json))
      }
    }
  }

  private[json] def fillViewChildren(vv: View, json: JsValue)(implicit model: Model): View = {
    implicit val view: View = vv
    fields(json \ "nodes").foreach { case (id, v) => view.objects store(v.as[ViewObject], id) }
    fields(json \ "edges").foreach { case (id, v) => view.objects.store(v.as[ViewObject], id) }
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
      JsSuccess(fillViewChildren(readView((json \ `viewpoint`).as[String], json), json))
    }
  }

  implicit object ViewWrites extends Writes[View] {
    override def writes(o: View): JsValue = writeArchimateObject(
      o,
      `viewpoint` -> o.viewpoint.name,
      "nodes" -> Json.toJson(o.nodes),
      "edges" -> Json.toJson(o.edges)
    )
  }

  private[json] def fillFolderChildren(folder: Folder, json: JsValue)(implicit model: Model): Folder = {
    fields(json \ "children").foreach { case (id, v) => folder.children.store(v.as[Folder], id) }
    fields(json \ "views").foreach { case (id, v) => folder.views.store(v.as[View], id) }
    folder
  }

  def fillFolder(folder: Folder, json: JsValue): Folder =
    fillArchimateObject(folder, json)

  def readFolder(json: JsValue): Folder = {
    fillFolder(new Folder, json)
  }

  implicit def folderReads(implicit model: Model): Reads[Folder] = new Reads[Folder] {
    override def reads(json: JsValue): JsResult[Folder] = {
      JsSuccess(fillFolderChildren(readFolder(json), json))
    }
  }

  implicit object FolderWrites extends Writes[Folder] {

    override def writes(o: Folder): JsValue = writeArchimateObject(
      o,
      "children" -> Json.toJson(o.children.values),
      "views" -> Json.toJson(o.views.values)
    )

  }

  implicit object ModelRW extends Reads[Model] with Writes[Model] {

    override def reads(json: JsValue): JsResult[Model] = {
      implicit val model = fillArchimateObject(new Model(), json)
      fields(json \ "nodes").foreach { case (id, v) => model.concepts.store(v.as[NodeConcept], id) }
      fields(json \ "edges").foreach { case (id, v) => model.concepts.store(v.as[EdgeConcept], id) }
      fillFolderChildren(model.root, (json \ "root").as[JsValue])
      JsSuccess(model)
    }

    override def writes(o: Model): JsValue = writeArchimateObject(
      o,
      "nodes" -> Json.toJson(o.nodes),
      "edges" -> Json.toJson(o.edges),
      "root" -> Json.toJson(o.root)
    )

  }

  def toJsonPair(vo: ViewObject, deep: Boolean): JsObject =
    Json.obj(vo.id -> vo)

  def toJsonPair(view: View, deep: Boolean): JsObject =
    Json.obj(view.id -> view)

  def toJsonPair(folder: Folder, deep: Boolean): JsObject =
    Json.obj(folder.id -> folder)

  def toJsonPair(concept: Concept, deep: Boolean): JsObject = concept match {
    case n: NodeConcept => Json.obj(n.id -> n)
    case r: Relationship => Json.obj(r.id -> r)
  }

  def toJsonPair(m: Model, deep: Boolean): JsObject =
    Json.obj(m.id -> m)


  def fromJsonString(json: String): Model = fields(Json.parse(json)).collectFirst { case (id, v) => v.as[Model].withId(id) }.get
  def toJsonString(model: Model): String = toJsonPair(model, deep = true).toString()

}
