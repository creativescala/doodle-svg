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

import doodle.effect.Writer
import doodle.effect.Writer._

package object svg {
  val jvm = new doodle.svg.algebra.JvmAlgebraModule {}

  // Need to re-export most of the things from JsAlgebraModule because directly
  // extending JsAlgebraModule from the package object leads to a compilation
  // error
  type Algebra[F[_]] = jvm.Algebra[F]
  val algebraInstance = jvm.algebraInstance
  type Drawing[A] = jvm.Drawing[A]
  val Svg = jvm.Svg
  type Tag = jvm.Tag
  type Frame = doodle.svg.effect.Frame
  val Frame = doodle.svg.effect.Frame
  implicit val svgWriter: Writer[Algebra, Drawing, Frame, Svg] =
    doodle.svg.effect.SvgWriter

  type Picture[A] = doodle.algebra.Picture[Algebra, Drawing, A]
  object Picture {
    def apply(f: Algebra[Drawing] => Drawing[Unit]): Picture[Unit] = {
      new Picture[Unit] {
        def apply(implicit algebra: Algebra[Drawing]): Drawing[Unit] =
          f(algebra)
      }
    }
  }
}
