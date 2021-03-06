package doodle
package svg
package effect

import doodle.algebra.generic._
import doodle.core._
import doodle.language.Basic
import munit.CatsEffectSuite

import scala.collection.mutable

class SvgSpec
    extends CatsEffectSuite
    with doodle.svg.algebra.TestAlgebraModule {
  import scalatags.Text.{svgAttrs, svgTags}
  import scalatags.Text.implicits._

  val blackStroke = Stroke(Color.black, 1.0, Cap.butt, Join.miter, None)

  test("circle renders to svg circle") {
    val diameter = 10.0
    val circle = doodle.algebra.Picture[Basic, Drawing, Unit](algebra =>
      algebra.strokeColor(algebra.circle(diameter), Color.black)
    )
    val expected =
      svgTags.g(
        svgTags.defs(),
        svgTags.circle(
          svgAttrs.transform := Svg.toSvgTransform(
            Transform.verticalReflection
          ),
          svgAttrs.style := Svg
            .toStyle(Some(blackStroke), None, mutable.Set.empty),
          svgAttrs.r := (diameter / 2.0)
        )
      )

    Svg
      .renderWithoutRootTag(algebraInstance, circle)
      .map { case (_, elt, _) => elt }
      .assertEquals(expected)
  }

  test("paths of path elements render correctly") {
    import doodle.core.PathElement._
    val path1 = "M 0,0 M 5,5 L 10,10 C 20,20 30,30 40,40 "
    val path2 = "M 0,0 M 5,5 L 10,10 C 20,20 30,30 40,40 Z"

    assertEquals(
      Svg
        .toSvgPath(
          List(moveTo(5, 5), lineTo(10, 10), curveTo(20, 20, 30, 30, 40, 40)),
          Svg.Open
        ),
      path1
    )

    assertEquals(
      Svg
        .toSvgPath(
          List(moveTo(5, 5), lineTo(10, 10), curveTo(20, 20, 30, 30, 40, 40)),
          Svg.Closed
        ),
      path2
    )
  }

  test("paths of points render correctly") {
    val path1 = "M 5,5 L 10,10 L 20,20 "
    val path2 = "M 5,5 L 10,10 L 20,20 Z"
    assertEquals(
      Svg.toSvgPath(
        Array(Point(5, 5), Point(10, 10), Point(20, 20)),
        Svg.Open
      ),
      path1
    )
    assertEquals(
      Svg
        .toSvgPath(
          Array(Point(5, 5), Point(10, 10), Point(20, 20)),
          Svg.Closed
        ),
      path2
    )
  }
}
