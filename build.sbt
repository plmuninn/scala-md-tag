name := "scala-md-tag"

version := "0.2"

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
  version := "0.2",
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
    .settings(defaultSettings: _*)
    .settings(publishSettings: _*)
    .settings(
      name := "scala-md-tag",
      libraryDependencies ++= tests,
      sbtVersion := "1.2.3"
    )

val username = "OneWebPro"
val repo = "scala-md-tag"

lazy val publishSettings = Seq(
  homepage := Some(url(s"https://github.com/$username/$repo")),
  licenses += "MIT" -> url(s"https://github.com/$username/$repo/blob/master/LICENSE"),
  scmInfo := Some(ScmInfo(url(s"https://github.com/$username/$repo"), s"git@github.com:$username/$repo.git")),
  apiURL := Some(url(s"https://$username.github.io/$repo/latest/api/")),
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  developers := List(
    Developer(
      id = username,
      name = "Maciej Romanski",
      email = "maciej.loki.romanski@gmail.com",
      url = new URL(s"http://github.com/$username")
    )
  ),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging),
//   Following 2 lines need to get around https://github.com/sbt/sbt/issues/4275
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  updateOptions := updateOptions.value.withGigahorse(false)
)