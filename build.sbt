import sbt.Keys.{libraryDependencies, _}
import sbt._

name := "presentation"

version := "0.1"

//scalaVersion := "2.11.8"
scalaVersion := "2.12.3"
val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-file" % "0.15",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.apache.commons" % "commons-text" % "1.1",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.scalactic" %% "scalactic" % "3.0.4",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test,
  "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "0.16"
)

lazy val manifestSettings = Seq(
  packageOptions in(Compile, packageBin) +=
    Package.ManifestAttributes(
      "git_last_commit" -> git.gitHeadCommit.value.toString,
      "git_last_message" -> git.gitHeadMessage.value.toString.replaceAll("\n", ""))
)

lazy val root = Project(id = "root", base = file(".")).settings(manifestSettings: _*)
