name := "presentation"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.8",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.8" % Test,
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11" % Test,
  "com.lightbend.akka" %% "akka-stream-alpakka-file" % "0.15",
  "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
)

libraryDependencies += "org.apache.commons" % "commons-text" % "1.1"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "0.16"