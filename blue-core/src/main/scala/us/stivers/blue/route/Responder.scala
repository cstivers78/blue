package us.stivers.blue.route

import scala.annotation.{implicitNotFound}
import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Request,Response,Status}

/**
 * Converts a value of type A into a `Reponse`. With Responders you can handle how 
 * any type returned from a handler function will be converted into a Response: 
 * any type, including functions. 
 *
 * Common uses of Responders are:
 *  - Convert the return value of the handler function.
 *  - Provide arguments to the handler function. 
 *
 * Responders provide the ability to isolate HTTP handling from business logic. Your handler functions
 * can simply specify the arguments it expects and it's output, and only focus on it's logic.
 * The Responders will be responsible for providing the arguments and converting the return value into
 * an HTTP Response.
 *
 * <p>&nbsp;</p>
 *
 * Let's assume you have a handler function defined as:
 * {{{
 *    def greet: String = "Hello World!"
 * }}}
 * 
 * In order for the function to work properly and send a `Response` back to the client, you will need 
 * a `Responder` to handle the return value.
 *
 * The following is a `Responder` for `String`. It takes the String value, and wraps it directly into a `Content` of a Response.
 * {{{
 *    implicit val StringResponder: Responder[String] = new Responder[String] {
 *      def apply(req: Request, route: Route, str: String) = Success(Response(content = Content("text/plain",str)))
 *    }
 * }}}
 *
 * <p>&nbsp;</p>
 *
 * Let's assume you want to provide `Locale` object to your handler function, to localize a date string. You can
 * define your function as one which accepts a `Locale`:
 *
 * {{{
 *    val today = (locale: Locale) => (new MessageFormat("{0,date}",locale)).format(new Date()).toString
 * }}}
 *
 * Then to make it function as properly as a handler function, you need to define a `Responder[Locale=>A]`.
 *
 * {{{
 *    implicit def RequestResponder[A: Responder]: Responder[Locale=>A] = new Responder[Locale=>A] {
 *      def apply(req: Request, route: Route, f: Locale=>A): Result[Response] = {
 *        val locale: Locale = ...
 *        implicitly[Responder[A]].apply(req,route,f(locale))
 *      }
 *    } 
 * }}}
 *
 * As you can see, it is quite easy to extend blue using `Responders` to handle the inputs and outputs of your functions. 
 * This has the added benefit of allowing you to simply worry about the inputs, outputs, and logic of the function, and 
 * isolate how you provide and process those inputs and outputs.
 *
 *
 */
// @implicitNotFound("Cannot find Responder for ${A}") 
trait Responder[A] extends ((Request,Route,A)=>Try[Response])

object Responder {

  import us.stivers.blue.codec.{ContentEncoder}
  import us.stivers.blue.http.{Content,Request,Response,Status}

  /**
   * Responder for any value that has a ContentEncoder defined for it.
   */
  implicit def ContentEncoderResponder[A : ContentEncoder]: Responder[A] = new Responder[A] {
    def apply(req: Request, route: Route, a: A): Try[Response] = implicitly[ContentEncoder[A]].apply(a) match {
      case Success(c) => Success(Response(content = c))
      case Failure(e) => Success(Response(status = Status.InternalServerError))
    }
  }

  /**
   * Responder for a function that accepts a Request parameter
   */
  implicit def RequestHandlerResponder[A: Responder]: Responder[Request=>A] = new Responder[Request=>A] {
    def apply(req: Request, route: Route, f: Request=>A): Try[Response] = implicitly[Responder[A]].apply(req,route,f(req))
  } 

  /**
   * Responder for Response objects
   */
  implicit val ResponseResponder: Responder[Response] = new Responder[Response] {
    def apply(req: Request, route: Route, res: Response): Try[Response] = Success(res)
  } 

  /**
   * Responder for Status Objects
   */
  implicit val StatusResponder: Responder[Status] = new Responder[Status] {
    def apply(req: Request, route: Route, status: Status): Try[Response] = Success(Response(status = status))
  } 

  /**
   * Responder for Content Objects
   */
  implicit val ContentResponder: Responder[Content] = new Responder[Content] {
    def apply(req: Request, route: Route, content: Content): Try[Response] = Success(Response(content = content))
  } 
  
  /**
   * Responder for Try
   */
  implicit def TryResponder[A: Responder]: Responder[Try[A]] = new Responder[Try[A]] {
    def apply(req: Request, route: Route, result: Try[A]): Try[Response] = for {
      out <- result
      res <- implicitly[Responder[A]].apply(req, route, out)
    } yield res
  }

}