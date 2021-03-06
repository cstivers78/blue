package us.stivers.blue.http

sealed class Header(val name: String) {
  override lazy val toString = name
  override def equals(that: Any) = that match {
    case x: Header => x.name == name
    case _ => false
  }
  override def hashCode = name.hashCode
}

object Header extends Headers {
  def apply(name: String): Header = name.toLowerCase match {
    case AccessControlAllowOrigin.name      => AccessControlAllowOrigin
    case AccessControlAllowMethods.name     => AccessControlAllowMethods 
    case AccessControlAllowHeaders.name     => AccessControlAllowHeaders 
    case AccessControlMaxAge.name           => AccessControlMaxAge 
    case AccessControlRequestHeaders.name   => AccessControlRequestHeaders 
    case AccessControlRequestMethod.name    => AccessControlRequestMethod 
    case Accept.name                        => Accept 
    case AcceptCharset.name                 => AcceptCharset 
    case AcceptEncoding.name                => AcceptEncoding 
    case AcceptLanguage.name                => AcceptLanguage 
    case AcceptRanges.name                  => AcceptRanges 
    case Age.name                           => Age 
    case Allow.name                         => Allow 
    case Authorization.name                 => Authorization 
    case CacheControl.name                  => CacheControl 
    case Connection.name                    => Connection 
    case ContentEncoding.name               => ContentEncoding 
    case ContentLanguage.name               => ContentLanguage 
    case ContentLength.name                 => ContentLength 
    case ContentLocation.name               => ContentLocation 
    case ContentMD5.name                    => ContentMD5 
    case ContentRange.name                  => ContentRange 
    case ContentType.name                   => ContentType 
    case Cookie.name                        => Cookie 
    case SetCookie.name                     => SetCookie 
    case Date.name                          => Date 
    case ETag.name                          => ETag 
    case Expect.name                        => Expect 
    case Expires.name                       => Expires 
    case From.name                          => From 
    case Host.name                          => Host 
    case IfMatch.name                       => IfMatch 
    case IfModifiedSince.name               => IfModifiedSince 
    case IfNoneMatch.name                   => IfNoneMatch 
    case IfRange.name                       => IfRange 
    case IfUnmodifiedSince.name             => IfUnmodifiedSince 
    case IfModified.name                    => IfModified 
    case LastModified.name                  => LastModified 
    case Location.name                      => Location 
    case Origin.name                        => Origin 
    case Pragma.name                        => Pragma 
    case ProxyAuthenticate.name             => ProxyAuthenticate 
    case Range.name                         => Range 
    case Referer.name                       => Referer 
    case RetryAfter.name                    => RetryAfter 
    case SecWebSocketKey1.name              => SecWebSocketKey1 
    case SecWebSocketKey2.name              => SecWebSocketKey2 
    case SecWebSocketLocation.name          => SecWebSocketLocation 
    case SecWebSocketOrigin.name            => SecWebSocketOrigin 
    case Server.name                        => Server 
    case TE.name                            => TE 
    case Trailer.name                       => Trailer 
    case TransferEncoding.name              => TransferEncoding 
    case Upgrade.name                       => Upgrade 
    case UserAgent.name                     => UserAgent 
    case Vary.name                          => Vary 
    case Via.name                           => Via 
    case Warning.name                       => Warning 
    case WebSocketLocation.name             => WebSocketLocation 
    case WebSocketOrigin.name               => WebSocketOrigin 
    case WebSocketProtocol.name             => WebSocketProtocol 
    case WWWAuthenticate.name               => WWWAuthenticate 
    case name                               => new Header(name)
  }
}

trait Headers {
  val AccessControlAllowOrigin      = new Header("access-control-allow-origin")
  val AccessControlAllowMethods     = new Header("access-control-allow-methods")
  val AccessControlAllowHeaders     = new Header("access-control-allow-headers")
  val AccessControlMaxAge           = new Header("access-control-max-age")
  val AccessControlRequestHeaders   = new Header("access-control-request-headers")
  val AccessControlRequestMethod    = new Header("access-control-request-method")
  val Accept                        = new Header("accept")
  val AcceptCharset                 = new Header("accept-charset")
  val AcceptEncoding                = new Header("accept-encoding")
  val AcceptLanguage                = new Header("accept-language")
  val AcceptRanges                  = new Header("accept-ranges")
  val Age                           = new Header("age")
  val Allow                         = new Header("allow")
  val Authorization                 = new Header("authorization")
  val CacheControl                  = new Header("cache-control")
  val Connection                    = new Header("connection")
  val ContentEncoding               = new Header("content-encoding")
  val ContentLanguage               = new Header("content-language")
  val ContentLength                 = new Header("content-length")
  val ContentLocation               = new Header("content-location")
  val ContentMD5                    = new Header("content-md5")
  val ContentRange                  = new Header("content-range")
  val ContentType                   = new Header("content-type")
  val Cookie                        = new Header("cookie")
  val SetCookie                     = new Header("set-cookie")
  val Date                          = new Header("date")
  val ETag                          = new Header("etag")
  val Expect                        = new Header("expect")
  val Expires                       = new Header("expires")
  val From                          = new Header("from")
  val Host                          = new Header("host")
  val IfMatch                       = new Header("if-match")
  val IfModifiedSince               = new Header("if-modified-since")
  val IfNoneMatch                   = new Header("if-none-match")
  val IfRange                       = new Header("if-range")
  val IfUnmodifiedSince             = new Header("if-unmodified-since")
  val IfModified                    = new Header("if-modified")
  val LastModified                  = new Header("last-modified")
  val Location                      = new Header("location")
  val Origin                        = new Header("origin")
  val Pragma                        = new Header("pragma")
  val ProxyAuthenticate             = new Header("proxy-authenticate")
  val Range                         = new Header("range")
  val Referer                       = new Header("referer")
  val RetryAfter                    = new Header("retry-after")
  val SecWebSocketKey1              = new Header("sec-websocket-key1")
  val SecWebSocketKey2              = new Header("sec-websocket-key2")
  val SecWebSocketLocation          = new Header("sec-websocket-location")
  val SecWebSocketOrigin            = new Header("sec-websocket-origin")
  val Server                        = new Header("server")
  val TE                            = new Header("te")
  val Trailer                       = new Header("trailer")
  val TransferEncoding              = new Header("transfer-encoding")
  val Upgrade                       = new Header("upgrade")
  val UserAgent                     = new Header("user-agent")
  val Vary                          = new Header("vary")
  val Via                           = new Header("via")
  val Warning                       = new Header("warning")
  val WebSocketLocation             = new Header("websocket-location")
  val WebSocketOrigin               = new Header("websocket-origin")
  val WebSocketProtocol             = new Header("websocket-protocol")
  val WWWAuthenticate               = new Header("www-authenticate")
}

