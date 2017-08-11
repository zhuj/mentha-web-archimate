package org.mentha.utils.archimate.state

import akka.actor._
import akka.persistence._
import org.mentha.utils.archimate.model._

import scala.util._

object StateActor {

  sealed trait Event {}
  case object SubscriberJoin extends Event {}
  case class SubscriberSend(request: ModelState.Request) extends Event {}
  case class SubscriberFail(request: String, error: Throwable) extends Event {}


  /**
    * The actor which dispatches incoming messages to all subscribers
    */
  private[state] class StateDispatcherActor extends Actor with ActorLogging {
    private def _name(actorRef: ActorRef): String = actorRef.path.name
    private var subscribers: Map[String, ActorRef] = Map.empty

    override def receive: Receive = akka.event.LoggingReceive {
      case response: ModelState.Response => {
        subscribers.values.foreach(_ ! response)
      }
      case user: ActorRef => {
        val name = _name(user)
        this.subscribers += (name -> user)
        context.watch(user) // we want to be notified when the subscriber dies
      }
      case Terminated(user) => {
        val name = _name(user)
        this.subscribers -= name
        if (this.subscribers.isEmpty) { context.parent ! PoisonPill } // nobody is there - kill the state actor TODO: check if it works
      }
    }
  }

}

/**
  * Process commands, generates sets of changes and applies it to the model
  * It uses StateDispatcherActor to deliver the model changes to the subscribers
  * @param modelId
  */
class StateActor(val modelId: String) extends PersistentActor with ActorLogging {

  private val dispatcher = context.actorOf(Props(new StateActor.StateDispatcherActor()), name = "dispatcher")

  override def recovery: Recovery = Recovery.create(SnapshotSelectionCriteria.Latest)
  override val persistenceId: String = s"state-model-${modelId}"

  private[state] var state: ModelState = new ModelState( new Model().withId(modelId) )
  private[state] def setState(state: ModelState): Unit = { this.state = state }

  private[state] def query(request: ModelState.Query): Try[ModelState.JsonObject] = Try { state.query(request) }
  private[state] def prepare(command: ModelState.Command): Try[ModelState.ChangeSet] = Try { state.prepare(command) }
  private[state] def commit(changeSet: ModelState.ChangeSet): Try[ModelState.Response] = changeSet.commit(state)

  override def receiveRecover: Receive = {
    case RecoveryCompleted => saveSnapshot(ModelState.toJson(state))
    case SnapshotOffer(_, json: String) => setState(ModelState.fromJson(id = modelId, jsonString = json))
    case e: ModelState.ChangeSet => commit(e)
  }

  private[state] def execute(user: ActorRef, changeSet: ModelState.ChangeSet): Unit = {
    persist(changeSet) {
      changeSet => {
        commit(changeSet) match {
          case Success(response) => {
            dispatchAll(response, user)
            if (!changeSet.simple) {
              saveSnapshot(ModelState.toJson(state))
            }
            // TODO: if (lastSequenceNr != 0 && lastSequenceNr % snapshotInterval == 0) {
            // TODO:   saveSnapshot(ModelState.toJson(state))
            // TODO: }
          }
          case Failure(error) => {
            execute(user, Left(changeSet.command), error)
            saveSnapshot(ModelState.toJson(state))
          }
        }
      }
    }
  }

  private[state] def execute(user: ActorRef, request: Either[ModelState.Request, String], error: Throwable): Unit = {
    log.error(error, error.getMessage)
    val fail = ModelState.Responses.ModelStateError(state.model.id, request, error)
    dispatchUser(fail, user)
  }

  private[state] def execute(user: ActorRef, command: ModelState.Command): Unit = {
    prepare(command) match {
      case Success(changeSet) => execute(user, changeSet)
      case Failure(error) => execute(user, Left(command), error)
    }
  }

  private[state] def execute(user: ActorRef, request: ModelState.Query): Unit = {
    query(request) match {
      case Success(json) => {
        val response = ModelState.Responses.ModelObjectJson(modelId, request, json)
        dispatchUser(response, user)
      }
      case Failure(error) => {
        execute(user, Left(request), error)
      }
    }
  }

  private[state] def execute(user: ActorRef, noop: ModelState.Noop): Unit = {
    val response = ModelState.Responses.ModelStateNoop(modelId)
    dispatchUser(response, user)
  }

  @inline private def dispatchAll(response: ModelState.Response, user: ActorRef): Unit = {
    dispatcher ! response
  }

  @inline private def dispatchUser(response: ModelState.Response, user: ActorRef): Unit = {
    user ! response
  }

  override def receiveCommand: Receive = akka.event.LoggingReceive {
    case StateActor.SubscriberSend(cmd) => cmd match {
      case command: ModelState.Command => execute(sender(), command)
      case query: ModelState.Query => execute(sender(), query)
      case noop @ ModelState.Noop() => execute(sender(), noop)
    }
    case StateActor.SubscriberJoin => {
      val user = sender()
      dispatcher.forward(user)
      execute(user, ModelState.Queries.GetModel(id = state.model.id))
    }
    case StateActor.SubscriberFail(request, error) => {
      execute(sender(), Right(request), error) // should never happen (it's handled by the UserActor)
    }

    case SaveSnapshotSuccess(_) =>
    case DeleteSnapshotSuccess(_) =>
    case DeleteSnapshotsSuccess(_) =>
    case SaveSnapshotFailure(_, _) =>
    case DeleteSnapshotFailure(_, _) =>
    case DeleteSnapshotsFailure(_, _) =>

  }

}
