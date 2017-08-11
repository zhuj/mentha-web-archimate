package org.mentha.utils.archimate.state

import akka.NotUsed
import akka.actor._
import akka.http.scaladsl.model.ws
import akka.stream.OverflowStrategy
import akka.stream.scaladsl._

import scala.util._

/**
  *
  */
object UserActor {
  case class Connected(outgoing: ActorRef)
  case class IncomingMessage(text: String)
  case class OutgoingMessage(text: String)

  //#websocket-flow
  def newUser(modelId: String, stateActor: ActorRef)(implicit system: ActorSystem): Flow[ws.Message, ws.Message, NotUsed] = {

    // new connection - new user actor
    val userActor = system.actorOf(Props(new UserActor(modelId, stateActor)))

    // new actor (it will send incoming messages to the userActor)
    // also it will kill the userActor on complete of the input stream
    val actorRefSink = Sink.actorRef[UserActor.IncomingMessage](userActor, PoisonPill)

    // incoming stream (will convert messages and send it to actorRefSink)
    import scala.concurrent.duration._
    val in = Flow[ws.Message]
      .collect { case ws.TextMessage.Strict(text) => UserActor.IncomingMessage(text) }
      .keepAlive( 45 seconds, () => UserActor.IncomingMessage("{}") ) // TODO: make it configured
      .to { actorRefSink }

    // jet another actor (it will subscribe to the userActor answers)
    val out = Source.actorRef[AnyRef](bufferSize = 16, OverflowStrategy.fail)
      .mapMaterializedValue { outActor => userActor ! UserActor.Connected(outActor); NotUsed }
      .collect { case response: UserActor.OutgoingMessage => ws.TextMessage(response.text) }

    // return the flow
    Flow.fromSinkAndSource(in, out)
  }
  //#websocket-flow

}

/**
  * It parses the incoming message (text) to a request, then sends it to the StateActor
  * @param stateActor
  */
class UserActor(modelId: String, stateActor: ActorRef) extends Actor {

  def receive: Receive = {
    case UserActor.Connected(outgoing) => context.become(connected(outgoing))
  }

  private def connected(outgoing: ActorRef): Receive = {
    // subscribe self first
    stateActor ! StateActor.SubscriberJoin

    // then return the new receive handler
    {
      case UserActor.IncomingMessage(text) => {
        Try { ModelState.parseMessage(text) } match {
          case Success(request) => stateActor ! StateActor.SubscriberSend(request)
          case Failure(error) => if (true) {
            outgoing ! UserActor.OutgoingMessage(
              ModelState.Responses.ModelStateError(modelId, Right(text), error).toJson.toString
            )
          } else {
            stateActor ! StateActor.SubscriberFail(text, error)
          }
        }
      }

      case response: ModelState.Response => {
        outgoing ! UserActor.OutgoingMessage(response.toJson.toString)
      }
    }
  }

}
