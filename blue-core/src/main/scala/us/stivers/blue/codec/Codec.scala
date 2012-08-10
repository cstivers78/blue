package us.stivers.blue.codec

import scala.annotation.{implicitNotFound}
import scalax.util.{Try,Success,Failure}

/**
 * Converts A to B.
 */
@implicitNotFound("Cannot find Converter for ${A} -> ${B} ")
trait Converter[A,B] extends (A=>Try[B])

/**
 * Encodes A into B. Decodes A from B.
 */
@implicitNotFound("Cannot find Codec for ${A} <-> ${B} ")
trait Codec[A,B] {
  val encode: Converter[A,B]
  val decode: Converter[B,A]
}

/**
 * An error occurred during conversion.
 */
case class ConversionException(message: String) extends Exception(message)