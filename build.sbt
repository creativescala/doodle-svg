lazy val scala213 = "2.13.8"
lazy val scala3 = "3.1.2"

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / useSuperShell := false
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

// Run this (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "clean" ::
    "compile" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    state
}
val createDocs = taskKey[Unit]("Produce documentation")
val previewDocs = taskKey[Unit]("Preview documentation")

lazy val svg =
  crossProject(JSPlatform, JVMPlatform)
    .settings(
      moduleName := "doodle-svg",
      scalacOptions ++= Seq("-release", "8"),
      startYear := Some(2015),
      licenses := List(
        "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")
      ),
      libraryDependencies ++= Seq(
        "org.creativescala" %%% "doodle" % "0.11.2",
        "com.lihaoyi" %%% "scalatags" % "0.11.1",
        "org.scalameta" %%% "munit" % "0.7.29" % Test,
        "org.typelevel" %%% "munit-cats-effect-3" % "1.0.7" % Test
      ),
      libraryDependencies ++= (
        if (scalaBinaryVersion.value == "2.13")
          compilerPlugin(
            "org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full
          ) :: Nil
        else Nil
      )
    )

lazy val svgJvm = svg.jvm
lazy val svgJs = svg.js
  .settings(
    mainClass := Some("doodle.svg.examples.ConcentricCircles")
  )

lazy val docs = project
  .in(file("docs"))
  .settings(
    createDocs := Def.sequential(mdoc.toTask(""), laikaSite).value,
    previewDocs := Def.sequential(mdoc.toTask(""), laikaPreview).value,
    mdocIn := baseDirectory.value / "src",
    Laika / sourceDirectories := Seq(mdocOut.value)
  )
  .enablePlugins(MdocPlugin, LaikaPlugin)
