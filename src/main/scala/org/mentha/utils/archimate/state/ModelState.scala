package org.mentha.utils.archimate.state

import org.mentha.utils.archimate.model._
import org.mentha.utils.archimate.model.view._

import scala.util._

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
        val src = model.concept[Concept](srcId)
        val dst = model.concept[Concept](dstId)
        require(rMeta.isLinkPossible(src.meta, dst.meta))
        ChangeSets.AddConcept(Identifiable.generateId(), c)
      }
      case c @ Commands.AddView(_, params) => {
        // TODO: val path = (params \ json.`path`).as[List[String]]
        // TODO: val name = (params \ json.`name`).as[String]
        // TODO: require(model.findView(path, name).isEmpty, s"Duplicate view name: ${name}.")
        ChangeSets.AddView(Identifiable.generateId(), c)
      }
      case c @ Commands.ModView(id, params) => {
        // TODO: val path = (params \ json.`path`).as[List[String]]
        // TODO: val name = (params \ json.`name`).as[String]
        // TODO: model.findView(path, name).isEmpty match {
        // TODO:   case Some(v) => require(v.id == id, s"Duplicate view name: ${name}.")
        // TODO:   case None =>
        // TODO: }
        ChangeSets.ModView(c)
      }
      case c @ Commands.DelView(_) => {
        ChangeSets.DelView(c)
      }
    }
  }

  private[state] implicit class ImplicitConcept(concept: Concept) extends StateOps {
    override def prepare: PartialFunction[Command, ChangeSet] = {
      case c @ Commands.ModConcept(_, _) => ChangeSets.ModConcept(c)
      case c @ Commands.DelConcept(_) => ChangeSets.DelConcept(c)
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
  case class Noop() extends Request {}

  //
  object Queries {

    sealed trait Get[T] extends Query with ById[T] {}

    case class GetModel(id: ID = Identifiable.EMPTY_ID) extends Get[Model] {}
    case class GetConcept(id: ID) extends Get[Concept] {}
    case class GetView(id: ID) extends Get[View] {}
    case class GetViewObject(viewId: ID, id: ID) extends Get[ViewObject] {}
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

    sealed trait ViewCommand extends Command {
    }

    case class AddView(viewpoint: Type, params: JsonObject) extends ViewCommand with Add[View] {}

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
        val concept = model.add(newId) {
          command match {
            case Commands.AddElement(tp, params) => json.readElement(tp, params)
            case Commands.AddConnector(tp, rel, params) => json.readRelationshipConnector(tp, rel, params)
            case Commands.AddRelationship(tp, src, dst, params) => json.readRelationship(tp, model.concept[Concept](src), model.concept[Concept](dst), params)
          }
        }
        json.toJsonDiff(concept, "+")
      }
    }

    case class ModConcept(command: Commands.ModConcept) extends ModelChangeSet with Mod[Concept] {
      override def id: ID = command.id
      override def params: JsonObject = command.params
      override def commit(model: Model): Try[JsonObject] = Try {
        val concept = model.concept[Concept](id) match {
          case el: Element => json.fillElement(el, params)
          case rc: RelationshipConnector => json.fillRelationshipConnector(rc, params)
          case rel: Relationship => json.fillRelationship(rel, params)
        }
        json.toJsonDiff(concept, "@")
      }
    }

    case class DelConcept(command: Commands.DelConcept) extends ModelChangeSet with Del[Concept] {
      override def id: ID = command.id
      override def commit(model: Model): Try[JsonObject] = Try {
        val concept = model.concept[Concept](id).markAsDeleted()
        json.toJsonDiff(concept, "-")
      }
    }

    case class AddView(newId: ID, command: Commands.AddView) extends ModelChangeSet with Add[View] {
      override def params: JsonObject = command.params
      override def commit(model: Model): Try[JsonObject] = Try {
        val v = model.add(newId) { json.readView(command.viewpoint, params) }
        json.toJsonDiff(v, "+")
      }
    }

    case class ModView(command: Commands.ModView) extends ModelChangeSet with Mod[View] {
      override def id: ID = command.id
      override def params: JsonObject = command.params
      override def commit(model: Model): Try[JsonObject] = Try {
        val v = json.fillView(model.view(id), params)
        json.toJsonDiff(v, "@")
      }
    }

    case class DelView(command: Commands.DelView) extends ModelChangeSet with Del[View] {
      override def id: ID = command.id
      override def commit(model: Model): Try[JsonObject] = Try {
        val v = model.view(id).markAsDeleted()
        json.toJsonDiff(v, "-")
      }
    }

    sealed trait ViewChangeSet extends ChangeSet {
      def command: Command
      def viewId: ID

      def commit(model: Model, view: View): Try[JsonObject]

      override def commit(state: ModelState): Try[JsonObject] = commit(
        state.model,
        state.model.view(viewId)
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
            case Commands.AddViewNodeConcept(_, conceptId, params) => json.readViewNodeConcept(model.concept(conceptId), params)
            case Commands.AddViewRelationship(_, src, dst, conceptId, params) => json.readViewRelationship(view.get(src), view.get(dst), model.concept(conceptId), params)
          }
        }
        json.toJsonDiff(view, vo, "+")
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
        json.toJsonDiff(view, vo, "@")
      }
    }

    case class DelViewObject(command: Commands.DelViewObject) extends ViewChangeSet with Del[ViewObject] {
      override def viewId: ID = command.viewId
      override def id: ID = command.id
      override def commit(model: Model, view: View): Try[JsonObject] = Try {
        val vo = view.get[ViewObject](id).markAsDeleted()
        json.toJsonDiff(view, vo, "-")
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
        json.toJsonDiff(view, vo, "@")
      }
    }

  }

  object Responses {

    case class ModelChangeSet(modelId: String, changeSet: ModelState.ChangeSet, json: ModelState.JsonObject) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "commit" -> json
      )
    }

    case class ModelStateJson(modelId: String, request: ModelState.Query, json: ModelState.JsonObject) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "object" -> json
      )
    }

    case class ModelStateNoop(modelId: String) extends ModelState.Response {
      override def toJson: JsonObject = Json.obj(
        "noop" -> JsonObject.empty
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
    val (cmd, js) = Json.parse(value) match {
      case o : JsonObject if o.value.size == 0 => ("noop", o)
      case o : JsonObject if o.value.size == 1 => o.fields.head match { case (cmd, jsv) => (cmd, jsv.as[JsonObject]) }
      case _ => throw new IllegalStateException(s"Unexpected command structure: ${value}.")
    }

    cmd match {
      case "get-model" => Queries.GetModel(id = (js \ "id").asOpt[ID].getOrElse(Identifiable.EMPTY_ID))

      case "get-concept" => Queries.GetConcept(id = (js \ "id").as[ID])
      case "get-view" => Queries.GetView(id = (js \ "id").as[ID])
      case "get-view-object" => Queries.GetViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID])

      case "add-element" => Commands.AddElement(tp = (js \ json.`tp`).as[Type], params = js)
      case "add-connector" => Commands.AddConnector(tp = (js \ json.`tp`).as[Type], rel = (js \ json.`rel`).as[Type], params = js)
      case "add-relationship" => Commands.AddRelationship(tp = (js \ json.`tp`).as[Type], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], params = js)
      case "add-view" => Commands.AddView(viewpoint = (js \ json.`viewpoint`).as[Type], params = js)
      case "add-view-notes" => Commands.AddViewNotes(viewId = (js \ "viewId").as[ID], params = js)
      case "add-view-connection" => Commands.AddViewConnection(viewId = (js \ "viewId").as[ID], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], params = js)
      case "add-view-node-concept" => Commands.AddViewNodeConcept(viewId = (js \ "viewId").as[ID], conceptId = (js \ "concept").as[ID], params = js)
      case "add-view-relationship" => Commands.AddViewRelationship(viewId = (js \ "viewId").as[ID], src = (js \ json.`src`).as[ID], dst = (js \ json.`dst`).as[ID], conceptId = (js \ "concept").as[ID], params = js)

      case "del-concept" => Commands.DelConcept(id = (js \ "id").as[ID])
      case "del-view" => Commands.DelView(id = (js \ "id").as[ID])
      case "del-view-object" => Commands.DelViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID])

      case "mod-concept" => Commands.ModConcept(id = (js \ "id").as[ID], params = js)
      case "mod-view" => Commands.ModView(id = (js \ "id").as[ID], params = js)
      case "mod-view-object" => Commands.ModViewObject(viewId = (js \ "viewId").as[ID], id = (js \ "id").as[ID], params = js)

      case "noop" => Noop()
      case _ => throw new IllegalStateException(s"Unexpected command: ${cmd}.")
    }
  }

}

