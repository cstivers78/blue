import us.stivers.blue.finagle.{BlueService}
import us.stivers.blue.route.router.{FilteringRouter}
import com.twitter.finagle.builder.{ServerBuilder,Server}
import com.twitter.finagle.http.{RichHttp,Http,Request}
import java.net.{InetSocketAddress}

object main extends App {
  
  val server = ServerBuilder()
    .codec(RichHttp[Request](Http()))
    .bindTo(new InetSocketAddress(8080))
    .name("rails-like")
    .build(BlueService(FilteringRouter(routes)))  

  Console.readLine()
  server.close()
  System.exit(0)
}