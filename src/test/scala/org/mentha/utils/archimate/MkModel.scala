package org.mentha.utils.archimate

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

/**
  *
  */
class MkModel {

  import org.mentha.utils.archimate.model._

  private lazy implicit val system = ActorSystem()

  private def sendWebSocketMessage(id: String, message: String, finish: Boolean = true): Unit = {

    implicit val executionContext = system.dispatcher
    implicit val materializer = ActorMaterializer()

    Thread.sleep(150)

    import akka.Done
    import akka.actor._
    import akka.http.scaladsl.Http
    import akka.http.scaladsl.model.ws._
    import akka.stream.ActorMaterializer
    import akka.stream.scaladsl._

    val sink = Sink.foreach[Message] {
        case message: TextMessage.Strict => println(">> RESPONSE: " + message.text)
        case message => println(">> RESPONSE: " + message)
    }

    val source = Source.single[Message]( TextMessage(message) )
    val flow = Flow.fromSinkAndSourceMat(sink, source)(Keep.left)
    val (upgradeResponse, closed) = Http().singleWebSocketRequest(WebSocketRequest(s"ws://127.0.0.1:8088/model/${id}"), flow)
    val connected = upgradeResponse.map { _ => Done }

    Thread.sleep(150)

    if (finish) {
      connected.foreach(_ => terminate())
      closed.foreach(_ => println("closed"))
    }
  }

  def terminate(): Unit = {
    Thread.sleep(500)
    system.terminate()
  }

  def publishModel(model: Model, finish: Boolean = true): Unit = {
    import play.api.libs.json.Json
    val js = Json.obj( "set-model" -> json.toJsonPair(model) )
    sendWebSocketMessage(model.id, js.toString, finish)
  }

}
