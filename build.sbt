lazy val scala213 = "2.13.8"
lazy val scala3 = "3.1.2"

ThisBuild / tlBaseVersion := "0.1"

ThisBuild / organization := "org.creativescala"
ThisBuild / organizationName := "Creative Scala"

ThisBuild / startYear := Some(2015)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(tlGitHubDev("noelwelsh", "Noel Welsh"))

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / useSuperShell := false
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / tlSitePublishBranch := Some("main")
ThisBuild / scalacOptions ++= Seq("-release", "8")

Global / onChangedBuildSource := ReloadOnSourceChanges

// Run this (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "clean" ::
    "compile" ::
    "fastOptJS" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    "docs / tlSite" ::
    state
}
val createDocs = taskKey[Unit]("Produce documentation")
val previewDocs = taskKey[Unit]("Preview documentation")

lazy val root = tlCrossRootProject.aggregate(svg, docs, unidocs)

lazy val svg =
  crossProject(JSPlatform, JVMPlatform)
    .settings(
      moduleName := "doodle-svg",
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
    Compile / mainClass := Some("doodle.svg.examples.ConcentricCircles"),
    scalaJSUseMainModuleInitializer := true
  )

lazy val docs = project
  .in(file("docs"))
  .settings(
    createDocs := Def.sequential(mdoc.toTask(""), laikaSite).value,
    previewDocs := Def.sequential(mdoc.toTask(""), laikaPreview).value,
    mdocIn := baseDirectory.value / "src",
    Laika / sourceDirectories := Seq(
      mdocOut.value,
      (svgJs / Compile / fastOptJS / artifactPath).value
        .getParentFile() / s"${(svgJs / moduleName).value}-fastopt"
    ),
    laikaExtensions ++= Seq(
      laika.markdown.github.GitHubFlavor,
      laika.parse.code.SyntaxHighlighting
    )
  )
  .dependsOn(svgJvm)
  .enablePlugins(TypelevelSitePlugin)

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin) // also enables the ScalaUnidocPlugin
  .settings(
    name := "doodle-svg-docs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(
      svgJs
    )
  )
