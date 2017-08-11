package org.mentha.utils.archimate.state

import akka.actor._
import akka.util._
import akka.pattern.ask


object StorageActor {

  case class Request(modelId: String)
  case class Response(stateActorRef: ActorRef)

}

/**
  * It creates StateActor on demand
  */
class StorageActor extends Actor with ActorLogging {

  import scala.util._
  import scala.concurrent._
  import scala.concurrent.duration._

  private implicit val timeoutDuration = (1 seconds)
  private implicit val timeout = Timeout(timeoutDuration)

  override def receive: Receive = akka.event.LoggingReceive {
    case StorageActor.Request(modelId) => {
      implicit val executionContext: ExecutionContext = context.dispatcher
      val origin = sender()
      val actorName = s"stateActor-${modelId}"
      context
        .actorSelection(self.path / actorName)
        .ask { Identify(java.lang.Long.toUnsignedString(System.currentTimeMillis(),32)) }
        .map { case ActorIdentity(_, refOpt) => refOpt }
        .map {
          case Some(ref: ActorRef) => ref
          case None => context.actorOf(props(modelId), name = actorName)
        }
        .andThen {
          case Success(ref) => origin ! StorageActor.Response(ref)
          case Failure(f) => log.error(f, f.getMessage)
        }
    }
  }

  private def props(modelId: String) = {
    Props(new StateActor(modelId))
  }
}
