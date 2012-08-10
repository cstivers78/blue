package us.stivers.blue.http

import scala.collection.immutable.{List,Map,Set}
import us.stivers.blue.uri.{URI}

case class Request(
  method:   Method = Method.Get,
  uri:      URI = URI.empty,
  version:  Version = Version.Http11,
  headers:  Map[Header,List[String]] = Map.empty,
  cookies:  Set[Cookie] = Set.empty,
  content:  Content = Content.empty
)