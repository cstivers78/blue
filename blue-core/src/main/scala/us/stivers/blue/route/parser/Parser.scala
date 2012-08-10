package us.stivers.blue.route.parser

import scalax.util.{Try,Success,Failure}
import us.stivers.blue.codec.{ContentDecoder,ParameterDecoder}
import us.stivers.blue.http.{Request}
import us.stivers.blue.route.{Route,Segment}

/**
 * Parses a value from a Request and Route.
 */
trait Parser[A] extends ((Request,Route) => Try[A])

/**
 * Build in Parsers
 */
object Parser {

  /**
   * "Factory" method. This is for convenience in creating a new Parser.
   */
  def apply[A](f: (Request,Route) => Try[A]): Parser[A] = new Parser[A] {
    def apply(req: Request, route: Route) = f(req, route)
  }

  case class Parser1[A](curr: Parser[A]) extends Parser[A] {

    def apply(req: Request, route: Route) = curr(req,route)

    def ^[T](f: A=>T) = Parser{ this(_,_).map(f) }

    def &[N](n: Parser[N]) = Parser2(this, n)
  }

  case class Parser2[A,B](prev: Parser[A], curr: Parser[B]) extends Parser[(A,B)] {

    def apply(req: Request, route: Route) = for {
      a <- prev(req,route)
      b <- curr(req,route)
    } yield (a,b)

    def ^[T](f: (A,B)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser3(this, n)
  }

  case class Parser3[A,B,C](prev: Parser[(A,B)], curr: Parser[C]) extends Parser[(A,B,C)] {

    def apply(req: Request, route: Route) = for {
      (a,b) <- prev(req,route)
      c <- curr(req,route)
    } yield (a,b,c)

    def ^[T](f: (A,B,C)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser4(this, n)
  }

  case class Parser4[A,B,C,D](prev: Parser[(A,B,C)], curr: Parser[D]) extends Parser[(A,B,C,D)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c) <- prev(req,route)
      d <- curr(req,route)
    } yield (a,b,c,d)

    def ^[T](f: (A,B,C,D)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser5(this, n)
  }

  case class Parser5[A,B,C,D,E](prev: Parser[(A,B,C,D)], curr: Parser[E]) extends Parser[(A,B,C,D,E)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d) <- prev(req,route)
      e <- curr(req,route)
    } yield (a,b,c,d,e)

    def ^[T](f: (A,B,C,D,E)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser6(this, n)
  }

  case class Parser6[A,B,C,D,E,F](prev: Parser[(A,B,C,D,E)], curr: Parser[F]) extends Parser[(A,B,C,D,E,F)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d,e) <- prev(req,route)
      f <- curr(req,route)
    } yield (a,b,c,d,e,f)

    def ^[T](f: (A,B,C,D,E,F)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser7(this, n)
  }

  case class Parser7[A,B,C,D,E,F,G](prev: Parser[(A,B,C,D,E,F)], curr: Parser[G]) extends Parser[(A,B,C,D,E,F,G)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d,e,f) <- prev(req,route)
      g <- curr(req,route)
    } yield (a,b,c,d,e,f,g)

    def ^[T](f: (A,B,C,D,E,F,G)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser8(this, n)
  }

  case class Parser8[A,B,C,D,E,F,G,H](prev: Parser[(A,B,C,D,E,F,G)], curr: Parser[H]) extends Parser[(A,B,C,D,E,F,G,H)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d,e,f,g) <- prev(req,route)
      h <- curr(req,route)
    } yield (a,b,c,d,e,f,g,h)

    def ^[T](f: (A,B,C,D,E,F,G,H)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser9(this, n)
  }

  case class Parser9[A,B,C,D,E,F,G,H,I](prev: Parser[(A,B,C,D,E,F,G,H)], curr: Parser[I]) extends Parser[(A,B,C,D,E,F,G,H,I)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d,e,f,g,h) <- prev(req,route)
      i <- curr(req,route)
    } yield (a,b,c,d,e,f,g,h,i)

    def ^[T](f: (A,B,C,D,E,F,G,H,I)=>T) = Parser{ this(_,_).map(f.tupled) }

    def &[N](n: Parser[N]) = Parser10(this, n)
  }

  case class Parser10[A,B,C,D,E,F,G,H,I,J](prev: Parser[(A,B,C,D,E,F,G,H,I)], curr: Parser[J]) extends Parser[(A,B,C,D,E,F,G,H,I,J)] {

    def apply(req: Request, route: Route) = for {
      (a,b,c,d,e,f,g,h,i) <- prev(req,route)
      j <- curr(req,route)
    } yield (a,b,c,d,e,f,g,h,i,j)
    
    def ^[T](f: (A,B,C,D,E,F,G,H,I,J)=>T) = Parser{ this(_,_).map(f.tupled) }
  }

}


trait Parsers {

  /**
   * Parses named values from a Request's Query String.
   */
  def query[A: ParameterDecoder](name: String) = Parser[A]{ (req, route) =>
    req.uri.query.get(name).flatMap(_.headOption).map(implicitly[ParameterDecoder[A]]).getOrElse(Failure(new Exception("damn")))
  }

  /**
   * Parses named path segment from a Request's URI
   */
  def path[A: ParameterDecoder](name: String) = Parser[A]{ (req, route) =>
    import Segment._
    val i = route.path.indexWhere{
        case Named(n) if n == name => true
        case WildCard if "*" == name => true
        case _ => false
    }
    req.uri.path.segments.lift(i).map(implicitly[ParameterDecoder[A]]).getOrElse(Failure(new Exception("shit")))
  }

  /**
   * Parses the content of a Request
   */
  def content[A: ContentDecoder]: Parser[A] = Parser{ (req, route) =>
    implicitly[ContentDecoder[A]].apply(req.content)
  }

  implicit def ParserToParser1[A](p: Parser[A]): Parser.Parser1[A] = Parser.Parser1(p)
}

object Parsers extends Parsers