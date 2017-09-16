package org.mentha.utils.archimate.model.utils

import java.util.concurrent.atomic.AtomicLong

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.mentha.utils.archimate.model._

import scala.concurrent.duration._

/**
  *
  */
abstract class MkModel {

  // set sequential time source
  {
    val source = new AtomicLong(0l)
    Identifiable.timeSource.value = () => source.incrementAndGet()
  }

  private def sendWebSocketMessage(id: String, message: String): Unit = {

    implicit val system = ActorSystem()
    implicit val executionContext = system.dispatcher
    implicit val materializer = ActorMaterializer()

    import akka.http.scaladsl.Http
    import akka.http.scaladsl.model.ws._
    import akka.stream.scaladsl._

    val incoming = Sink.foreach[Message] {
        case message: TextMessage.Strict => println(">> RESPONSE: " + message.text)
        case message => println(">> RESPONSE: " + message)
    }

    // see: http://doc.akka.io/docs/akka-http/10.0.0/scala/http/client-side/websocket-support.html#half-closed-websockets
    val outgoing = Source(TextMessage(message) :: Nil).concatMat(Source.maybe[Message])(Keep.right)
    val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest(s"ws://127.0.0.1:8088/model/${id}"))
    val (upgradeResponse, closed) = outgoing
      .viaMat(webSocketFlow)(Keep.right)
      .toMat(incoming)(Keep.both)
      .run()

    closed.foreach(_ => println("closed"))
    upgradeResponse.onComplete(_ => {
      Thread.sleep(1000)
      system.terminate()
    })
  }

  def publishModel(model: Model): Unit = {
    import play.api.libs.json.Json
    val js = Json.obj( "set-model" -> json.toJsonPair(model) )
    sendWebSocketMessage(model.id, js.toString)
  }

}
