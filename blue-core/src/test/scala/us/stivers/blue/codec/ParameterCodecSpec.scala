package us.stivers.blue.codec

import scalax.util.{Try,Success,Failure}
import org.specs2._
import org.scalacheck._
import specification.gen._


class ParameterCodecSpec extends Specification with ScalaCheck { def is =

  "Boolean" ^ 
    "encode" ! encode[Boolean]  ^ 
    "decode" ! decode[Boolean]  ^ 
    end ^
  "Double" ^ 
    "encode" ! encode[Double]   ^ 
    "decode" ! decode[Double]   ^ 
    end ^
  "Float" ^ 
    "encode" ! encode[Float]    ^ 
    "decode" ! decode[Float]    ^ 
    end ^
  "Int" ^ 
    "encode" ! encode[Int]      ^ 
    "decode" ! decode[Int]      ^ 
    end ^
  "Long" ^ 
    "encode" ! encode[Long]     ^ 
    "decode" ! decode[Long]     ^ 
    end ^
  "String" ^ 
    "encode" ! encode[String]   ^ 
    "decode" ! decode[String]   ^ 
    end

  def encode[A : ParameterCodec : Arbitrary] = check{ (a: A) =>
    implicitly[ParameterCodec[A]].encode(a) must beLike {
      case Success(output) => ok
    }
  }

  def decode[A : ParameterCodec : Arbitrary] = check{ (a: A) =>
    implicitly[ParameterCodec[A]].encode(a) must beLike {
      case Success(input) =>
        implicitly[ParameterCodec[A]].decode(input) must beLike {
          case Success(output) => ok
        }
    }
  }

}