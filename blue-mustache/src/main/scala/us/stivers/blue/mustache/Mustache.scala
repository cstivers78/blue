package us.stivers.blue.mustache

import us.stivers.blue.http.{Content}
import us.stivers.blue.view.{View}
import us.stivers.blue.uri.{URI}

import com.github.mustachejava.{Mustache=>MustacheTemplate}
import java.io.{StringWriter}
import scala.collection.JavaConversions.{mapAsJavaMap}
import scalax.util.{Try}

case class Mustache(uri: URI, template: MustacheTemplate) extends View {

  def apply(): Try[Content] = apply(new Object)
  
  def apply[V<:AnyRef](values: V): Try[Content] = Try {
    val writer = new StringWriter
    val output = template.execute(writer,values).close()
    Content(contentType = "text/html", string = writer.toString)
  }
}


object Mustache {

  import us.stivers.blue.http.{Status,Request,Response}
  import us.stivers.blue.route.{Responder,Route}
  import us.stivers.blue.view.{ViewCompiler}

  implicit val MustacheViewCompiler: ViewCompiler[Mustache] = new MustacheCompiler()

  implicit def MustacheResponder(implicit responder: Responder[Content]): Responder[Mustache] = new Responder[Mustache] {
    def apply(req: Request, route: Route, mustache: Mustache): Try[Response] = {
      for {
        content <- mustache()
        response <- responder(req, route, content)
      } yield {
        response
      }
    }
  }
}