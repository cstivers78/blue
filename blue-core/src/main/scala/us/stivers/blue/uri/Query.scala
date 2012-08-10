package us.stivers.blue.uri

import scala.collection.immutable.{Map,MapProxy}

/**
 * Represents the Query part of a URI.
 */
case class Query(parameters: Map[String,List[String]]) extends MapProxy[String,List[String]] {

  val self = parameters

  override lazy val toString = Query.QueryToString(this)
}

object Query {

  /**
   * An Empty Query
   */
  val empty = new Query(Map.empty)

  /**
   * Construct a new Query using a querystring
   */
  def apply(querystring: String): Query = {
    import java.net.URLDecoder.decode
    var parameters = Map.empty[String,List[String]]
    querystring.split('&').map(_.split("=",2)).filter(_.size >= 1).foreach{ pair => 
      val key = decode(pair(0),"UTF-8")
      val values = parameters.getOrElse(key,List.empty)
      if ( pair.length > 1 ) {
        val value = decode(pair(1),"UTF-8")
        parameters += key -> (values :+ value)
      } else {
        val value = "true"
        parameters += key -> (values :+ value)
      }
    }
    Query(parameters)
  }

  /**
   * Constructs a querystring from a Query
   */
  implicit def QueryToString(query: Query): String = {
    import java.net.URLEncoder.encode
    query.parameters.flatMap{ case (key,values) =>
      val encodedKey = encode(key,"UTF-8")
      values.map(encodedKey +"="+ encode(_,"UTF-8"))
    }.mkString("&")
  }
}
