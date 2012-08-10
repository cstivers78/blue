 package us.stivers.blue.mustache

import us.stivers.blue.uri.{URI, Resolver}
import us.stivers.blue.view.{ViewCompiler}

import com.github.mustachejava.{MustacheFactory}
import scala.io.{Source}
import scalax.util.{Try}

/**
 * A view compiler for mustache templates.
 */
case class MustacheCompiler() extends ViewCompiler[Mustache] {
  def apply(resolver: Resolver, uri: URI, partial: Boolean = false): Try[Mustache] = {
    for {
      factory <- Try(MustacheCompiler.mustacheFactory(resolver))
      template <- Try(factory.compile(uri))
    } yield {
      Mustache(uri,template)
    }
  }
}

object MustacheCompiler {

  import java.io.{Reader}
  import com.github.mustachejava.{Mustache,MustacheException,MustacheFactory,MustacheVisitor}
  import com.github.mustachejava.{DefaultMustacheFactory,DefaultMustacheVisitor}
  import com.twitter.mustache.{TwitterObjectHandler}


  class BlueObjectHandler extends TwitterObjectHandler {

    import com.github.mustachejava.{Iteration}
    import com.twitter.util.{Future}
    import java.io.{Writer}
    import java.lang.reflect.{Method, Field}
    import java.util.concurrent.{Callable}
    import scala.collection.JavaConversions.{mapAsJavaMap}
    import com.google.common.base.{Function=>GFunc}

    override def coerce(obj: Object) = {
      obj match {
        case x: Map[_,_] => mapAsJavaMap(x)
        case x: Option[_] => x.map(_.asInstanceOf[Object]).map(coerce).orNull
        case x: Function0[_] => coerce(x.apply().asInstanceOf[Object])
        case x: Try[_] => x.map(_.asInstanceOf[Object]).toOption.map(coerce).orNull 
        case x: Future[_] =>
          new Callable[Any]() {
            def call() = {
              coerce(x.get().asInstanceOf[Object])
            }
          }
        case _ => obj
      }
    }
  }

  trait URIMustachFactory extends MustacheFactory {
    def getReader(uri: URI): Reader = Source.fromURI(uri).bufferedReader
    def compile(uri: URI): Mustache = compile(getReader(uri),uri.toString)
  }

  def mustacheFactory(resolve: Resolver): URIMustachFactory = {
    
    new DefaultMustacheFactory with URIMustachFactory {

      setObjectHandler(new BlueObjectHandler)

      override def getReader(name: String): Reader = resolve(name).map(getReader) match {
        case Some(r)  => r
        case None     => throw new MustacheException("Template " + name + " not found. " + resolve(name))
      }

      override def compile(name: String): Mustache = compile(getReader(name),name)

      override def compile(reader: Reader, name: String):  Mustache = compile(reader, name, "{{", "}}")

      override def compile(reader: Reader, name: String, start: String, end: String): Mustache = super.compile(reader,name,start,end)
    }

  }

}