lazy val root = project
  .in(file("."))
  .settings(
    name                                   := "scala-md-tag",
    organization                           := "pl.muninn",
    description                            := "Scala markdown library",
    version                                := "1.0.0",
    scalaVersion                           := "3.1.0",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

lazy val docs = project
  .in(file("documentation"))
  .settings(
    moduleName := "scala-md-tag-docs",
    mdocOut    := file("website") / "docs"
  )
  .enablePlugins(MdocPlugin, DocusaurusPlugin)
