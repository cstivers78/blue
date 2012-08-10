package us.stivers.blue.uri

/**
 * Represents a filepath's components of dirname, basename and extname.
 */
case class Path(dirname: String, basename: String, extname: Option[String]) {
  
  lazy val segments = toString.stripPrefix("/").stripSuffix("/").split("[/]").filter(!_.isEmpty).toList

  def parent: Path = Path(dirname)

  override val toString = Path.PathToString(this)
}


object Path {
 
  /**
   * An Empty Path
   */
  val empty = Path("","",None)

  /**
   * Construct a new Path using a path spec
   */
  def apply(path: String): Path = {
    val segments    = path.stripPrefix("/").stripSuffix("/").split("[/]").filter(!_.isEmpty)
    val filename    = segments.lastOption.getOrElse("")
    val filepath    = segments.dropRight(1).mkString("/")
    val dirname     = path.headOption.filter(_=='/').map(_ + filepath).getOrElse(filepath)
    val dot         = filename.lastIndexOf('.')
    if ( dot > 0 && dot < filename.length-1 ) {
      val (basename,extname) = filename.splitAt(dot)
      Path(dirname,basename,Some(extname.stripPrefix(".")))
    }
    else {
      Path(dirname,filename,None)
    }
  }

  /**
   * Constructs a path spec from a Path
   */
  implicit def PathToString(path: Path): String = {
    "%s/%s".format(path.dirname,path.extname.map(path.basename+"."+_).getOrElse(path.basename))
  }

}