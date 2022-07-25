package doodle.svg.examples

import cats.effect.IOApp
import doodle.core._
import doodle.svg._
import doodle.syntax.all._

object ConcentricCircles extends IOApp.Simple {
  def circles(count: Int): Picture[Unit] =
    if (count <= 0) Picture.empty
    else
      Picture
        .circle(count * 20)
        .fillColor(Color.skyBlue.spin((count * 10).degrees))
        .under(circles(count - 1))

  val run =
    for {
      canvas <- Frame("svg-root").canvas()
      a <- circles(10).drawWithCanvasToIO(canvas)
    } yield a

}
