package us.stivers.blue.route.router

import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Request,Method,Status}
import us.stivers.blue.route.{Route, Router, RouteException, Segment}
import scalaz.Scalaz._

case class FilteringRouter(r: Iterable[Route]) extends Router with RouteOrdering {

  val routes = r.toList.sorted

  protected def path(req: Request) = (route: Route) => {
    import Segment._

    // We zip the route path and request path segments
    // This will result in a list a long as the shortest list.
    // We will then take only segments which "match".
    val zipped = route.path.zip(Segment.parse(req.uri.path)).takeWhile{
      case (Value(a),Value(b))  => a==b
      case (Named(a),Value(b))  => true
      case (WildCard,Value(b))  => true
      case _                    => false
    }

    val asize = zipped.size
    val bsize = req.uri.path.segments.size
    val csize = route.path.size

    if ( asize == bsize && bsize == csize ) {
      // If the sizes are the all the same, then it is a match
      true
    }
    else if ( asize <= bsize && zipped.lastOption.exists(_._1 == WildCard)) {
      // If the zipped list is shorter and it's last element is a wildcard, then it is a match
      true
    }
    else {
      false
    }
  }

  protected def method(req: Request) = (route: Route) => route.method match {
    case None         => true
    case Some(method) => method == req.method
  }

  protected def select(req: Request): Try[Seq[Route]] = {
    routes.filter(path(req)) match {
      case Nil        => Failure(RouteException(req,Status.NotFound))
      case routes     => routes.filter(method(req)) match {
        case Nil      => Failure(RouteException(req,Status.MethodNotAllowed))
        case routes   => Success(routes)
      }
    }
  }

  def apply(req: Request): Try[Route] = select(req) match {
    case Success(route :: _)    => Success(route)
    case Failure(e)             => Failure(e)
    case _                      => Failure(RouteException(req, Status.InternalServerError))
  }
}


private[router] trait RouteOrdering {

  import scala.math.{Ordering}

  protected implicit val SegmentOrdering: Ordering[Segment] = new Ordering[Segment] {
    import Segment._
    def compare(x: Segment, y: Segment): Int = (x,y) match {
      case (Value(a),Value(b)) => b.compareTo(a)
      case (Value(_),Named(_)) => -1
      case (Value(_),WildCard) => -2
      case (Named(_),Value(_)) => 1
      case (Named(a),Named(b)) => b.compareTo(a)
      case (Named(_),WildCard) => -1
      case (WildCard,Value(_)) => 2
      case (WildCard,Named(_)) => 1
      case (WildCard,WildCard) => 0
    }
  }
  
  protected implicit def MethodOrdering: Ordering[Method] = new Ordering[Method] {
    def compare(x: Method, y: Method): Int = x.toString.compareTo(y.toString)
  }

  protected implicit def SegmentListOrdering: Ordering[List[Segment]] = new Ordering[List[Segment]] {
    def compare(x: List[Segment], y: List[Segment]): Int = x.zip(y).map(xy => implicitly[Ordering[Segment]].compare(xy._1,xy._2)).find(_ != 0).getOrElse(y.size - x.size)
  }

  protected implicit def RouteOrdering: Ordering[Route] = new Ordering[Route] {
    def compare(x: Route, y: Route): Int = {
      implicitly[Ordering[List[Segment]]].compare(x.path,y.path) match {
        case 0 => (x.method,y.method) match {
          case (Some(_),None) => 1
          case (None,Some(_)) => -1
          case _ => 0
        }
        case i => i
      }
    }
  }
}