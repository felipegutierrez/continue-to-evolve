package org.github.felipegutierrez.evolve.codility

import org.scalatest.flatspec.AnyFlatSpec

class TriangleSpec extends AnyFlatSpec {
  "the Triangle triple algorithm" should
    "return 1 if a triple is found or 0 if not" in {
    assertResult(1)(Triangle.solution(Array[Int](10, 2, 5, 1, 8, 20)))
    assertResult(0)(Triangle.solution(Array[Int](10, 50, 5, 1)))
  }
  "the Triangle triple recursive algorithm" should
    "return 1 if a triple is found or 0 if not" in {
    assertResult(1)(Triangle.solutionSort(Array[Int](10, 2, 5, 1, 8, 20)))
    assertResult(0)(Triangle.solutionSort(Array[Int](10, 50, 5, 1)))
  }
}
