package doodle
package svg
package algebra

import cats.effect.IO
import doodle.core.Point
import doodle.interact.algebra.MouseClick
import doodle.interact.algebra.MouseMove
import doodle.interact.algebra.Redraw
import doodle.svg.effect.Canvas
import fs2.Stream

trait CanvasAlgebra
    extends MouseClick[Canvas]
    with MouseMove[Canvas]
    with Redraw[Canvas] {

  def mouseClick(canvas: Canvas): Stream[IO, Point] =
    canvas.mouseClick

  def mouseMove(canvas: Canvas): Stream[IO, Point] =
    canvas.mouseMove

  def redraw(canvas: Canvas): Stream[IO, Int] =
    canvas.redraw
}
object CanvasAlgebra extends CanvasAlgebra
