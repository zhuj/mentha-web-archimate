package org.mentha.utils.archimate.state

import akka.actor._
import akka.util._
import akka.pattern.ask

object StorageActor {

  case class Request(id: String)
  case class Response(stateActorRef: ActorRef)

}

class StorageActor extends Actor with ActorLogging {

  import scala.concurrent._
  import scala.concurrent.duration._

  private implicit val timeoutDuration = (1 seconds)
  private implicit val timeout = Timeout(timeoutDuration)

  override def receive: Receive = akka.event.LoggingReceive {
    case StorageActor.Request(id) => {
      implicit val executionContext: ExecutionContext = context.dispatcher
      val actorName = s"stateActor-${id}"
      val actorRef = Await.result(
        context
          .actorSelection(self.path / actorName)
          .ask(Identify(""))
          .map { case ActorIdentity(_, refOpt) => refOpt },
        timeoutDuration
      ) match {
        case Some(ref) => ref
        case None => context.actorOf(Props(new StateActor(id)), name = actorName)
      }
      sender() ! StorageActor.Response(actorRef)
    }
  }

}
