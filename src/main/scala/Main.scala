
import com.typesafe.config.Config
import org.mentha.utils.archimate.state._

object Main {

  import akka.actor._
  import akka.stream._
  import akka.pattern.ask

  import akka.util.Timeout
  import scala.concurrent._
  import scala.concurrent.duration._

  import akka.http.scaladsl._
  import akka.http.scaladsl.server.Directives._


  def getSSLContext(config: Config) : HttpsConnectionContext = {

    import java.nio.file._
    import java.security._
    import javax.net.ssl._

    val ks: KeyStore = KeyStore.getInstance("JKS")

    val keystorePath = config.getString("keystore.path")
    val keystorePassword = config.getString("keystore.password").toCharArray

    val keystore = Files.newInputStream(Paths.get(keystorePath))

    require(keystore != null, "Keystore required!")
    ks.load(keystore, keystorePassword)

    val keyManagerFactory: KeyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(ks, keystorePassword)

    val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    tmf.init(ks)

    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, tmf.getTrustManagers, new SecureRandom)
    ConnectionContext.https(sslContext)
  }

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("webArchimate")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext: ExecutionContext = system.dispatcher
    implicit val timeout = Timeout(5 seconds)

    val storageActor = system.actorOf(Props(new StorageActor()), name="storage")

    // TODO: https://www.playframework.com/documentation/2.6.x/ScalaWebSockets
    // TODO: https://github.com/playframework/play-scala-websocket-example

    //#websocket-request-handling
    val route = path("model" / Remaining) { id =>
        handleWebSocketMessages {
          UserActor.newUser(
            modelId = id,
            stateActor = Await.result(
              storageActor
                .ask(StorageActor.Request(id))
                .map { case StorageActor.Response(ref) => ref },
              timeout.duration
            )
          )
        }
    }
    //#websocket-request-handling

    val http = Http()
    // TODO: http.setDefaultServerHttpContext(getSSLContext(system.settings.config))

    val bindingFuture = http.bindAndHandle(route, interface = "0.0.0.0", port = 8088)

    println(s"Server online at http://localhost:8088/model/{model}\nPress RETURN to stop...")
    scala.io.StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

}
