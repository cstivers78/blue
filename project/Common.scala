import sbt._
import Keys._
import scala.collection.mutable.{ListBuffer}

trait Common {

  lazy val common = settings

  private[this] var settings = new ListBuffer[Setting[_]]

  settings ++= Project.defaultSettings

  settings += (organization := "us.stivers.blue")

  settings += (version := "0.11.1-SNAPSHOT")

  // Scala Compiler Options
  settings += (scalacOptions ++= Seq("-deprecation","-unchecked"))

  // disable publishing the main API jar
  settings += (publishArtifact in (Compile, packageDoc) := false)

  // disable publishing the main sources jar
  settings += (publishArtifact in (Compile, packageSrc) := false)

  // Runtime Dependencies
  settings += (libraryDependencies ++= Seq(
    "org.scalaz" %% "scalaz-core" % "6.0.4",
    "com.twitter" %% "util-core" % "1.12.13"    // Config
  ))

  // Test Dependencies
  settings += (libraryDependencies ++= Seq(
    "org.specs2" %% "specs2" % "1.12" % "test",
    "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"
  ))

  // Remote Resolvers
  settings += (resolvers ++= Seq(
    "scala-tools.org" at "http://scala-tools.org/repo-releases",
    "repo.codahale.com" at "http://repo.codahale.com",
    "maven.twitter.com" at "http://maven.twttr.com/",
    "repo.typesafe.com" at "http://repo.typesafe.com/typesafe/releases"
  ))

}