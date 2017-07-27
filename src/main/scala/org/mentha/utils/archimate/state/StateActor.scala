package org.mentha.utils.archimate.state

import akka.actor._
import akka.persistence._
import org.mentha.utils.archimate.model.Model

import scala.util.{Failure, Success, Try}

object StateActor {

  sealed trait Event {}
  case class SubscriberJoin() extends Event {}
  case class SubscriberSend(request: ModelState.Request) extends Event {}
  case class SubscriberFail(request: String, error: Throwable) extends Event {}

}

class StateActor(val modelId: String) extends PersistentActor with ActorLogging {

  private val snapshotInterval = 16
  private var subscribers: Map[String, ActorRef] = Map.empty

  override def recovery: Recovery = Recovery.create(SnapshotSelectionCriteria.Latest)
  override def persistenceId: String = s"state-model-${modelId}"

  private[state] var state: ModelState = new ModelState( new Model().withId(modelId) )
  private[state] def setState(state: ModelState): Unit = { this.state = state }

  private[state] def query(request: ModelState.Query): ModelState.JsonObject = state.query(request)
  private[state] def prepare(command: ModelState.Command): ModelState.ChangeSet = state.prepare(command)
  private[state] def commit(changeSet: ModelState.ChangeSet): ModelState.Response = state.commit(changeSet)

  override def receiveRecover: Receive = {
    case RecoveryCompleted => saveSnapshot(ModelState.toJson(state))
    case SnapshotOffer(_, json: String) => setState(ModelState.fromJson(id = modelId, json = json))
    case e: ModelState.ChangeSet => commit(e)
  }

  private[state] def execute(user: String, changeSet: ModelState.ChangeSet): Unit = {
    persist(changeSet) {
      changeSet => {
        Try { commit(changeSet) } match {
          case Failure(error) => {
            execute(user, Left(changeSet.command), error)
            saveSnapshot(ModelState.toJson(state))
          }
          case Success(response) => {
            dispatchAll(response)
            answerDirectly(response, user)
            if (!changeSet.simple) {
              saveSnapshot(ModelState.toJson(state))
            }
            // TODO: if (lastSequenceNr != 0 && lastSequenceNr % snapshotInterval == 0) {
            // TODO:   saveSnapshot(ModelState.toJson(state))
            // TODO: }
          }
        }
      }
    }
  }

  private[state] def execute(user: String, request: Either[ModelState.Request, String], error: Throwable): Unit = {
    log.error(error, error.getMessage)
    val fail = ModelState.Responses.ModelStateError(state.model.id, request, error)
    dispatchUser(fail, user)
    answerDirectly(fail, user)
  }

  private[state] def execute(user: String, command: ModelState.Command): Unit = {
    Try { prepare(command) } match {
      case Success(changeSet) => execute(user, changeSet)
      case Failure(error) => execute(user, Left(command), error)
    }
  }

  private[state] def execute(user: String, request: ModelState.Query): Unit = {
    Try { query(request) } match {
      case Failure(error) => execute(user, Left(request), error)
      case Success(json) => {
        val response = ModelState.Responses.ModelObjectJson(modelId, request, json)
        dispatchUser(response, user)
        answerDirectly(response, user)
      }
    }
  }

  private[state] def execute(user: String, noop: ModelState.Noop): Unit = {
    val response = ModelState.Responses.ModelStateNoop(modelId)
    dispatchUser(response, user)
    answerDirectly(response, user)
  }

  private def dispatchAll(response: ModelState.Response): Unit = {
    subscribers.values.foreach(_ ! response)
  }

  private def dispatchUser(response: ModelState.Response, user: String): Unit = {
    subscribers.get(user).foreach(_ ! response)
  }

  private def answerDirectly(response: ModelState.Response, user: String) = {
    subscribers.get(user) match {
      case None => sender() ! response
      case _ =>
    }
  }

  private def _name(actorRef: ActorRef): String = actorRef.path.name

  override def receiveCommand: Receive = akka.event.LoggingReceive {
    case Terminated(user) => {
      val name = _name(user)
      this.subscribers -= name
    }
    case StateActor.SubscriberJoin() => {
      val user = sender()
      val name = _name(user)
      this.subscribers += (name -> user)
      context.watch(user)
      execute(name, ModelState.Queries.GetModel(id = state.model.id))
    }
    case StateActor.SubscriberFail(request, error) => {
      // TODO: move this case out ot the StateActor
      execute(_name(sender()), Right(request), error)
    }
    case StateActor.SubscriberSend(cmd) => cmd match {
      case query: ModelState.Query => execute(_name(sender()), query)
      case command: ModelState.Command => execute(_name(sender()), command)
      case noop @ ModelState.Noop() => execute(_name(sender()), noop)
    }

    case SaveSnapshotSuccess(_) =>
    case DeleteSnapshotSuccess(_) =>
    case DeleteSnapshotsSuccess(_) =>
    case SaveSnapshotFailure(_, _) =>
    case DeleteSnapshotFailure(_, _) =>
    case DeleteSnapshotsFailure(_, _) =>
  }

}
