
import com.typesafe.config.Config
import org.mentha.utils.archimate.state._

object Main {

  import akka.actor._
  import akka.http.scaladsl._
  import akka.http.scaladsl.server.Directives._
  import akka.http.scaladsl.server._
  import akka.pattern.ask
  import akka.stream._
  import akka.util.Timeout

  import scala.concurrent._
  import scala.concurrent.duration._

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

  private implicit val system: ActorSystem = ActorSystem("webArchimate")
  private implicit val mat: Materializer = ActorMaterializer()
  private implicit val executionContext: ExecutionContext = system.dispatcher
  private implicit val timeout: Timeout = Timeout(5 seconds)

  private val storageActor = system.actorOf(Props(new StorageActor()), name="storage")

  private def obtainStateActor(modelId: String) = {
    Await.result(
      storageActor
        .ask(StorageActor.Request(modelId))
        .map { case StorageActor.Response(ref) => ref },
      timeout.duration
    )
  }

  // TODO: https://www.playframework.com/documentation/2.6.x/ScalaWebSockets
  // TODO: https://github.com/playframework/play-scala-websocket-example
  //#websocket-request-handling
  private def modelWebSocket(modelId: String): Route = {
    extractUpgradeToWebSocket {
      upgrade => {
        val flow = UserActor.newWebSocketUser(modelId, obtainStateActor(modelId))
        complete { upgrade.handleMessages(flow, None) }
      }
    }
  }
  //#websocket-request-handling

//  private def modelREST(modelId: String): Route = {
//    post {
//      entity(as[String]) {
//        command => {
//          val flow = UserActor.newCommandFlow(modelId, obtainStateActor(modelId))
//
//          val source = Source.single[String](command).concatMat(Source.maybe[String])(Keep.right)
//          val sink = Sink.head[String]
//          val (p, f) = source.via(flow).toMat(sink)(Keep.both).run()
//
//          p.success(None)
//          complete { f }
//        }
//      }
//    }
//  }

  private val route =
    path("model" / Remaining) {
      modelId => modelWebSocket(modelId) ~
//        modelREST(modelId) ~
        get { getFromResource(s"public/index.html") }
    } ~ get {
      path(Remaining) { resource => getFromResource(s"public/${resource}") }
    }

  private val binding = Await.result(
    {
      val http = Http()
      // TODO: http.setDefaultServerHttpContext(getSSLContext(system.settings.config))
      http.bindAndHandle(route, interface = "0.0.0.0", port = 8088)
    },
    timeout.duration
  )

  sys.addShutdownHook {
    binding.unbind()
    system.terminate()
  }

  def main(args: Array[String]): Unit = {
  }

}
