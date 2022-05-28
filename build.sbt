lazy val scala213 = "2.13.8"
lazy val scala3 = "3.1.2"

ThisBuild / crossScalaVersions := List(scala3, scala213)
ThisBuild / scalaVersion := crossScalaVersions.value.head
ThisBuild / useSuperShell := false
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0"
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

// Run this (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "compile" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    state
}

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
        "org.creativescala" %%% "doodle" % "0.10.1",
        "com.lihaoyi" %%% "scalatags" % "0.11.1",
        "org.scalameta" %%% "munit" % "0.7.29" % "test"
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
