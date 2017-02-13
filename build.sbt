import ScalateKeys._

val ScalatraVersion = "2.2.2"

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
  "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
  "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,compile",
  "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016")

Seq(webSettings :_*)