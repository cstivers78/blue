package us.stivers.blue.http

import scala.collection.immutable.{List,Map,Set}

case class Response(
  status:   Status = Status.Ok,
  version:  Version = Version.Http11,
  headers:  Map[Header,List[String]] = Map.empty,
  cookies:  Set[Cookie] = Set.empty,
  content:  Content = Content.empty
)
