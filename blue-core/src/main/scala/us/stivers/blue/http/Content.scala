package us.stivers.blue.http

import java.nio.{ByteBuffer}

case class Content(val contentType: Option[String], val buffer: ByteBuffer)

object Content {
  
  lazy val empty = Content(None,ByteBuffer.allocate(0))

  def apply(contentType: String, buffer: ByteBuffer): Content = Content(Some(contentType),buffer)
  def apply(contentType: String, bytes: Array[Byte]): Content = Content(contentType,ByteBuffer.wrap(bytes))
  def apply(contentType: String, string: String): Content = Content(contentType,string.getBytes(scala.io.Codec.UTF8))
}