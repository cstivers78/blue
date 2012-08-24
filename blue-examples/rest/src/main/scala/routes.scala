import us.stivers.blue.route.{Route,RouteBuilder}
import us.stivers.blue.route.parser.{Parsers}

object routes extends RouteBuilder with Parsers with Responders {
  val routes =
    Get -> "/add" ! ( query[Int]("a") & query[Int]("b") ^ {(_: Int) + (_: Int)} ) |
    Get -> "/sub" ! ( query[Int]("a") & query[Int]("b") ^ {(_: Int) - (_: Int)} ) 
}


trait Responders {

  import scalax.util.{Try}
  import us.stivers.blue.http.{Request,Response,Content}
  import us.stivers.blue.route.{Responder,Route}

  implicit def StringResponder: Responder[String] = new Responder[String] {
    def apply(req: Request, route: Route, s: String) = Try(Response(content = Content("text/plain",s)))
  }

  implicit def IntResponder: Responder[Int] = new Responder[Int] {
    def apply(req: Request, route: Route, i: Int) = Try(Response(content = Content("text/plain",i.toString)))
  }

}