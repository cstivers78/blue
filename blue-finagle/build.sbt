name := "blue-finagle"

// Finagle Dependencies
libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-core" % "1.11.0",
  "com.twitter" %% "finagle-http" % "1.11.0",
  "com.twitter" %% "finagle-stream" % "1.11.0",
  "com.twitter" %% "util-core" % "1.12.13",
  "com.twitter" %% "util-eval" % "1.12.13"
)