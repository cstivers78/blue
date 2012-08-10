package us.stivers.blue.uri

import java.io.{File}

/**
 * Resolves a URI using the given filename/filepath.
 * If the URI cannot be resolved, then None is returned. Otherwise, a Some containing the URI will be returned.
 */
trait Resolver extends (String=>Option[URI])

/**
 * A collection of standard Resolvers
 */
object Resolver {

  /**
   * Create a Resolver that will resolve URIs to resources using the given ClassLoader.
   */
  def resource(base: URI, classLoader: ClassLoader): Resolver = new Resolver {
    def apply(path: String): Option[URI] = {
      val uri = base.resolve(path)
      val url = classLoader.getResource(uri.toString)
      Option(url).map(_.toURI).map(URI(_))
    }
  }

  /**
   * Create a Resolver that will resolve URIs to resources using the current thread's 
   * ClassLoader starting from base.
   */
  def resource(base: URI): Resolver = resource(base, Thread.currentThread.getContextClassLoader)

  /**
   * Create a Resolver that will resolve URIs to resources using the current thread's 
   * ClassLoader starting from base.
   */
  def resource(base: String): Resolver = resource(URI(base))

  /**
   * Create a Resolver that will resolve URIs starting from the root of the resources path.
   */
  def resource(): Resolver = resource(URI("."))

  /**
   * Create a Resolver that will resolve URIs to files in the filesystem
   */
  def file(base: File) = new Resolver {
    def apply(path: String): Option[URI] = {
      Option(new File(base,path)).filter(f => f.exists && f.isFile && f.canRead).map(_.toURI).map(URI(_))
    }
  }

  /**
   * Create a Resolver that will resolve URIs to files in the filesystem
   * starting from base.
   */
  def file(base: String): Resolver = file(new File(base))

  /**
   * Create a Resolver that will use multiple Resolvers to resolve a URI. 
   * This is usefule if you have multiple locations or sources in which a file may exist, such 
   * as a resource or file.
   */
  def fallback(resolvers: Resolver*): Resolver = new Resolver {
    def apply(path: String): Option[URI] = {
      resolvers.iterator.map(_.apply(path)).find(_.isDefined).getOrElse(None)
    }
  }

}