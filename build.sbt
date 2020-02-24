name := "akka-project"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.3" % Test

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"