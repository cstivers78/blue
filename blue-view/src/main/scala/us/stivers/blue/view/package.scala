package us.stivers.blue.view

import java.io.{File}
import us.stivers.blue.uri.{URI, Resolver}
import scalax.util.{Try}
import scalaz.{NonEmptyList}
import scalaz.Scalaz._

/**
 * Marks a class as a view
 */
trait View

/**
 * Compiles a view
 */
trait ViewCompiler[V<:View] extends ((Resolver, URI, Boolean) => Try[V])

/**
 * Resolves and loads a view from a path, then compiles it using the view-specific compiler.
 */
case class Views(resolver: Resolver) {

  def apply[V<:View : ViewCompiler](path: String): Try[V] = apply(path,false)

  def apply[V<:View : ViewCompiler](path: String, partial: Boolean): Try[V] = {
    for {
      uri <- Try { resolver(path) match {
        case Some(uri) => uri
        case None => throw ViewNotFoundException(path.toString)
      }}
      view <- implicitly[ViewCompiler[V]].apply(resolver,uri,partial)
    } yield {
      view
    }
  }
}

/**
 * View was not found
 */
case class ViewNotFoundException(path: String) extends Exception