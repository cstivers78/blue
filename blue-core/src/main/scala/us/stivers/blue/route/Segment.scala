package us.stivers.blue.route

import us.stivers.blue.uri.{Path}

/**
 * Represents a path segment for routing purposes.
 */
trait Segment

object Segment {

  /**
   * A literal value route segment
   */
  case class Value(value: String) extends Segment {
    override lazy val toString = value
  }

  /**
   * A named variable route segment
   */
  case class Named(name: String) extends Segment {
    override lazy val toString = "{"+name+"}"
  }

  /**
   * A wildcard route segment
   */
  case object WildCard extends Segment {
    override lazy val toString = "*"
  }

  private lazy val NamedPattern = """^\{([^:\}]+)\}$""".r
  
  /**
   * Create a list of segments from a path string
   */
  def parse(path: String): List[Segment] = parse(Path(path))
  
  /**
   * Create a list of segments from a path
   */
  def parse(path: Path): List[Segment] = {
    path.segments.map {
      case "*" => WildCard
      case NamedPattern(name) => Named(name)
      case value => Value(value)
    }
  }
}
