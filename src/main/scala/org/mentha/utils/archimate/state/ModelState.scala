package org.mentha.utils.archimate.state

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

import scala.util.Try

object ModelState {

  import play.api.libs.json._

  type JsonObject = json.JsonObject
  val JsonObject = json.JsonObject

  type Type = String
  type ID = Identifiable.ID

  private[state] trait StateOps {
    def prepare: PartialFunction[Command, ChangeSet]
  }

  private[state] implicit class ImplicitModel(model: Model) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.AddElement(tp, _) => {
        val eMeta = nodes.mapElements.getOrElse(tp, throw new IllegalStateException(s"Unexpected element type: ${tp}"))
        ChangeSets.AddConcept(Identifiable.generateId(), c)
      }
      case c @ Commands.AddConnector(tp, rtp, _) => {
        val cMeta = nodes.mapRelationshipConnectors.getOrElse(tp, throw new IllegalStateException( s"Unexpected relationship connector type: ${tp}"))
        val rMeta = edges.mapRelations.getOrElse(rtp, throw new IllegalStateException( s"Unexpected relationship type: ${tp}"))
        ChangeSets.AddConcept(Identifiable.generateId(), c)
      }
      case c @ Commands.AddRelationship(tp, srcId, dstId, _) => {
        val rMeta = edges.mapRelations.getOrElse(tp, throw new IllegalStateException(s"Unexpected relationship type: ${tp}"))
        val src = model.get[Concept](srcId)
        val dst = model.get[Concept](dstId)
        require(rMeta.isLinkPossible(src.meta, dst.meta))
        ChangeSets.AddConcept(Identifiable.generateId(), c)
      }
    }
  }

  private[state] implicit class ImplicitConcept(concept: Concept) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.ModConcept(_, _) => ChangeSets.ModConcept(c)
      case c @ Commands.DelConcept(_) => ChangeSets.DelConcept(c)
    }
  }

  private[state] implicit class ImplicitFolder(folder: Folder) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.AddFolder(_, params) => {
        val name = (params \ json.`name`).as[String]
        require(folder.getFolderByName(name).isEmpty, s"Duplicate folder name: ${name}.")
        ChangeSets.AddSubFolder(folder.id, Identifiable.generateId(), c)
      }
      case c @ Commands.ModFolder(id, params) => {
        for { name <- (params \ json.`name`).asOpt[String] } {
          folder.getFolderByName(name) match {
            case Some(f) => require(f.id == id, s"Duplicate folder name: ${name}.")
            case None =>
          }
        }
        ChangeSets.ModSubFolder(folder.id, c)
      }
      case c @ Commands.DelFolder(_) => ChangeSets.DelSubFolder(folder.id, c)

      case c @ Commands.AddView(_, _, params) => {
        val name = (params \ json.`name`).as[String]
        require(folder.getViewByName(name).isEmpty, s"Duplicate view name: ${name}.")
        ChangeSets.AddFolderView(folder.id, Identifiable.generateId(), c)
      }
      case c @ Commands.ModView(id, params) => {
        for { name <- (params \ json.`name`).asOpt[String] } {
          folder.getViewByName(name) match {
            case Some(v) => require(v.id == id, s"Duplicate view name: ${name}.")
            case None =>
          }
        }
        ChangeSets.ModFolderView(folder.id, c)
      }
      case c @ Commands.DelView(_) => ChangeSets.DelFolderView(folder.id, c)
    }
  }

  private[state] implicit class ImplicitView(view: View) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.AddViewNotes(_, _) => ChangeSets.AddViewObject(Identifiable.generateId(), c)
      case c @ Commands.AddViewConnection(_, _, _, _) => ChangeSets.AddViewObject(Identifiable.generateId(), c)
      case c @ Commands.AddViewNodeConcept(_, _, _) => ChangeSets.AddViewObject(Identifiable.generateId(), c)
      case c @ Commands.AddViewRelationship(_, _, _, _, _) => ChangeSets.AddViewObject(Identifiable.generateId(), c)
    }
  }

  private[state] implicit class ImplicitViewObject(viewObject: ViewObject) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.ModViewObject(_, _, _) => ChangeSets.ModViewObject(c)
      case c @ Commands.DelViewObject(_, _) => ChangeSets.DelViewObject(c)
    }
  }

  sealed trait WithParams {
    def params: JsonObject
  }

  sealed trait ById[T] {
    def id: ID
  }

  sealed trait Add[T] extends WithParams {

  }

  sealed trait Mod[T] extends ById[T] with WithParams {

  }

  sealed trait Del[T] extends ById[T] {

  }

  sealed trait ConnectionParams {
    def src: ID
    def dst: ID
  }

  sealed trait JsonSerializable {
    def toJson: JsonObject
  }

  //
  sealed trait Response extends JsonSerializable {}

  //
  sealed trait Request {}

  //
  sealed trait Command extends Request {}

  //
  sealed trait Query extends Request {}

  //
  sealed trait ChangeSet {
    def commit(state: ModelState): Try[JsonObject]
  }

  //
  object Queries {

    sealed trait Get[T] extends Query with ById[T] {
      def deep: Boolean
    }

    case class GetModel(id: ID = Identifiable.EMPTY_ID, deep: Boolean = true) extends Get[Model] {}
    case class GetConcept(id: ID, deep: Boolean = false) extends Get[Concept] {}
    case class GetFolder(id: ID, deep: Boolean) extends Get[Folder] {}
    case class GetView(id: ID, deep: Boolean) extends Get[View] {}
    case class GetViewObject(viewId: ID, id: ID, deep: Boolean = false) extends Get[ViewObject] {}
  }



  //
  object Commands {

    sealed trait ConceptCommand extends Command {}

    sealed trait AddConceptCommand[T] extends ConceptCommand with Add[T] {
      def tp: Type
    }

    case class AddElement(tp: Type, params: JsonObject) extends AddConceptCommand[Element] {}

    case class AddConnector(tp: Type, rel: Type, params: JsonObject) extends AddConceptCommand[RelationshipConnector] {}

    case class AddRelationship(tp: Type, src: ID, dst: ID, params: JsonObject) extends AddConceptCommand[Relationship] with ConnectionParams {}

    case class DelConcept(id: ID) extends ConceptCommand with Del[Concept] {}

    case class ModConcept(id: ID, params: JsonObject) extends ConceptCommand with Mod[Concept] {}

    sealed trait FolderCommand extends Command {
    }

    case class AddFolder(path: List[String], params: JsonObject) extends FolderCommand with Add[Folder] {}

    case class DelFolder(id: ID) extends FolderCommand with Del[Folder] {}

    case class ModFolder(id: ID, params: JsonObject) extends FolderCommand with Mod[Folder] {}

    sealed trait ViewCommand extends Command {
    }

    case class AddView(path: List[String], viewpoint: Type, params: JsonObject) extends ViewCommand with Add[View] {}

    case class DelView(id: ID) extends ViewCommand with Del[View] {}

    case class ModView(id: ID, params: JsonObject) extends ViewCommand with Mod[View] {}

    sealed trait ViewObjectCommand extends Command {
      def viewId: ID
    }

    sealed trait AddViewObjectCommand[T] extends ViewObjectCommand with Add[T] {

    }

    case class AddViewNotes(viewId: ID, params: JsonObject) extends AddViewObjectCommand[ViewNotes] {}

    case class AddViewNodeConcept(viewId: ID, conceptId: ID, params: JsonObject) extends AddViewObjectCommand[ViewNodeConcept[_]] {}

    case class AddViewConnection(viewId: ID, src: ID, dst: ID, params: JsonObject) extends AddViewObjectCommand[ViewConnection] with ConnectionParams {}

    case class AddViewRelationship(viewId: ID, src: ID, dst: ID, conceptId: ID, params: JsonObject) extends AddViewObjectCommand[ViewRelationship[_]] with ConnectionParams {}

    case class DelViewObject(viewId: ID, id: ID) extends ViewObjectCommand with Del[ViewObject] {}

    case class ModViewObject(viewId: ID, id: ID, params: JsonObject) extends ViewObjectCommand with Mod[ViewObject] {}

    sealed trait PlaceViewObjectCommand extends ViewObjectCommand with Mod[ViewObject] {}

    case class PlaceViewNode(viewId: ID, id: ID, position: Point, size: Size, params: JsonObject) extends PlaceViewObjectCommand {}

    case class PlaceViewEdge(viewId: ID, id: ID, points: List[Point], params: JsonObject) extends PlaceViewObjectCommand {}

  }

  object ChangeSets {

    sealed trait ModelChangeSet extends ChangeSet {
      def command: Command
      def commit(model: Model): Try[JsonObject]
      override def commit(state: ModelState): Try[JsonObject] = commit(state.model)
    }

    case class AddConcept(newId: ID, command: Commands.AddConceptCommand[_]) extends ModelChangeSet with Add[Concept] {
      override def params: JsonObject = command.params
      override def commit(model: Model): Try[JsonObject] = Try {
        val concept = model.add[Concept](newId) {
          command match {
            case Commands.AddElement(tp, params) => json.readElement(tp, params)
            case Commands.AddConnector(tp, rel, params) => json.readRelationshipConnector(tp, rel, params)
            case Commands.AddRelationship(tp, src, dst, params) => json.readRelationship(tp, model.get(src), model.get(dst), params)
          }
        }
        json.toJsonPair(concept, deep = false)
      }
    }

    case class ModConcept(command: Commands.ModConcept) extends ModelChangeSet with Mod[Concept] {
      override def id: ID = command.id
      override def params: JsonObject = command.params
      override def commit(model: Model): Try[JsonObject] = Try {
        val concept = model.get[Concept](id) match {
          case el: Element => json.fillElement(el, params)
          case rc: RelationshipConnector => json.fillRelationshipConnector(rc, params)
          case rel: Relationship => json.fillRelationship(rel, params)
        }
        json.toJsonPair(concept, deep = false)
      }
    }

    case class DelConcept(command: Commands.DelConcept) extends ModelChangeSet with Del[Concept] {
      override def id: ID = command.id
      override def commit(model: Model): Try[JsonObject] = Try {
        val concept = model.get[Concept](id).markAsDeleted()
        json.toJsonPair(concept, deep = false)
      }
    }

    sealed trait FolderChangeSet extends ChangeSet {
      def command: Command
      def folderId: ID
      def commit(model: Model, folder: Folder): Try[JsonObject]
      override def commit(state: ModelState): Try[JsonObject] = commit(
        state.model,
        state.model.getFolder(folderId)
      )
    }

    case class AddSubFolder(folderId: ID, newId: ID, command: Commands.AddFolder) extends FolderChangeSet with Add[Folder] {
      override def params: JsonObject = command.params
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val f = folder.add(newId) { json.readFolder(params) }
        json.toJsonPair(f, deep = false)
      }
    }

    case class ModSubFolder(folderId: ID, command: Commands.ModFolder) extends FolderChangeSet with Mod[Folder] {
      override def id: ID = command.id
      override def params: JsonObject = command.params
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val f = json.fillFolder(
          folder.getFolder(id).getOrElse(throw new NoSuchElementException(s"No folder with id=${id}.")),
          params
        )
        json.toJsonPair(f, deep = false)
      }
    }

    case class DelSubFolder(folderId: ID, command: Commands.DelFolder) extends FolderChangeSet with Del[Folder] {
      override def id: ID = command.id
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val f = folder.getFolder(id).getOrElse(throw new NoSuchElementException(s"No folder with id=${id}.")).markAsDeleted()
        json.toJsonPair(f, deep = false)
      }
    }

    case class AddFolderView(folderId: ID, newId: ID, command: Commands.AddView) extends FolderChangeSet with Add[View] {
      override def params: JsonObject = command.params
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val v = folder.add(newId) { json.readView(command.viewpoint, params) }
        json.toJsonPair(v, deep = false)
      }
    }

    case class ModFolderView(folderId: ID, command: Commands.ModView) extends FolderChangeSet with Mod[View] {
      override def id: ID = command.id
      override def params: JsonObject = command.params
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val v = json.fillView(
          folder.getView(id).getOrElse(throw new NoSuchElementException(s"No view with id=${id}.")),
          params
        )
        json.toJsonPair(v, deep = false)
      }
    }

    case class DelFolderView(folderId: ID, command: Commands.DelView) extends FolderChangeSet with Del[View] {
      override def id: ID = command.id
      def commit(model: Model, folder: Folder): Try[JsonObject] = Try {
        val v = folder.getFolder(id).getOrElse(throw new NoSuchElementException(s"No folder with id=${id}.")).markAsDeleted()
        json.toJsonPair(v, deep = false)
      }
    }

    sealed trait ViewChangeSet extends ChangeSet {
      def command: Command
      def viewId: ID

      def commit(model: Model, view: View): Try[JsonObject]

      override def commit(state: ModelState): Try[JsonObject] = commit(
        state.model,
        state.model.getView(viewId)
      )
    }

    case class AddViewObject(newId: ID, command: Commands.AddViewObjectCommand[_]) extends ViewChangeSet with Add[ViewObject] {
      override def viewId: ID = command.viewId
      override def params: JsonObject = command.params
      override def commit(model: Model, view: View): Try[JsonObject] = Try {
        val vo = view.add[ViewObject](newId) {
          command match {
            case Commands.AddViewNotes(_, p) => json.readViewNotes(p)
            case Commands.AddViewConnection(_, src, dst, params) => json.readViewConnection(view.get(src), view.get(dst), params)
            case Commands.AddViewNodeConcept(_, conceptId, params) => json.readViewNodeConcept(model.get(conceptId), params)
            case Commands.AddViewRelationship(_, src, dst, conceptId, params) => json.readViewRelationship(view.get(src), view.get(dst), model.get(conceptId), params)
          }
        }
        json.toJsonPair(vo, deep = false)
      }
    }

    case class ModViewObject(command: Commands.ModViewObject) extends ViewChangeSet with Mod[ViewObject] {
      override def viewId: ID = command.viewId
      override def id: ID = command.id
      override def params: JsonObject = command.params
      override def commit(model: Model, view: View): Try[JsonObject] = Try {
        val vo = view.get[ViewObject](id) match {
          case vn: ViewNotes => json.fillViewNotes(vn, params)
          case vc: ViewConnection => json.fillViewConnection(vc, params)
          case vnc: ViewNodeConcept[_] => json.fillViewNodeConcept(vnc, params)
          case vrs: ViewRelationship[_] => json.fillViewRelationship(vrs, params)
        }
        json.toJsonPair(vo, deep = false)
      }
    }

    case class DelViewObject(command: Commands.DelViewObject) extends ViewChangeSet with Del[ViewObject] {
      override def viewId: ID = command.viewId
      override def id: ID = command.id
      override def commit(model: Model, view: View): Try[JsonObject] = Try {
        val vo = view.get[ViewObject](id).markAsDeleted()
        json.toJsonPair(vo, deep = false)
      }
    }

    case class PlaceViewObject(command: Commands.PlaceViewObjectCommand) extends ViewChangeSet with Mod[ViewObject] {
      override def viewId: ID = command.viewId
      override def id: ID = command.id
      override def params: JsonObject = command.params
      override def commit(model: Model, view: View): Try[JsonObject] = Try {
        val vo = command match {
          case Commands.PlaceViewNode(_, id, position, size, _) => view.get[ViewNode](id).withPosition(position).withSize(size)
          case Commands.PlaceViewEdge(_, id, points, _) => view.get[ViewEdge](id).withPoints(points)
        }
        json.toJsonPair(vo, deep = false)
      }
    }

  }

  object Responses {

    case class ModelChangeSet(modelId: String, changeSet: ModelState.ChangeSet, json: ModelState.JsonObject) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "commmit" -> json
      )
    }

    case class ModelStateJson(modelId: String, request: ModelState.Query, json: ModelState.JsonObject) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "object" -> json
      )
    }

    case class ModelStateFail(modelId: String, error: Throwable) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "error" -> error.getMessage
      )
    }


  }

  def fromJson(json: String): ModelState = new ModelState(
    org.mentha.utils.archimate.model.json.fromJsonString(json)
  )

  def toJson(state: ModelState): String = {
    org.mentha.utils.archimate.model.json.toJsonString(state.model)
  }

  def parseMessage(value: String): ModelState.Request = {
    import play.api.libs.json._
    val strings = value.split("\\s+", 2)
    require(strings.length > 1)
    val tp = strings(0)
    val js = if (strings.length > 1 ) { Json.parse(strings(1)).as[JsObject] } else JsonObject.empty

    tp match {
      case "-get-model" => Queries.GetModel(id = (js \ "id").asOpt[ID].getOrElse(Identifiable.EMPTY_ID), deep = (js \ "deep").asOpt[Boolean].getOrElse(true))

      case "-get-concept" => Queries.GetConcept(id = (js \ "id").as[ID], deep = (js \ "deep").asOpt[Boolean].getOrElse(true))
      case "-get-folder" => Queries.GetFolder(id = (js \ "id").as[ID], deep = (js \ "deep").asOpt[Boolean].getOrElse(true))
      case "-get-view" => Queries.GetView(id = (js \ "id").as[ID], deep = (js \ "deep").asOpt[Boolean].getOrElse(true))
      case "-get-view-object" => Queries.GetViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID], deep = (js \ "deep").asOpt[Boolean].getOrElse(true))

      case "-add-element" => Commands.AddElement(tp = (js \ json.`tp`).as[Type], params = js)
      case "-add-connector" => Commands.AddConnector(tp = (js \ json.`tp`).as[Type], rel = (js \ json.`rel`).as[Type], params = js)
      case "-add-relationship" => Commands.AddRelationship(tp = (js \ json.`tp`).as[Type], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], params = js)
      case "-add-folder" => Commands.AddFolder(path = (js \ "path").as[List[String]], params = js)
      case "-add-view" => Commands.AddView(path = (js \ "path").as[List[String]], viewpoint = (js \ json.`viewpoint`).as[Type], params = js)
      case "-add-view-notes" => Commands.AddViewNotes(viewId = (js \ "viewId").as[ID], params = js)
      case "-add-view-connection" => Commands.AddViewConnection(viewId = (js \ "viewId").as[ID], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], params = js)
      case "-add-view-node-concept" => Commands.AddViewNodeConcept(viewId = (js \ "viewId").as[ID], conceptId = (js \ "concept").as[ID], params = js)
      case "-add-view-relationship" => Commands.AddViewRelationship(viewId = (js \ "viewId").as[ID], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], conceptId = (js \ "concept").as[ID], params = js)

      case "-del-concept" => Commands.DelConcept(id = (js \ "id").as[ID])
      case "-del-folder" => Commands.DelFolder(id = (js \ "id").as[ID])
      case "-del-view" => Commands.DelView(id = (js \ "id").as[ID])
      case "-del-view-object" => Commands.DelViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID])

      case "-mod-concept" => Commands.ModConcept(id = (js \ "id").as[ID], params = js)
      case "-mod-folder" => Commands.ModFolder(id = (js \ "id").as[ID], params = js)
      case "-mod-view" => Commands.ModView(id = (js \ "id").as[ID], params = js)
      case "-mod-view-object" => Commands.ModViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID], params = js)

      case _ => throw new IllegalStateException(s"Unexpected command: ${tp}.")
    }
  }

}

