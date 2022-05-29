package doodle
package svg
package algebra

import doodle.algebra.Layout
import doodle.algebra.Size
import doodle.algebra.generic._
import doodle.language.Basic

trait AlgebraModule {
  self: Base with ShapeModule with PathModule with SvgModule =>
  trait BaseAlgebra
      extends doodle.algebra.Algebra[Drawing]
      with Layout[Drawing]
      with Size[Drawing]
      with Shape
      with Path
      with GenericDebug[SvgResult]
      with GenericLayout[SvgResult]
      with GenericSize[SvgResult]
      with GenericStyle[SvgResult]
      with GenericTransform[SvgResult]
      with GivenApply[SvgResult]
      with GivenFunctor[SvgResult]
      with Basic[Drawing]
}
