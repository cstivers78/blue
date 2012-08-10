package us.stivers.blue.codec

import org.specs2.mutable._
import scalax.util.{Try,Success,Failure}
import us.stivers.blue.http.{Content}

class ContentCodecSpec extends Specification {

  "Built-in ContentCodec[scala.xml.NodeSeq]" should {

    import scala.xml.NodeSeq

    "encode '<a></a>'" in {

      val nodeSeq = <a></a>
      val codec = implicitly[ContentCodec[NodeSeq]]
      val result = codec.encode(nodeSeq)

      result must beLike {
        case Success(content) =>
          content.buffer.capacity must be equalTo(7)
      }
    }
    
    "decode '<a></a>'" in {

      val content = Content("text/xml","<a></a>")
      val codec = implicitly[ContentCodec[NodeSeq]]
      val result = codec.decode(content)

      result must beLike {
        case Success(nodeSeq) =>
          nodeSeq.size must be equalTo(1)
          nodeSeq.toString must be equalTo("<a></a>")
      }
    }

  }

  "Custom ContentCodec[String]" should {

    implicit val StringContentCodec = ContentCodec[String](
      ContentEncoder[String]( s => Content("text/plain",s) ),
      ContentDecoder[String]( c => new String(c.buffer.array, scala.io.Codec.UTF8) )
    )
    
    "encode 'Hello World!'" in {

      val str = "Hello World!"
      val codec = implicitly[ContentCodec[String]]
      val result = codec.encode(str)

      result must beLike {
        case Success(content) =>
          content.buffer.capacity must be equalTo(12)
      }
    }
    
    "decode 'Hello World!'" in {

      val content = Content("text/plain","Hello World!")
      val codec = implicitly[ContentCodec[String]]
      val result = codec.decode(content)

      result must beLike {
        case Success(str) =>
          str.size must be equalTo(12)
          str must be equalTo("Hello World!")
      }
    }

  }

}