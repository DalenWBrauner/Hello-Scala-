logLevel := Level.Warn

resolvers += "spray repo" at "http://repo.spray.io"

addSbtPlugin("org.scalatra.scalate" % "sbt-scalate-precompiler" % "1.8.0.1")
addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.7.0")
addSbtPlugin("io.spray" % "sbt-twirl" % "0.7.0")
