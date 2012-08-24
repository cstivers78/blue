package us.stivers.blue.http

sealed class Version(val value: String) {
  override lazy val toString = value
  override def equals(that: Any) = that match {
    case x: Version => x.value == value
    case _ => false
  }
  override def hashCode = value.hashCode
}

object Version extends Versions {
  def apply(value: String): Version = value.toUpperCase match {
    case Http10.value => Http10
    case Http11.value => Http11
    case value        => new Version(value){}
  }
}

trait Versions {
  val Http10 = new Version("HTTP/1.0")
  val Http11 = new Version("HTTP/1.1")
}
