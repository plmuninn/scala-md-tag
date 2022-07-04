import microsites._
import xerial.sbt.Sonatype._
import ReleaseTransformations._

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
  "-Xfatal-warnings"
)

val username = "plmuninn"
val repo = "scala-md-tag"

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / name := repo
ThisBuild / crossScalaVersions := Seq(scalaVersion.value, "2.12.16")
ThisBuild / organization := "pl.muninn"
ThisBuild / scalacOptions := compilerOptions

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

val scalaTestVersion = "3.2.12"

val tests = Seq(
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)

lazy val root =
  (project in file("."))
    .enablePlugins(MicrositesPlugin)
    .settings(publishSettings: _*)
    .settings(documentationSettings: _*)
    .settings(
      name := repo,
      libraryDependencies ++= tests
    )

lazy val documentationSettings = Seq(
  mdocVariables := Map(
    "VERSION" -> version.value
  ),
  micrositeName := repo,
  micrositeDescription := "Simple library to generate Markdown Tags",
  micrositeUrl := s"https://$username.github.io",
  micrositeBaseUrl := s"/$repo",
  micrositeHomepage := s"https://$username.github.io/$repo/",
  micrositeAuthor := "Maciej Romański Muninn Software",
  micrositeGithubOwner := username,
  micrositeGithubRepo := repo,
  micrositeHighlightTheme := "atom-one-light",
  micrositePushSiteWith := GHPagesPlugin
)

lazy val publishSettings = Seq(
  publishTo := sonatypePublishToBundle.value,
  sonatypeProjectHosting := Some(GitHubHosting(username, repo, "maciej.romanski@o2.pl")),
  homepage := Some(url(s"https://github.com/$username/$repo")),
  licenses += "MIT" -> url(s"https://github.com/$username/$repo/blob/master/LICENSE"),
  developers := List(
    Developer(
      id = username,
      name = "Maciej Romanski",
      email = "maciej.romanski@o2.pl",
      url = new URL(s"http://github.com/$username")
    )
  ),
  scmInfo := Some(ScmInfo(url(s"https://github.com/$username/$repo"), s"git@github.com:$username/$repo.git")),
  apiURL := Some(url(s"https://$username.github.io/$repo/latest/api/")),
  publishTo := sonatypePublishToBundle.value,
  publishMavenStyle := true,
  Test / publishArtifact := false,
//   Following 2 lines need to get around https://github.com/sbt/sbt/issues/4275
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  updateOptions := updateOptions.value.withGigahorse(false),
  releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    // For non cross-build projects, use releaseStepCommand("publishSigned")
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    releaseStepCommand("publishMicrosite"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)
