import sbt._
import Keys._

object Build extends Build with Common {

  // Project
  lazy val blue = Project("blue", file(".")) aggregate (
    core, 
    view, mustache,
    json, xml,
    finagle
  )

  // Core
  lazy val core = Project("core", file("blue-core")) settings (common:_*)
  
  // Views
  lazy val view = Project("view", file("blue-view")) settings (common:_*) dependsOn (core)
  lazy val mustache = Project("mustache", file("blue-mustache")) settings (common:_*) dependsOn (core, view)
  
  // Codecs
  lazy val json = Project("json", file("blue-json")) settings (common:_*) dependsOn (core)
  lazy val xml = Project("xml", file("blue-xml")) settings (common:_*) dependsOn (core)

  // Integrations
  lazy val finagle = Project("finagle", file("blue-finagle")) settings (common:_*) dependsOn (core)

  // Examples
  lazy val exampleSimple  = Project("ex-rails", file("blue-examples/rails")) settings (common:_*) dependsOn (core, json, xml, finagle)
  lazy val exampleRails   = Project("ex-rest",  file("blue-examples/rest")) settings (common:_*) dependsOn (core, json, xml, mustache, finagle)
}