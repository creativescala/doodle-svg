# Doodle SVG

A [Doodle](https://github.com/creativescala/doodle) backend for SVG.

This project draws Doodle pictures to SVG, both in the browser using [Scala JS](https://scala-js.org/) and to files on the JVM.


## Getting Started

Add the following dependency to your `build.sbt`:

```scala
libraryDependencies += "org.creativescala" %%% "doodle-svg" %% ${version}
```


## Usage

Firstly, bring everything into scope

```scala mdoc:silent
import doodle.svg._
```

Now what you can do depends on whether you are running in the browser or on the JVM. In the browser you can draw a picture to SVG. To do this, construct a `Frame` with the `id` of the DOM element where you'd like the picture drawn.

For example.


<div id="svg-root"></div>

