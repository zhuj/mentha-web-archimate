package org.mentha.utils.archimate.model.utils

import java.util.Random
import java.util.concurrent.atomic.AtomicLong

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.mentha.utils.archimate.model._

/**
  *
  */
abstract class MkModel {

  // set sequential time source
  def initTimeSource(base: Long): Unit = {
    val source = new AtomicLong(base & 0xff)
    val random = new Random(base)
    Identifiable.timeSource.value = () => source.incrementAndGet() | ((random.nextInt() & 0xffff) << 24)
  }

  // set sequential time source (default - 0)
  initTimeSource(0l)

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
    val maybe = Source.maybe[Message]
    val outgoing = Source(TextMessage(message) :: Nil).concatMat(maybe)(Keep.right)
    val flow = Flow.fromSinkAndSourceMat(incoming, outgoing)(Keep.both)
    val (upgradeResponse, (closed, promise)) = Http().singleWebSocketRequest(WebSocketRequest(s"ws://127.0.0.1:8088/model/${id}"), flow)

    closed.foreach(_ => println("closed"))
    upgradeResponse.onComplete(_ => {})

    Thread.sleep(1500)
    promise.success(None)

    Thread.sleep(500)
    system.terminate()
  }

  def publishModel(model: Model): Unit = {
    import play.api.libs.json.Json
    val js = Json.obj( "set-model" -> json.toJsonPair(model) )
    sendWebSocketMessage(model.id, js.toString)
  }

}
