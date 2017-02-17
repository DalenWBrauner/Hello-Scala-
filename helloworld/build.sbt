name := """HelloWorld"""
organization := "Github.DalenWBrauner"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
libraryDependencies += guice

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "Github.DalenWBrauner.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "Github.DalenWBrauner.binders._"
