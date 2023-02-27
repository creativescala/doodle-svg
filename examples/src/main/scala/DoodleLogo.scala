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
import doodle.core.font._
import doodle.svg._
import doodle.syntax.all._
import scala.scalajs.js.annotation._
import cats.effect.unsafe.implicits.global

@JSExportTopLevel("DoodleLogo")
object DoodleLogo {
  val font = Font.defaultSansSerif.size(FontSize.points(22))
  val logo: Picture[Unit] =
    Picture
      .text("Doodle SVG")
      .font(font)
      .fillColor(Color.red)
      .on(
        Picture.text("Doodle SVG").font(font).fillColor(Color.blue).at(20, 20)
      )
      .strokeWidth(7)

  @JSExport
  def draw(mount: String) =
    logo.drawWithFrame(Frame(mount))
}