class ModelState(private[state] var model: Model = new Model) {

  import ModelState._

  def prepare(command: Command): ChangeSet = command match {
    case Commands.AddElement(_, _) => model.prepare(command)
    case Commands.AddConnector(_, _, _) => model.prepare(command)
    case Commands.AddRelationship(_, _, _, _) => model.prepare(command)
    case Commands.AddView(_, _) => model.prepare(command)
    case Commands.AddViewNotes(viewId, _) => view(viewId).prepare(command)
    case Commands.AddViewConnection(viewId, _,_, _) => view(viewId).prepare(command)
    case Commands.AddViewNodeConcept(viewId, _,_) => view(viewId).prepare(command)
    case Commands.AddViewRelationship(viewId, _,_, _, _) => view(viewId).prepare(command)
    case c: Commands.ConceptCommand with ById[_] => concept(c.id).prepare(command)
    case c: Commands.ViewCommand with ById[_] => view(c.id).prepare(command)
    case c: Commands.ViewObjectCommand with ById[_] => viewObject(c.viewId, c.id).prepare(command)
  }

  def query(query: ModelState.Query): ModelState.JsonObject = query match {
    case Queries.GetModel(_) => json.toJsonPair(model)
    case Queries.GetConcept(id) => json.toJsonPair(concept(id))
    case Queries.GetView(id) => json.toJsonPair(view(id))
    case Queries.GetViewObject(viewId, id) => json.toJsonPair(viewObject(viewId, id))
  }

  private def concept(id: ID): Concept = model.concept[Concept](id)
  private def view(viewId: ID): View = model.view(viewId)
  private def viewObject(viewId: ID, id: ID): ViewObject = view(viewId).get[ViewObject](id)

  def commit(changeSet: ChangeSet): JsonObject = {
    changeSet.commit(this).get
  }

}
