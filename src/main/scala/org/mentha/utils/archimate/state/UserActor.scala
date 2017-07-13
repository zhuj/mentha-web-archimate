package org.mentha.utils.archimate.state

import akka.NotUsed
import akka.actor._
import akka.http.scaladsl.model.ws
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.util._

object UserActor {
  case class Connected(outgoing: ActorRef)
  case class IncomingMessage(text: String)
  case class OutgoingMessage(text: String)

  //#websocket-flow
  def newUser(stateActor: ActorRef)(implicit system: ActorSystem): Flow[ws.Message, ws.Message, NotUsed] = {

    // new connection - new user actor
    val userActor = system.actorOf(Props(new UserActor(stateActor)))

    // new actor (it will send incoming messages to the stateModel)
    val actorRefSink = Sink.actorRef[UserActor.IncomingMessage](userActor, PoisonPill)

    // incoming stream
    val in = Flow[ws.Message]
      .collect { case ws.TextMessage.Strict(text) => UserActor.IncomingMessage(text) }
      .to { actorRefSink }

    // jet another actor (it will subscribe to the stateModel answers)
    val out = Source.actorRef[AnyRef](bufferSize = 10, OverflowStrategy.fail)
      .mapMaterializedValue { outActor => userActor ! UserActor.Connected(outActor); NotUsed }
      .collect { case response: UserActor.OutgoingMessage => ws.TextMessage(response.text) }

    // return the flow
    Flow.fromSinkAndSource(in, out)
  }
  //#websocket-flow

}

class UserActor(stateActor: ActorRef) extends Actor {

  def receive: Receive = {
    case UserActor.Connected(outgoing) => context.become(connected(outgoing))
  }

  private def connected(outgoing: ActorRef): Receive = {
    // subscribe self first
    stateActor ! StateActor.SubscriberJoin()

    // then return the new receive handler
    {
      case UserActor.IncomingMessage(text) => {
        val msg = Try { ModelState.parseMessage(text) } match {
          case Success(request) => StateActor.SubscriberSend(request)
          case Failure(error) => StateActor.SubscriberFail(error)
        }
        stateActor ! msg
      }

      case response: ModelState.Response => {
        outgoing ! UserActor.OutgoingMessage(response.toJson.toString)
      }
    }
  }

}
