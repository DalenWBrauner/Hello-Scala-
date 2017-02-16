import ScalateKeys._
import twirl.sbt.TwirlPlugin._

val ScalatraVersion = "2.2.2"

Twirl.settings

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016",
  "ch.qos.logback" % "logback-classic" % "1.0.1")

//seq(Twirl.settings: _*)
Seq(webSettings :_*)
