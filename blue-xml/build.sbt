name := "blue-xml"

// Runtime Dependencies
libraryDependencies ++= Seq(
  "xstream" % "xstream" % "1.2.2",      // XML
  "joda-time" % "joda-time" % "2.1",    // Date-Time - was 1.6.2
  "org.joda" % "joda-convert" % "1.2"   // Conversions for Joda
)