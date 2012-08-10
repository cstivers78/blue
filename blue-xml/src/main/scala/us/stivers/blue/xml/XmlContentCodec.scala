package us.stivers.blue.xml

import com.thoughtworks.xstream.{XStream}
import com.thoughtworks.xstream.io.xml.{DomDriver}
import us.stivers.blue.codec.{ContentCodec,ContentEncoder,ContentDecoder}
import us.stivers.blue.http.{Content}
import scalax.util.{Try}

/**
 * Encodes A into JSON Content.
 */
class XmlContentEncoder[A: Manifest] extends ContentEncoder[A] {
  def apply(a: A): Try[Content] = Try {
    val xstream = new XStream(new DomDriver())
    val m = manifest[A]
    xstream.alias(m.erasure.getSimpleName.toLowerCase,m.erasure)
    Content("application/xml",xstream.toXML(a))
  }
}

/**
 * Decodes A from JSON Content.
 */
class XmlContentDecoder[A: Manifest] extends ContentDecoder[A] {
  def apply(c: Content): Try[A] = Try(throw new Exception("XmlContentDecoder not implemented."))
}

/**
 * Encode A into JSON Content. Decodes A from JSON Content
 */
class XmlContentCodec[A: Manifest] extends ContentCodec[A] {
  val encode = new XmlContentEncoder[A]
  val decode = new XmlContentDecoder[A]
}

object XmlContentCodec {
  def apply[A: Manifest] = new XmlContentCodec[A]
}