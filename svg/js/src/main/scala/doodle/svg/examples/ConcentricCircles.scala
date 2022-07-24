package doodle.svg.examples

import doodle.core._
import doodle.syntax.all._
import doodle.svg._
import cats.effect.IOApp

object ConcentricCircles extends IOApp.Simple {
  def circles(count: Int): Picture[Unit] =
    if (count == 0) Picture.empty
    else
      Picture
        .circle(count * 20)
        .fillColor(Color.peach.spin((count * 10).degrees))
  .on(circles(count - 1))

  def main(args: Array[String]): Unit =
    circles(10).drawWithFrame(Frame("svg-root"))
}
