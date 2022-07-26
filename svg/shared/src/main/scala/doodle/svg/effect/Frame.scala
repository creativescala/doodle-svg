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
package effect

import doodle.core.Color

final case class Frame(
    id: String,
    size: Size,
    background: Option[Color] = None
) {
  def background(color: Color): Frame =
    this.copy(background = Some(color))

  def fitToPicture(border: Int = 20): Frame =
    this.copy(size = Size.fitToPicture(border))

  def size(width: Double, height: Double): Frame =
    this.copy(size = Size.fixedSize(width, height))
}
object Frame {
  def apply(id: String): Frame =
    Frame(id, Size.fitToPicture(), None)
}
