package us.stivers.blue.uri

/**
 * Represents a URI. 
 */
case class URI(
  scheme:             Option[String] = None, 
  schemeSpecificPart: Option[String] = None,
  userInfo:           Option[String] = None,
  host:               Option[String] = None, 
  port:               Option[Int] = None, 
  path:               Path = Path.empty, 
  query:              Query = Query.empty,
  fragment:           Option[String] = None
) {

  lazy val toJavaURI = URI.URIToJavaURI(this)

  lazy val toURL: java.net.URL = URI.URIToJavaURL(this)

  override lazy val toString = URI.URIToString(this)

  def resolve(uri: URI): URI = URI(this.toJavaURI.resolve(uri.toJavaURI))

  def resolve(path: String): URI = URI(this.toJavaURI.resolve(path))
}

object URI {
  
  /**
   * An Empty URI
   */
  lazy val empty: URI = new URI()
  
  /**
   * Constructs a URI from a String
   */
  def apply(uri: String): URI = JavaURIToURI(new java.net.URI(uri))
  
  /**
   * Constructs a URI from a java.net.URI
   */
  def apply(uri: java.net.URI): URI = JavaURIToURI(uri)

  /**
   * Constructs a URI from a java.net.URI
   */
  implicit def JavaURIToURI(uri: java.net.URI): URI = {
    URI (
      Option(uri.getScheme),
      Option(uri.getSchemeSpecificPart),
      Option(uri.getUserInfo),
      Option(uri.getHost),
      Option(uri.getPort),
      if ( uri.getPath == null ) Path.empty else Path(uri.getPath),
      if ( uri.getQuery == null ) Query.empty else Query(uri.getQuery),
      Option(uri.getFragment)
    )
  }

  /**
   * Constructs a java.net.URI from a URI
   */
  implicit def URIToJavaURI(uri: URI): java.net.URI = uri.schemeSpecificPart match {
    case Some(ssp)  => new java.net.URI(uri.scheme.orNull,ssp,uri.fragment.orNull)
    case None       => new java.net.URI(uri.scheme.orNull,uri.userInfo.orNull,uri.host.orNull,uri.port.getOrElse(-1),uri.path.toString,uri.query.toString,uri.fragment.orNull)
  }

  /**
   * Constructs a java.net.URL from a URI
   */
  implicit def URIToJavaURL(uri: URI): java.net.URL = uri.toJavaURI.toURL

  /**
   * Constructs a string from a URI
   */
  implicit def URIToString(uri: URI): String = uri.toJavaURI.toString

}