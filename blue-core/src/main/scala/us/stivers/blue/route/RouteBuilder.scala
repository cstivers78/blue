package us.stivers.blue.route

import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Request,Response,Method,Status}
import us.stivers.blue.uri.{Path}
import Route._

trait RouteBuilder {

  implicit def MethodToRouteMethod(m: Method): RouteMethod = RouteMethod(m)
  implicit def SegmentListToRoutePath(p: List[Segment]): RoutePath = RoutePath(p)
  implicit def RouteToRouteSet(route: Route): RouteSet = RouteSet(route)
  implicit def StringToSegmentList(path: String): List[Segment] = Segment.parse(path)

  case class RouteMethod(method: Method) {
    def ->(path: List[Segment]): RouteMethodAndPath = RouteMethodAndPath(method, path)
    def ![A: Responder](handler: A): Route = Route(method, List.empty, handler)
  }

  case class RoutePath(path: List[Segment]) {
    def ![A: Responder](handler: A): Route = Route(path, handler)
  }

  case class RouteMethodAndPath(method: Method, path: List[Segment]) {
    def ![A: Responder](handler: A): Route = Route(method, path, handler)
  }
}