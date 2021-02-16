package org.github.felipegutierrez.evolve.codility

import org.scalatest.flatspec.AnyFlatSpec

class MaxProductOfThreeSpec extends AnyFlatSpec {
  "the max product of three any position in an array" should
    "return correct values" in {
    assertResult(60)(MaxProductOfThree.solution(Array[Int](-3, 1, 2, -2, 5, 6)))
    assertResult(200)(MaxProductOfThree.solution(Array[Int](4, 2, 2, 5, 1, 5, 8)))
    assertResult(200)(MaxProductOfThree.solution(Array[Int](4, 2, 2, -5, 1, 5, -8)))
  }
  "the max product of three any position in an array using quick sort" should
    "return correct values" in {
    assertResult(60)(MaxProductOfThree.solutionWithQuickSort(Array[Int](-3, 1, 2, -2, 5, 6)))
    assertResult(200)(MaxProductOfThree.solutionWithQuickSort(Array[Int](4, 2, 2, 5, 1, 5, 8)))
    assertResult(200)(MaxProductOfThree.solutionWithQuickSort(Array[Int](4, 2, 2, -5, 1, 5, -8)))
  }
}
