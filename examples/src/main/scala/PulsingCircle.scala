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
package examples

import doodle.core._
import doodle.svg._
import doodle.syntax.all._
import doodle.interact.syntax.all._
import scala.scalajs.js.annotation._
import cats.effect.unsafe.implicits.global

@JSExportTopLevel("PulsingCircle")
object PulsingCircle {
  def circle(count: Double): Picture[Unit] =
    Picture
      .circle(2 * count + 10)
      .fillColor(Color.hsl((count * 5).degrees, 0.7, 0.6))

  @JSExport
  def draw(mount: String) =
    0.0
      .upToIncluding(72.0)
      .map(c => circle(c))
      .forSteps(120)
      .andThen(_ => 72.0.upToIncluding(0.0).map(c => circle(c)).forSteps(120))
      .repeatForever
      .animate(Frame(mount).withSize(72 * 2 + 10, 72 * 2 + 10))
}
