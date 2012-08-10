name := "blue-json"

// Dependencies
libraryDependencies ++= Seq(
  "com.codahale" %% "jerkson" % "0.6.0-SNAPSHOT",   // JSON
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.0.4-SNAPSHOT",   // JSON
  "joda-time" % "joda-time" % "2.1",                // Date-Time - was 1.6.2
  "org.joda" % "joda-convert" % "1.2"               // Conversions for Joda
)