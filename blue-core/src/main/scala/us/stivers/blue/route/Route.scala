package us.stivers.blue.route

import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Request,Response,Method,Status}

/**
 * A Route is able to route an HTTP Request to a Response
 */
trait Route extends (Request=>Try[Response]) {
  def method: Option[Method]
  def path: List[Segment]
}

object Route {

  def apply[A: Responder](method: Method, handler: A): Route = DefaultRoute(Some(method), List.empty, handler)
  def apply[A: Responder](method: Method, path: List[Segment], handler: A): Route = DefaultRoute(Some(method), path, handler)
  def apply[A: Responder](path: List[Segment], handler: A): Route = DefaultRoute(None, path, handler)
  
  case class DefaultRoute[A: Responder](method: Option[Method], path: List[Segment], handler: A) extends Route {
    def apply(req: Request): Try[Response] = implicitly[Responder[A]].apply(req, this, handler)
  }  

}