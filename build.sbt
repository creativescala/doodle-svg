import scala.sys.process._
import laika.rewrite.link.LinkConfig
import laika.rewrite.link.ApiLinks
import laika.theme.Theme

lazy val scala213 = "2.13.11"
lazy val scala3 = "3.3.0"

ThisBuild / tlBaseVersion := "0.16"

ThisBuild / organization := "org.creativescala"
ThisBuild / organizationName := "Creative Scala"

ThisBuild / startYear := Some(2015)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(tlGitHubDev("noelwelsh", "Noel Welsh"))

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / useSuperShell := false
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / tlSitePublishBranch := Some("main")

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
val css = taskKey[Unit]("Build the CSS")
val createDocs = taskKey[Unit]("Produce documentation")
val previewDocs = taskKey[Unit]("Preview documentation")

val doodleVersion = "0.19.0"

lazy val root = tlCrossRootProject.aggregate(svg, docs, unidocs)

lazy val svg =
  crossProject(JSPlatform, JVMPlatform)
    .settings(
      moduleName := "doodle-svg",
      libraryDependencies ++= Seq(
        "org.creativescala" %%% "doodle-core" % doodleVersion,
        "com.lihaoyi" %%% "scalatags" % "0.12.0",
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
  .settings(
    libraryDependencies += "org.creativescala" %%% "doodle-java2d" % doodleVersion
  )
lazy val svgJs = svg.js
  .settings(
    libraryDependencies += "org.creativescala" %%% "doodle-interact" % doodleVersion,
    Compile / mainClass := Some("doodle.svg.examples.ConcentricCircles")
  )

lazy val examples =
  project
    .in(file("examples"))
    .dependsOn(svgJs)
    .enablePlugins(ScalaJSPlugin)

lazy val docs =
  project
    .in(file("docs"))
    .settings(
      laikaConfig := laikaConfig.value.withConfigValue(
        LinkConfig(apiLinks =
          Seq(
            ApiLinks(baseUri =
              "https://javadoc.io/doc/org.creativescala/doodle-svg-docs_3/latest/"
            )
          )
        )
      ),
      mdocVariables := {
        mdocVariables.value ++ Map("DOODLE_VERSION" -> doodleVersion)
      },
      mdocIn := file("docs/src/pages"),
      css := {
        val src = file("docs/src/css")
        val dest1 = mdocOut.value
        val dest2 = (laikaSite / target).value
        val cmd1 =
          s"npx tailwindcss -i ${src.toString}/creative-scala.css -o ${dest1.toString}/creative-scala.css"
        val cmd2 =
          s"npx tailwindcss -i ${src.toString}/creative-scala.css -o ${dest2.toString}/creative-scala.css"
        cmd1 !

        cmd2 !
      },
      Laika / sourceDirectories += file("docs/src/templates"),
      Laika / sourceDirectories +=
        (examples / Compile / fastOptJS / artifactPath).value
          .getParentFile() / s"${(examples / moduleName).value}-fastopt",
      laikaTheme := Theme.empty,
      laikaExtensions ++= Seq(
        laika.markdown.github.GitHubFlavor,
        laika.parse.code.SyntaxHighlighting,
        CreativeScalaDirectives
      ),
      tlSite := Def
        .sequential(
          (examples / Compile / fastLinkJS),
          mdoc.toTask(""),
          css,
          laikaSite
        )
        .value
    )
    .dependsOn(examples)
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
