package us.stivers.blue.http

sealed class Method(val name: String) {
  override lazy val toString = name
  override def equals(that: Any) = that match {
    case x: Method => x.name == name
    case _ => false
  }
  override def hashCode = name.hashCode
}

object Method extends Methods {
  def apply(name: String): Method = name.toUpperCase match {
    case Connect.name => Connect
    case Delete.name  => Delete
    case Get.name     => Get
    case Head.name    => Head
    case Options.name => Options
    case Patch.name   => Patch
    case Post.name    => Post
    case Put.name     => Put
    case Trace.name   => Trace
    case name         => new Method(name)
  }
}

trait Methods {
  val Connect   = new Method("CONNECT")
  val Delete    = new Method("DELETE")
  val Get       = new Method("GET")
  val Head      = new Method("HEAD")
  val Options   = new Method("OPTIONS")
  val Patch     = new Method("PATCH")
  val Post      = new Method("POST")
  val Put       = new Method("PUT")
  val Trace     = new Method("TRACE")
  val Any       = new Method("*")
}
