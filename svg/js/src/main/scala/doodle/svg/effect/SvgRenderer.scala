package doodle
package svg
package effect

import cats.effect.IO
import doodle.effect.Renderer
import cats.effect.unsafe.IORuntime

object SvgRenderer extends Renderer[Algebra, Drawing, Frame, Canvas] {

  import cats.effect.unsafe.implicits.global

  def canvas(description: Frame): IO[Canvas] =
    Canvas.fromFrame(description)

  def render[A](canvas: Canvas)(picture: Picture[A]): IO[A] =
    canvas.render(picture)
}
