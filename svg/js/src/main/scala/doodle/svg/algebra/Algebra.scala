/*
 * Copyright 2015 Creative Scala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package doodle
package svg
package algebra

import cats._
import doodle.core.BoundingBox
import doodle.core.font.Font
import doodle.language.Basic
import doodle.svg.effect.Canvas
import org.scalajs.dom.svg.Rect

trait JsAlgebraModule
    extends AlgebraModule
    with PathModule
    with ShapeModule
    with SvgModule
    with TextModule
    with JsBase {
  type Algebra[F[_]] = doodle.algebra.Algebra[F]
    with doodle.algebra.Text[F]
    with Basic[F]

  final class JsAlgebra(
      val canvas: Canvas,
      val applyF: Apply[SvgResult],
      val functorF: Functor[SvgResult]
  ) extends BaseAlgebra
      with HasTextBoundingBox
      with Text {
    def textBoundingBox(text: String, font: Font): (BoundingBox, Rect) =
      canvas.textBoundingBox(text, font)
  }
}
