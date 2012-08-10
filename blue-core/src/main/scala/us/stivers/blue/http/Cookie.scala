package us.stivers.blue.http

case class Cookie(
  name:     String,
  value:    String,
  version:  Int = 1,
  maxAge:   Option[Int] = None,
  path:     Option[String] = None,
  domain:   Option[String] = None,
  ports:    Set[Int] = Set.empty,
  comment:  Option[String] = None,
  secure:   Boolean = false,
  discard:  Boolean = false
)