class ModelState(private[state] var model: Model = new Model) {

  import ModelState._

  def prepare(command: Command): ChangeSet = command match {
    case Commands.AddElement(_, _) => model.prepare(command)
    case Commands.AddConnector(_, _, _) => model.prepare(command)
    case Commands.AddRelationship(_, _, _, _) => model.prepare(command)
    case Commands.AddFolder(path, _) => folder(path).prepare(command)
    case Commands.AddView(path, _, _) => folder(path).prepare(command)
    case Commands.AddViewNotes(viewId, _) => view(viewId).prepare(command)
    case Commands.AddViewConnection(viewId, _,_, _) => view(viewId).prepare(command)
    case Commands.AddViewNodeConcept(viewId, _,_) => view(viewId).prepare(command)
    case Commands.AddViewRelationship(viewId, _,_, _, _) => view(viewId).prepare(command)
    case c: Commands.ConceptCommand with ById[_] => concept(c.id).prepare(command)
    case c: Commands.FolderCommand with ById[_] => folder(c.id).prepare(command)
    case c: Commands.ViewCommand with ById[_] => view(c.id).prepare(command)
    case c: Commands.ViewObjectCommand with ById[_] => viewObject(c.viewId, c.id).prepare(command)
  }

  def query(query: ModelState.Query): ModelState.JsonObject = query match {
    case Queries.GetModel(id, deep) => json.toJsonPair(model, deep)
    case Queries.GetConcept(id, deep) => json.toJsonPair(concept(id), deep)
    case Queries.GetFolder(id, deep) => json.toJsonPair(folder(id), deep)
    case Queries.GetView(id, deep) => json.toJsonPair(view(id), deep)
    case Queries.GetViewObject(viewId, id, deep) => json.toJsonPair(viewObject(viewId, id), deep)
  }

  private def concept(id: ID): Concept = model.get[Concept](id)
  private def folder(path: List[String]): Folder = model \ path
  private def folder(folderId: ID): Folder = model.getFolder(folderId)
  private def view(viewId: ID): View = model.getView(viewId)
  private def viewObject(viewId: ID, id: ID): ViewObject = view(viewId).get[ViewObject](id)

  def commit(changeSet: ChangeSet): JsonObject = {
    changeSet.commit(this).get
  }

}
