name := "scala-md-tag"

version := "0.1"

scalaVersion := "2.12.6"

val compilerOptions = Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xfuture",
  "-Ywarn-unused-import",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val defaultSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.6",
  organization := "pl.muninn",
  scalacOptions := compilerOptions
)
val scalaTestVersion = "3.0.5"

val tests = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

lazy val root =
  (project in file("."))
    .settings(defaultSettings)
    .settings(
      name := "scala-md-tag",
      libraryDependencies ++= tests,
      sbtVersion := "1.2.3"
    )