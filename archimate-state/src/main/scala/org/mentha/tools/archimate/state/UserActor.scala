package org.mentha.tools.archimate.state

import akka._
import akka.actor._
import akka.http.scaladsl.model.ws
import akka.stream._
import akka.stream.scaladsl._

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._

/**
  *
  */
object UserActor {

  private case class Connected(outgoing: ActorRef)
  case class IncomingMessage(text: String)
  case class OutgoingMessage(text: String)

  //#websocket-flow
  def newWebSocketUserFlow(
    modelId: String,
    stateActor: ActorRef,
    dataTransferTimeout: FiniteDuration,
    keepAliveInterval: FiniteDuration
  )(implicit system: ActorSystem, materializer: Materializer): Flow[ws.Message, ws.Message, NotUsed] = {
    implicit val executionContext: ExecutionContext = system.dispatcher
    def collect(stream: Source[String, _]) = stream
        .limit(1000)
        .completionTimeout(dataTransferTimeout)
        .runFold("") { _ + _ }
        .map { text => UserActor.IncomingMessage(text) }

    newFlow[ws.Message, ws.Message](
      modelId = modelId,
      stateActor = stateActor,
      inCollector = {
        case ws.TextMessage.Strict(text) => Future.successful(UserActor.IncomingMessage(text))
        case ws.TextMessage.Streamed(stream) => collect { stream }
        case ws.BinaryMessage.Strict(data) => akka.http.scaladsl.coding.Gzip.decode(data).map { msg => UserActor.IncomingMessage(msg.utf8String) }
        case ws.BinaryMessage.Streamed(stream) => collect { stream.via(akka.http.scaladsl.coding.Gzip.decoderFlow).map(_.utf8String) }
      },
      outCollector = {
        case UserActor.OutgoingMessage(response) => ws.TextMessage(response)
      },
      keepAliveInterval = keepAliveInterval
    )
  }
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
    inCollector: PartialFunction[I, Future[UserActor.IncomingMessage]],
    outCollector: PartialFunction[UserActor.OutgoingMessage, O],
    keepAliveInterval: FiniteDuration
  )(implicit system: ActorSystem): Flow[I, O, NotUsed] = {

    // new connection - new user actor
    val userActor = system.actorOf(Props(new UserActor(modelId, stateActor)))

    // new actor (it will send incoming messages to the userActor), also it will kill the userActor on complete of the input stream
    val actorRefSink = Sink.actorRef[UserActor.IncomingMessage](userActor, PoisonPill)

    // incoming stream (will convert messages and send it to actorRefSink)
    val in = Flow[I]
      .collect { inCollector }
      .mapAsync(parallelism = 2)(identity)
      .keepAlive( keepAliveInterval, () => UserActor.IncomingMessage("{}") )
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
