package us.stivers.blue.codec

import scala.annotation.{implicitNotFound}
import scalax.util.{Try,Success,Failure}

@implicitNotFound("Cannot find ParameterEncoder for ${A}")
trait ParameterEncoder[A] extends Converter[A,String]

@implicitNotFound("Cannot find ParameterDecoder for ${A}")
trait ParameterDecoder[A] extends Converter[String,A]

@implicitNotFound("Cannot find ParameterCodec for ${A}")
trait ParameterCodec[A] extends Codec[A,String]

object ParameterEncoder {

  def apply[A](f: A => String) = new ParameterEncoder[A] {
    def apply(a: A): Try[String] = Try(f(a))
  }

  implicit val BooleanParameterEncoder    = ParameterEncoder[Boolean] { _.toString }
  implicit val DoubleParameterEncoder     = ParameterEncoder[Double] { _.toString }
  implicit val FloatParameterEncoder      = ParameterEncoder[Float] { _.toString }
  implicit val IntParameterEncoder        = ParameterEncoder[Int] { _.toString }
  implicit val LongParameterEncoder       = ParameterEncoder[Long] { _.toString }
  implicit val StringParameterEncoder     = ParameterEncoder[String] { a => a }
}

object ParameterDecoder {

  def apply[A](f: String => A) = new ParameterDecoder[A] {
    def apply(s: String): Try[A] = Try(f(s))
  }

  implicit val BooleanParameterDecoder    = ParameterDecoder[Boolean] { s => s.toBoolean }
  implicit val DoubleParameterDecoder     = ParameterDecoder[Double] { s => s.toDouble }
  implicit val FloatParameterDecoder      = ParameterDecoder[Float] { s => s.toFloat }
  implicit val IntParameterDecoder        = ParameterDecoder[Int] { s => s.toInt }
  implicit val LongParameterDecoder       = ParameterDecoder[Long] { s => s.toLong }
  implicit val StringParameterDecoder     = ParameterDecoder[String] { s => s }

}

object ParameterCodec {

  def apply[A](implicit encoder: ParameterEncoder[A], decoder: ParameterDecoder[A]) = new ParameterCodec[A] {
    val encode = encoder
    val decode = decoder
  }

  import ParameterEncoder._
  import ParameterDecoder._

  implicit val BooleanParameterCodec  = ParameterCodec[Boolean]
  implicit val DoubleParameterCodec   = ParameterCodec[Double]
  implicit val FloatParameterCodec    = ParameterCodec[Float]
  implicit val IntParameterCodec      = ParameterCodec[Int]
  implicit val LongParameterCodec     = ParameterCodec[Long]
  implicit val StringParameterCodec   = ParameterCodec[String]

}