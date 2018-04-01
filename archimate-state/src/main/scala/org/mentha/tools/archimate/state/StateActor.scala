package org.mentha.tools.archimate.state

import akka.actor._
import akka.persistence._
import com.typesafe.config.Config
import org.mentha.tools.archimate.model._

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._
import scala.util.control._


object StateActor {

  sealed trait Event {}
  case object SubscriberJoin extends Event {}
  case class SubscriberSend(request: ModelState.Request) extends Event {}

  private case object NoMoreSubscribers {}
  private case object AutoSaveEvent {}

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
        if (this.subscribers.isEmpty) {
          context.parent ! NoMoreSubscribers // nobody is there - kill the state actor
        }
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

  private val config: Config = context.system.settings.config
  private val poisonPillDelay = Duration(config.getString("akka.mentha.state.poisonPillDelay")).asInstanceOf[FiniteDuration]
  private val autoSaveInterval = Duration(config.getString("akka.mentha.state.autoSaveInterval")).asInstanceOf[FiniteDuration]
  private val autoSaveThreshold = Math.min(config.getInt("akka.mentha.state.autoSaveThreshold"), 1000)

  private val dispatcher = context.actorOf(Props(new StateActor.StateDispatcherActor()), name = "dispatcher")

  override def recovery: Recovery = Recovery.create(SnapshotSelectionCriteria.Latest)
  override val journalPluginId: String = config.getString("akka.mentha.state.persistence.journal")
  override val snapshotPluginId: String = config.getString("akka.mentha.state.persistence.snapshot")
  override val persistenceId: String = s"state-model-${modelId}"

  private[state] var state: ModelState = new ModelState( new Model().withId(modelId) )
  private[state] def setState(state: ModelState): Unit = { this.state = state }

  private[state] def query(request: ModelState.Query): Try[ModelState.JsonObject] = Try { state.query(request) }
  private[state] def prepare(command: ModelState.Command): Try[ModelState.ChangeSet] = Try { state.prepare(command) }
  private[state] def commit(changeSet: ModelState.ChangeSet): Try[ModelState.Response] = changeSet.commit(state)

  private var changes: Int = 0
  private def snapshotState(force: Boolean): Unit = {
    if (force || (changes > 0)) {
      saveSnapshot(ModelState.toJsonPair(state))
      changes = 0
    }
  }

  override def receiveRecover: Receive = {
    case SnapshotOffer(md, json: play.api.libs.json.JsObject) => {
      try {
        setState(ModelState.fromJsonPair(id = modelId, jsonPair = json))
      } catch {
        case NonFatal(e) => {
          log.error(e, e.getMessage)
          self ! PoisonPill // An error has appeared during the initialization - just stop any activity
          throw e
        }
      }
      changes = 0
      deleteMessages(md.sequenceNr)
    }
    case e: ModelState.ChangeSet => {
      commit(e)
      changes += 1
    }
    case RecoveryCompleted => {
      snapshotState(force = false)
    }
  }

  private[state] def execute(user: ActorRef, changeSet: ModelState.ChangeSet): Unit = {
    persistAsync(changeSet) {
      changeSet => {
        commit(changeSet) match {
          case Success(response) => {
            dispatchAll(response, user)
            if (!changeSet.simple) {
              snapshotState(force = true)
            } else {
              changes += 1
              if (changes > autoSaveThreshold) {
                snapshotState(force = false)
              }
            }
          }
          case Failure(error) => {
            execute(user, Left(changeSet.command), error)
            snapshotState(force = true)
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
    val response = ModelState.Responses.ModelStateNoop(noop, modelId)
    dispatchUser(response, user)
  }

  @inline private def dispatchAll(response: ModelState.Response, user: ActorRef): Unit = {
    dispatcher ! response
  }

  @inline private def dispatchUser(response: ModelState.Response, user: ActorRef): Unit = {
    user ! response
  }

  private var delayedPoisonPill: Cancellable = _

  private def cancelPoisonPill(): Unit = {
    if (null != delayedPoisonPill) { delayedPoisonPill.cancel() }
    delayedPoisonPill = null
  }

  private def delayPoisonPill(): Unit = {
    cancelPoisonPill()
    implicit val executionContext: ExecutionContext = context.dispatcher
    delayedPoisonPill = context.system.scheduler.scheduleOnce(
      delay = poisonPillDelay,
      receiver = self,
      message = PoisonPill
    )
  }

  override def receiveCommand: Receive = receiveCommandNormal

  private val receiveCommandNormal: Receive = akka.event.LoggingReceive {
    case StateActor.SubscriberSend(cmd) => cmd match {
      case command: ModelState.Command => execute(sender(), command)
      case query: ModelState.Query => execute(sender(), query)
      case noop @ ModelState.Noop() => execute(sender(), noop)
    }
    case StateActor.SubscriberJoin => {
      cancelPoisonPill() // cancel poison pull if a new subscriber has arrived
      val user = sender()
      dispatcher.forward(user)
      execute(user, ModelState.Queries.GetModel(id = state.model.id))
    }
    case StateActor.AutoSaveEvent => {
      snapshotState(force = false) // store all changes (if exist)
    }
    case StateActor.NoMoreSubscribers => {
      snapshotState(force = false) // store all changes (if exist)
      delayPoisonPill() // there is no more subscribers, take a while and kill itself
    }

    case SaveSnapshotSuccess(_) =>
    case DeleteSnapshotSuccess(_) =>
    case DeleteSnapshotsSuccess(_) =>

    case SaveSnapshotFailure(_, cause) => log.error(cause, s"SaveSnapshotFailure: ${cause.getMessage}")
    case DeleteSnapshotFailure(_, cause) => log.error(cause, s"DeleteSnapshotFailure: ${cause.getMessage}")
    case DeleteSnapshotsFailure(_, cause) => log.error(cause, s"DeleteSnapshotsFailure: ${cause.getMessage}")
  }

  private var autoSaveTimer: Cancellable = _

  private def cancelAutoSaveTimer(): Unit = {
    if (null != autoSaveTimer) { autoSaveTimer.cancel() }
    autoSaveTimer = null
  }

  private def startAutoSaveTimer(): Unit = {
    cancelAutoSaveTimer()
    implicit val executionContext: ExecutionContext = context.dispatcher
    autoSaveTimer = context.system.scheduler.schedule(
      initialDelay = autoSaveInterval,
      interval = autoSaveInterval,
      receiver = self,
      message = StateActor.AutoSaveEvent
    )
  }

  override def postStop(): Unit = {
    cancelAutoSaveTimer()
    cancelPoisonPill()
    super.postStop()
  }

  override def preStart(): Unit = {
    super.preStart()
    startAutoSaveTimer()
  }
}
