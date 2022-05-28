package doodle
package svg
package effect

import cats.Monoid
import cats.effect.IO
import doodle.effect.Renderer
import doodle.interact.effect.AnimationRenderer
import fs2.Stream

object SvgAnimationRenderer extends AnimationRenderer[Canvas] {
  def animate[Alg[x[_]] <: doodle.algebra.Algebra[x], F[_], A, Frm](
      canvas: Canvas
  )(frames: Stream[IO, doodle.algebra.Picture[Alg, F, A]])(implicit
      e: Renderer[Alg, F, Frm, Canvas],
      m: Monoid[A]
  ): IO[A] =
    frames
      .evalMap(e.render(canvas))
      .compile
      .foldMonoid
}
