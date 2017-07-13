import org.mentha.utils.archimate.state._

object Main {

  import akka.actor._
  import akka.stream._
  import akka.pattern.ask

  import akka.util.Timeout
  import scala.concurrent._
  import scala.concurrent.duration._

  import akka.http.scaladsl.Http
  import akka.http.scaladsl.server.Directives._

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("webArchimate")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext: ExecutionContext = system.dispatcher
    implicit val timeout = Timeout(5 seconds)

    val storageActor = system.actorOf(Props(new StorageActor()), name="storage")

    //#websocket-request-handling
    val route = path("model" / Remaining) { id =>
        handleWebSocketMessages {
          val actorRef = Await.result(
            storageActor
              .ask(StorageActor.Request(id))
              .map { case StorageActor.Response(ref) => ref },
            timeout.duration
          )
          UserActor.newUser(stateActor = actorRef)
        }
    }
    //#websocket-request-handling

    val bindingFuture = Http().bindAndHandle(route, interface = "localhost", port = 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    scala.io.StdIn.readLine()

    import system.dispatcher // for the future transformations
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

}
