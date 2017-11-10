package org.mentha.tools.archimate.model.utils

import java.util.Random
import java.util.concurrent.atomic.AtomicLong

import akka.actor.ActorSystem
import akka.stream._
import akka.util.ByteString
import org.mentha.tools.archimate.model._

import scala.concurrent._
import scala.concurrent.duration._

/**
  *
  */
abstract class MkModel {

  // set sequential time source
  def initTimeSource(base: Long): Unit = {
    val source = new AtomicLong(base & 0xff)
    val random = new Random(base)
    Identifiable.timeSource.value = () => (source.incrementAndGet() << 16) | (random.nextInt() & 0xffff)
  }

  // set sequential time source (default - 0)
  initTimeSource(0l)

  private def sendWebSocketMessage(id: String, message: String): Unit = {

    implicit val system: ActorSystem = ActorSystem()
    implicit val executionContext: ExecutionContext = system.dispatcher
    implicit val materializer: Materializer = ActorMaterializer()

    val log = system.log

    import akka.http.scaladsl.Http
    import akka.http.scaladsl.model.ws._
    import akka.stream.scaladsl._

    // see: https://stackoverflow.com/questions/36535143/consuming-both-strict-and-streamed-websocket-messages-in-akka
    val counter = new AtomicLong(0)
    val incoming = Flow[Message]
      .collect {
        case message: TextMessage.Strict => Future.successful(s"${counter.incrementAndGet()}: ${message.text}")
        case message: TextMessage.Streamed => message.textStream.limit(1000).completionTimeout(10 seconds).runFold(s"${counter.incrementAndGet()}: ")(_ + _)
        case _ => Future.successful(s"${counter.incrementAndGet()}: ${message}")
      }
      .mapAsync(parallelism = 2)(identity)
      .toMat(Sink.foreach[String]( x => log.debug(s"RESP: ${x}") ))(Keep.right)

    val payload = {
      akka.http.scaladsl.coding.Gzip.encode(ByteString(message))
    }

    // see: http://doc.akka.io/docs/akka-http/10.0.0/scala/http/client-side/websocket-support.html#half-closed-websockets
    val maybe = Source.maybe[Message]
    val outgoing = Source(BinaryMessage(payload) :: Nil).concatMat(maybe)(Keep.right)
    val flow = Flow.fromSinkAndSourceMat(incoming, outgoing)(Keep.both)
    val (upgradeResponse, (closed, promise)) = Http().singleWebSocketRequest(WebSocketRequest(s"ws://127.0.0.1:8088/model/${id}"), flow)

    closed.foreach(_ => log.debug("DBG: closed"))
    upgradeResponse.onComplete(_ => log.debug("DBG: complete"))

    log.debug("DBG: setup complete")

    Thread.sleep(2000)
    promise.success(None)

    log.debug("DBG: promise complete")

    Thread.sleep(1000)
    system.terminate()

    log.debug("DBG: terminated")
  }

  def publishModel(model: Model): Unit = {
    import play.api.libs.json.Json

    val p = json.toJsonPair(model)
    json.fromJsonPair(p) // check if it works

    val js = Json.obj( "set-model" -> p )
    sendWebSocketMessage(model.id, js.toString)
  }

}
