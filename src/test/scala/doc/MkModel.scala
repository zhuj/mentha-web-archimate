package doc


/**
  *
  */
class MkModel {

  import org.mentha.utils.archimate.model._
  import org.mentha.utils.archimate.model.view.View._
  import org.mentha.utils.archimate.model.view._

  val SPACE = 40

  implicit class ImplicitViewNode[+T <: ViewNode](node: T) {

    def placeUp(v: ViewNode): T = node.withPosition(Point(
      x = v.position.x,
      y = v.position.y - (v.size.height + node.size.height) / 2 - SPACE
    ))

    def placeDown(v: ViewNode): T = node.withPosition(Point(
      x = v.position.x,
      y = v.position.y + (v.size.height + node.size.height) / 2 + SPACE
    ))

    def placeLeft(v: ViewNode): T = node.withPosition(Point(
      x = v.position.x - (v.size.width + node.size.width) / 2 - SPACE,
      y = v.position.y
    ))

    def placeRight(v: ViewNode): T = node.withPosition(Point(
      x = v.position.x + (v.size.width + node.size.width) / 2 + SPACE,
      y = v.position.y
    ))

  }

  implicit class ImplicitViewEdge[+T <: ViewEdge](edge: T) {

    def route(route: (Double, Double)*): T = edge.withPoints {
      route.foldLeft((edge.source.position, List[Point]())) {
        case ((sp, seq), (x, y)) => {
          val p = Point(sp.x + x, sp.y + y)
          (p, p :: seq)
        }
      } match {
        case (p, b) => b.reverse
      }
    }

  }


  def c[T <: Concept](t: ViewObject with ViewConcept[T]): T = t.concept

  def in(view: View) = new {
    def node[T <: NodeConcept](r: => NodeConcept with T) = r.attach(view)
    def edge[T <: Relationship](r: => Relationship with T) = r.attach(view)
  }

  private def sendWebSocketMessage(id: String, message: String): Unit = {

    import akka.Done
    import akka.actor._
    import akka.http.scaladsl.Http
    import akka.http.scaladsl.model.ws._
    import akka.stream.ActorMaterializer
    import akka.stream.scaladsl._

    implicit val system = ActorSystem()
    implicit val executionContext = system.dispatcher
    implicit val materializer = ActorMaterializer()

    val sink = Sink.foreach[Message] {
        case message: TextMessage.Strict => println(message.text)
        case message => println(message)
    }

    val source = Source.single[Message]( TextMessage(message) )
    val flow = Flow.fromSinkAndSourceMat(sink, source)(Keep.left)
    val (upgradeResponse, closed) = Http().singleWebSocketRequest(WebSocketRequest(s"ws://127.0.0.1:8088/model/${id}"), flow)
    val connected = upgradeResponse.map { _ => Done }

    connected.onComplete(_ => system.terminate())
    closed.foreach(_ => println("closed"))
  }

  def publishModel(model: Model): Unit = {
    import play.api.libs.json.Json
    val js = Json.obj( "set-model" -> json.toJsonPair(model) )
    sendWebSocketMessage(model.id, js.toString)
  }

}
