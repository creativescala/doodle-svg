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
        .fillColor(Color.skyBlue.spin((count * 10).degrees))
  .on(circles(count - 1))

  val run =
    for {
      canvas <- Frame("svg-root").canvas()
      a <- circles(10).drawWithCanvasToIO(canvas)
    } yield a

}
