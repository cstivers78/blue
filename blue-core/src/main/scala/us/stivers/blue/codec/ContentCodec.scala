package us.stivers.blue.codec

import scala.annotation.{implicitNotFound}
import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Content}

/**
 * Encodes A into Content.
 */
@implicitNotFound("Cannot find ContentEncoder for ${A}")
trait ContentEncoder[A] extends Converter[A,Content]

/**
 * Decodes A from Content.
 */
@implicitNotFound("Cannot find ContentDecoder for ${A}")
trait ContentDecoder[A] extends Converter[Content,A]

/**
 * Encodes A into Content, decodes A from Content.
 */
@implicitNotFound("Cannot find ContentCodec for ${A}")
trait ContentCodec[A] extends Codec[A,Content]

/**
 * Built-in ContentEncoders
 */
object ContentEncoder {

  def apply[A](f: A => Content) = new ContentEncoder[A] {
    def apply(a: A): Try[Content] = Try(f(a))
  }

  implicit val NodeSeqContentEncoder = ContentEncoder[scala.xml.NodeSeq] { a => 
    Content("text/xml", a.toString) 
  }
}

/**
 * Built-in ContentDecoders
 */
object ContentDecoder {

  def apply[A](f: Content => A) = new ContentDecoder[A] {
    def apply(c: Content): Try[A] = Try(f(c))
  }

  implicit val NodeSeqContentDecoder = ContentDecoder[scala.xml.NodeSeq] { c => 
    scala.xml.XML.loadString(new String(c.buffer.array, scala.io.Codec.UTF8)) 
  }
}

/**
 * Built-in ContentCodecs
 */
object ContentCodec {

  def apply[A](implicit encoder: ContentEncoder[A], decoder: ContentDecoder[A]) = new ContentCodec[A] {
    val encode = encoder
    val decode = decoder
  }

  import ContentEncoder._
  import ContentDecoder._

  implicit val NodeSeqContentCodec = ContentCodec[scala.xml.NodeSeq]
}