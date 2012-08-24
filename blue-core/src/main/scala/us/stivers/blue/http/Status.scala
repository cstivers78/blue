package us.stivers.blue.http

sealed class Status(val code: Int, val message: String) {
  override lazy val toString = code.toString + " " + message
  override def equals(that: Any) = that match {
    case x: Status => x.code == code
    case _ => false
  }
  override def hashCode = code.hashCode
}

object Status extends Statuses {
  def apply(code: Int, message: String = ""): Status = code match {
    case Continue.code                      => Continue
    case SwitchingProtocols.code            => SwitchingProtocols
    case Ok.code                            => Ok
    case Created.code                       => Created
    case Accepted.code                      => Accepted
    case NonAuthoritativeInformation.code   => NonAuthoritativeInformation
    case NoContent.code                     => NoContent
    case ResetContent.code                  => ResetContent
    case PartialContent.code                => PartialContent
    case MultipleChoices.code               => MultipleChoices
    case MovedPermanently.code              => MovedPermanently
    case Found.code                         => Found
    case SeeOther.code                      => SeeOther
    case NotModified.code                   => NotModified
    case UseProxy.code                      => UseProxy
    case TemporaryRedirect.code             => TemporaryRedirect
    case BadRequest.code                    => BadRequest
    case Unauthorized.code                  => Unauthorized
    case PaymentRequired.code               => PaymentRequired
    case Forbidden.code                     => Forbidden
    case NotFound.code                      => NotFound
    case MethodNotAllowed.code              => MethodNotAllowed
    case NotAcceptable.code                 => NotAcceptable
    case ProxyAuthenticationRequired.code   => ProxyAuthenticationRequired
    case RequestTimeout.code                => RequestTimeout
    case Conflict.code                      => Conflict
    case Gone.code                          => Gone
    case LengthRequired.code                => LengthRequired
    case PreconditionFailed.code            => PreconditionFailed
    case RequestEntityTooLarge.code         => RequestEntityTooLarge
    case RequestUriTooLong.code             => RequestUriTooLong
    case UnsupportedMediaType.code          => UnsupportedMediaType
    case RequestedRangeNotSatisfiable.code  => RequestedRangeNotSatisfiable
    case ExpectationFailed.code             => ExpectationFailed
    case InternalServerError.code           => InternalServerError
    case NotImplemented.code                => NotImplemented
    case BadGateway.code                    => BadGateway
    case ServiceUnavailable.code            => ServiceUnavailable
    case GatewayTimeout.code                => GatewayTimeout
    case HttpVersionNotSupported.code       => HttpVersionNotSupported
    case UserAccessDenied.code              => UserAccessDenied
    case _                                  => new Status(code,message.toUpperCase)
  }
}

trait Statuses {
  val Continue                      = new Status(100,"CONTINUE")
  val SwitchingProtocols            = new Status(101,"SWITCHING PROTOCOLS")
  val Ok                            = new Status(200,"OK")
  val Created                       = new Status(201,"CREATED")
  val Accepted                      = new Status(202,"ACCEPTED")
  val NonAuthoritativeInformation   = new Status(203,"NON-AUTHORITATIVE INFORMATION")
  val NoContent                     = new Status(204,"NO CONTENT")
  val ResetContent                  = new Status(205,"RESET CONTENT")
  val PartialContent                = new Status(206,"PARTIAL CONTENT")
  val MultipleChoices               = new Status(300,"MULTIPLE CHOICES")
  val MovedPermanently              = new Status(301,"MOVED PERMANENTLY")
  val Found                         = new Status(302,"FOUND")
  val SeeOther                      = new Status(303,"SEE OTHER")
  val NotModified                   = new Status(304,"NOT MODIFIED")
  val UseProxy                      = new Status(305,"USE PROXY")
  val TemporaryRedirect             = new Status(307,"TEMPORARY REDIRECT ")
  val BadRequest                    = new Status(400,"BAD REQUEST")
  val Unauthorized                  = new Status(401,"UNAUTHORIZED")
  val PaymentRequired               = new Status(402,"PAYMENT REQUIRED")
  val Forbidden                     = new Status(403,"FORBIDDEN")
  val NotFound                      = new Status(404,"NOT FOUND")
  val MethodNotAllowed              = new Status(405,"METHOD NOT ALLOWED")
  val NotAcceptable                 = new Status(406,"NOT ACCEPTABLE")
  val ProxyAuthenticationRequired   = new Status(407,"PROXY AUTHENTICATION REQUIRED")
  val RequestTimeout                = new Status(408,"REQUEST TIMEOUT")
  val Conflict                      = new Status(409,"CONFLICT")
  val Gone                          = new Status(410,"GONE")
  val LengthRequired                = new Status(411,"LENGTH REQUIRED")
  val PreconditionFailed            = new Status(412,"PRECONDITION FAILED")
  val RequestEntityTooLarge         = new Status(413,"REQUEST ENTITY TOO LARGE")
  val RequestUriTooLong             = new Status(414,"REQUEST-URI TOO LONG")
  val UnsupportedMediaType          = new Status(415,"UNSUPPORTED MEDIA TYPE")
  val RequestedRangeNotSatisfiable  = new Status(416,"REQUESTED RANGE NOT SATISFIABLe")
  val ExpectationFailed             = new Status(417,"EXPECTATION FAILED")
  val InternalServerError           = new Status(500,"INTERNAL SERVER ERROR")
  val NotImplemented                = new Status(501,"NOT IMPLEMENTED")
  val BadGateway                    = new Status(502,"BAD GATEWAY")
  val ServiceUnavailable            = new Status(503,"SERVICE UNAVAILABLE")
  val GatewayTimeout                = new Status(504,"GATEWAY TIMEOUT")
  val HttpVersionNotSupported       = new Status(505,"HTTP VERSION NOT SUPPORTED")
  val UserAccessDenied              = new Status(530,"USER ACCESS DENIED")
}

