package org.mentha.utils.archimate.state

import akka.NotUsed
import akka.actor._
import akka.http.scaladsl.model.ws
import akka.stream._
import akka.stream.scaladsl._
import akka.util.Timeout

import scala.concurrent._
import scala.util._


/**
  *
  */
object UserActor {
  private case class Connected(outgoing: ActorRef)
  case class IncomingMessage(text: String)
  case class OutgoingMessage(text: String)

  //#websocket-flow
  def newWebSocketUser(modelId: String, stateActor: ActorRef)(implicit system: ActorSystem): Flow[ws.Message, ws.Message, NotUsed] = newFlow[ws.Message, ws.Message](
    modelId = modelId,
    stateActor = stateActor,
    inCollector = { case ws.TextMessage.Strict(text) => UserActor.IncomingMessage(text) },
    outCollector = { case UserActor.OutgoingMessage(response) => ws.TextMessage(response) }
  )(system)
  //#websocket-flow

//  def newCommandFlow(modelId: String, stateActor: ActorRef)(implicit system: ActorSystem): Flow[String, String, NotUsed] = newFlow[String, String](
//    modelId,
//    stateActor,
//    inCollector = { case text: String => UserActor.IncomingMessage(text)  },
//    outCollector = { case UserActor.OutgoingMessage(response) => response }
//  )(system)

  private def newFlow[I, O](
    modelId: String,
    stateActor: ActorRef,
    inCollector: PartialFunction[I, UserActor.IncomingMessage],
    outCollector: PartialFunction[UserActor.OutgoingMessage, O]
  )(implicit system: ActorSystem): Flow[I, O, NotUsed] = {

    // new connection - new user actor
    val userActor = system.actorOf(Props(new UserActor(modelId, stateActor)))

    // new actor (it will send incoming messages to the userActor)
    // also it will kill the userActor on complete of the input stream
    val actorRefSink = Sink.actorRef[UserActor.IncomingMessage](userActor, PoisonPill)

    // incoming stream (will convert messages and send it to actorRefSink)
    import scala.concurrent.duration._
    val in = Flow[I]
      .collect { inCollector }
      .keepAlive( 45 seconds, () => UserActor.IncomingMessage("{}") ) // TODO: make it configured
      .to { actorRefSink }

    // jet another actor (it will subscribe to the userActor answers)
    val out = Source.actorRef[UserActor.OutgoingMessage](bufferSize = 16, OverflowStrategy.fail)
      .mapMaterializedValue { outActor => userActor ! UserActor.Connected(outActor) }
      .collect { outCollector }

    // return the flow
    Flow.fromSinkAndSource(in, out)
  }

}

/**
  * It parses the incoming message (text) to a request, then sends it to the StateActor
  * @param stateActor
  */
class UserActor(modelId: String, stateActor: ActorRef) extends Actor with ActorLogging {

  def receive: Receive = /*akka.event.LoggingReceive*/ {
    case UserActor.Connected(outgoing) => {
      context.become { connected(outgoing) }
    }
  }

  private def connected(outgoing: ActorRef): Receive = akka.event.LoggingReceive {
    // subscribe self first
    require(null != outgoing)
    stateActor ! StateActor.SubscriberJoin

    // then return the new receive handler
    {
      case UserActor.IncomingMessage(text) => {
        Try { ModelState.parseMessage(text) } match {
          case Success(request) => stateActor ! StateActor.SubscriberSend(request)
          case Failure(error) => outgoing ! UserActor.OutgoingMessage( ModelState.Responses.ModelStateError(modelId, Right(text), error).toJson.toString )
        }
      }

      case response: ModelState.Response => {
        outgoing ! UserActor.OutgoingMessage(response.toJson.toString)
      }
    }
  }

}
