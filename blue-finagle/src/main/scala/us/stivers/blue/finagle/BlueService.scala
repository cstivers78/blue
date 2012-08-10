package us.stivers.blue.finagle

import us.stivers.blue.http.{Request=>BlueRequest,Response=>BlueResponse,Cookie=>BlueCookie}
import us.stivers.blue.route.{Router,RouteException}

import com.twitter.finagle.{Service}
import com.twitter.finagle.http.{Request,Response}
import com.twitter.util.{Future}
import org.jboss.netty.handler.codec.http.{Cookie}
import scala.collection.immutable.{Set}
import scala.collection.JavaConverters._

/**
 *
 *  ServerBuilder()
 *    .codec(RichHttp[Request](Http()))
 *    .bindTo(new InetSocketAddress(8080))
 *    .name("example")
 *    .build(BlueService(Router(routes)))
 */
case class BlueService(router: Router) extends Service[Request,Response] {
  
  def apply(request: Request): Future[Response] = {
    for {
      req <- Future(toBlueRequest(request))
      route <- Future(router(req).get)
      res <- Future(route(req).get)
    } yield res
  } rescue {
    case RouteException(req,status) => 
      Future(BlueResponse(status=status))
    case e: Throwable => 
      e.printStackTrace
      Future(BlueResponse(status=us.stivers.blue.http.Status.InternalServerError))
  } map (fromBlueResponse)

  val toBlueCookie: Cookie=>BlueCookie = (cookie: Cookie) => BlueCookie(
    name      = cookie.getName,
    value     = cookie.getValue,
    version   = cookie.getVersion,
    maxAge    = Option(cookie.getMaxAge),
    path      = Option(cookie.getPath),
    domain    = Option(cookie.getDomain),
    ports     = Set.empty ++ (cookie.getPorts.asScala.map(_.toInt)),
    comment   = Option(cookie.getComment),
    secure    = cookie.isSecure,
    discard   = cookie.isDiscard
  )

  val fromBlueCookie: BlueCookie=>Cookie = (cookie: BlueCookie) => {
    import org.jboss.netty.handler.codec.http.{DefaultCookie}
    val c = new DefaultCookie(cookie.name, cookie.value)
    c.setVersion(cookie.version)
    cookie.maxAge.foreach(c.setMaxAge)
    cookie.path.foreach(c.setPath)
    cookie.domain.foreach(c.setDomain)
    c.setPorts(cookie.ports.toIterable.map(i => i:java.lang.Integer).asJava)
    cookie.comment.foreach(c.setComment)
    c.setSecure(cookie.secure)
    c.setDiscard(cookie.discard)
    c
  }

  val toBlueRequest: Request=>BlueRequest = (req: Request) => {
    import us.stivers.blue.http.{Version,Method,Header,Content}
    import us.stivers.blue.uri.{URI}
    BlueRequest(
      version = Version(req.version.toString),
      method  = Method(req.method.toString),
      uri     = URI(req.getUri),
      headers = req.headers.keys.map(k => Header(k)->req.headers.getAll(k).toList).toMap[Header,List[String]],
      cookies = Set.empty ++ req.cookies.toList.map(toBlueCookie).toIterable,
      content = Content(req.contentType,req.content.toByteBuffer)
    )
  }

  val fromBlueResponse: BlueResponse=>Response = (res: BlueResponse) => {
    import org.jboss.netty.handler.codec.http.{HttpVersion,HttpResponseStatus}
    import org.jboss.netty.buffer.{ChannelBuffers}
    val r = Response()
    r.version = HttpVersion.valueOf(res.version.value)
    r.status  = HttpResponseStatus.valueOf(res.status.code)
    r.headers ++= res.headers.toList.flatMap(kv => kv._2.map(kv._1.name -> _))
    r.cookies ++= res.cookies.map(fromBlueCookie)
    res.content.contentType.foreach(r.contentType = _)
    r.content = ChannelBuffers.wrappedBuffer(res.content.buffer)
    r
  }

}