
import us.stivers.blue.route._
import controllers._

object routes extends RouteSet with RouteBuilder with Responders { val routes = 
  Get   -> "/add"                               ! query[Int]("a") & query[Int]("b") ^ Math.add |
  Get   -> "/subtract"                          ! query[Int]("a") & query[Int]("b") ^ Math.subtract |
  Get   -> "/square"                            ! query[Int]("a") ^ Math.square
}

trait Responders {

  import scalax.util.{Try}
  import us.stivers.blue.http.{Request,Response,Content}
  
  implicit val StringResponder: Responder[Int] = new Responder[Int] {
    def apply(req: Request, route: Route, num: Int): Try[Response] = Try(Response(content = Content("text/plain",num)))
  } 
}