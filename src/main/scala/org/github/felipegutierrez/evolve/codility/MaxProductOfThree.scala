package org.github.felipegutierrez.evolve.codility

/**
 * https://app.codility.com/programmers/lessons/6-sorting/max_product_of_three/
 * A[0] = -3
 * A[1] = 1
 * A[2] = 2
 * A[3] = -2
 * A[4] = 5
 * A[5] = 6
 *
 * process:
 * (0, 1, 2), product is −3 * 1 * 2 = −6
 * (0, 1, 3), = -3 * 1 * -2 = 6
 * (0, 1, 4), = -3 * 1 * 5  = -15
 * (0, 1, 5), = -3 * 1 * 6  = -18
 * (0, 2, 3), = -3 * 2 * -2 = 12
 * (0, 2, 4), = -3 * 2 * 5  = -30
 * (0, 2, 5), = -3 * 2 * 6  = -36
 * (0, 3, 4), = -3 * -2 * 5 = 30
 * (0, 3, 5), = -3 * -2 * 6 = 36
 * ...
 * (1, 2, 4), product is 1 * 2 * 5 = 10
 * (2, 4, 5), product is -2 * 5 * 6 = 60
 *
 * return: 60
 */
object MaxProductOfThree {
  def solution(a: Array[Int]): Int = {
    var maxProduct = 0
    for (pos0 <- 0 until (a.size - 2)) {
      for (pos1 <- pos0 + 1 until (a.size - 1)) {
        for (pos2 <- pos1 + 1 until (a.size)) {
          val currentMax = a(pos0) * a(pos1) * a(pos2)
          if (currentMax > maxProduct) maxProduct = currentMax
        }
      }
    }
    maxProduct
  }

  def solutionWithQuickSort(a: Array[Int]): Int = {
    scala.util.Sorting.quickSort(a)
    // the product of the last maximum three elements
    val pos = a.takeRight(3).product
    // if there are negative numbers take the 2 maximum negative numbers that will produce a positive product
    val neg = a(0) * a(1) * a.last
    // now compare the two results
    math.max(neg, pos)
  }
}
