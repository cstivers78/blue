package us.stivers.blue.json

import scalax.util.{Try}
import us.stivers.blue.codec.{ContentCodec,ContentEncoder,ContentDecoder}
import us.stivers.blue.http.{Content}

/**
 * Encodes A into JSON Content.
 */
class JsonContentEncoder[A](json: Json) extends ContentEncoder[A] {
  def apply(a: A): Try[Content] = Try(Content("application/json",json.generate[A](a)))
}

/**
 * Decodes A from JSON Content.
 */
class JsonContentDecoder[A: Manifest](json: Json) extends ContentDecoder[A] {
  def apply(c: Content): Try[A] = Try(json.parse[A](c.buffer.array)(implicitly[Manifest[A]]))
}

/**
 * Encode A into JSON Content. Decodes A from JSON Content
 */
class JsonContentCodec[A: Manifest](json: Json) extends ContentCodec[A] {
  val encode = new JsonContentEncoder[A](json)
  val decode = new JsonContentDecoder[A](json)
}

object JsonContentCodec {

  import com.fasterxml.jackson.databind.Module

  def apply[A: Manifest] = new JsonContentCodec[A](Json(Seq.empty))
  def apply[A: Manifest](json: Json) = new JsonContentCodec[A](json)
  def apply[A: Manifest](modules: Module*) = new JsonContentCodec[A](Json(modules))
}