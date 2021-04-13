package org.github.felipegutierrez.evolve.codility

import org.scalatest.flatspec.AnyFlatSpec

class NumberOfDiscIntersectionsSpec extends AnyFlatSpec {
  "the number of disc intersections" should
    "return all intersections of discs in an array" in {
    assertResult(11)(NumberOfDiscIntersections.solution(Array[Int](1, 5, 2, 1, 4, 0)))
  }
}
