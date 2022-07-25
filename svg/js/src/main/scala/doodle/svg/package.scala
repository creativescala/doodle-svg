package doodle

import doodle.effect.Renderer
import doodle.interact.effect.AnimationRenderer

package object svg {
  val js = new doodle.svg.algebra.JsAlgebraModule {}

  // Need to re-export most of the things from JsAlgebraModule because directly
  // extending JsAlgebraModule from the package object leads to a compilation
  // error
  type Algebra[F[_]] = js.Algebra[F]
  type Drawing[A] = js.Drawing[A]
  val Svg = js.Svg
  type Tag = js.Tag
  type Frame = doodle.svg.effect.Frame
  type Canvas = doodle.svg.effect.Canvas
  implicit val svgRenderer: Renderer[Algebra, Drawing, Frame, Canvas] =
    doodle.svg.effect.SvgRenderer
  implicit val svgAnimationRenderer: AnimationRenderer[Canvas] =
    doodle.svg.effect.SvgAnimationRenderer
  implicit val svgCanvas: doodle.svg.algebra.CanvasAlgebra =
    doodle.svg.algebra.CanvasAlgebra

  val Frame = doodle.svg.effect.Frame

  type Picture[A] = doodle.algebra.Picture[Algebra, Drawing, A]
  object Picture
      extends doodle.algebra.BaseConstructor
      with doodle.algebra.PathConstructor
      with doodle.algebra.ShapeConstructor
      with doodle.algebra.TextConstructor {

    type Algebra[x[_]] = svg.Algebra[x]
    type Drawing[A] = svg.Drawing[A]
  }
}
