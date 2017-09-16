package org.mentha.utils.archimate.state

import akka.actor._
import akka.persistence._
import com.typesafe.config.Config
import org.mentha.utils.archimate.model._

import scala.concurrent.ExecutionContext
import scala.util._
import scala.util.control.NonFatal

object StateActor {

  sealed trait Event {}
  case object SubscriberJoin extends Event {}
  case class SubscriberSend(request: ModelState.Request) extends Event {}
  case class SubscriberFail(request: String, error: Throwable) extends Event {}

  private case object NoMoreSubscribers {}

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
  private val dispatcher = context.actorOf(Props(new StateActor.StateDispatcherActor()), name = "dispatcher")

  override def recovery: Recovery = Recovery.create(SnapshotSelectionCriteria.Latest)
  override val journalPluginId: String = config.getString("akka.mentha.state.persistence.journal")
  override val snapshotPluginId: String = config.getString("akka.mentha.state.persistence.snapshot")
  override val persistenceId: String = s"state-model-${modelId}"

  private[state] val snapshotPrettyFormat: Boolean = config.getBoolean("akka.mentha.state.persistence.pretty")

  private[state] var state: ModelState = new ModelState( new Model().withId(modelId) )
  private[state] def setState(state: ModelState): Unit = { this.state = state }

  private[state] def query(request: ModelState.Query): Try[ModelState.JsonObject] = Try { state.query(request) }
  private[state] def prepare(command: ModelState.Command): Try[ModelState.ChangeSet] = Try { state.prepare(command) }
  private[state] def commit(changeSet: ModelState.ChangeSet): Try[ModelState.Response] = changeSet.commit(state)

  private var changes: Int = 0

  override def receiveRecover: Receive = {
    case SnapshotOffer(md, json: String) => {
      try {
        setState(ModelState.fromJson(id = modelId, jsonString = json))
      } catch {
        case NonFatal(e) => {
          log.error(e, e.getMessage)
          self ! PoisonPill // An error has appeared during the initialization - just stop any activity
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
      if (changes > 0) { saveSnapshot(ModelState.toJson(state, snapshotPrettyFormat)) }
      changes = 0
    }
  }

  private[state] def execute(user: ActorRef, changeSet: ModelState.ChangeSet): Unit = {
    persist(changeSet) {
      changeSet => {
        commit(changeSet) match {
          case Success(response) => {
            dispatchAll(response, user)
            if (!changeSet.simple) {
              saveSnapshot(ModelState.toJson(state, snapshotPrettyFormat))
              changes = 0
            } else {
              changes += 1
            }
          }
          case Failure(error) => {
            execute(user, Left(changeSet.command), error)
            saveSnapshot(ModelState.toJson(state, snapshotPrettyFormat))
            changes = 0
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

  private var delayedPoisonPill: Cancellable = _

  @inline private def cancelPoisonPill(): Unit = {
    if (null != delayedPoisonPill) {
      delayedPoisonPill.cancel()
      delayedPoisonPill = null
    }
  }

  @inline private def delayPoisonPill(): Unit = {
    cancelPoisonPill()
    import scala.concurrent.duration._
    implicit val executionContext: ExecutionContext = context.dispatcher
    delayedPoisonPill = context.system.scheduler.scheduleOnce(30 second, self, PoisonPill) // self ! PoisonPill
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
    case StateActor.SubscriberFail(request, error) => {
      execute(sender(), Right(request), error) // should never happen (it's handled by the UserActor)
    }
    case StateActor.NoMoreSubscribers => {
      delayPoisonPill() // there is no more subscribers, take a while and kill itself
    }
  }

}
