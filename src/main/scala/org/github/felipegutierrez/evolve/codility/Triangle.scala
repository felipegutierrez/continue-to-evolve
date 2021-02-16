package org.github.felipegutierrez.evolve.codility

/**
 * https://app.codility.com/programmers/lessons/6-sorting/triangle/
 *
 * A triplet (P, Q, R) is triangular if 0 ≤ P < Q < R < N and:
 * A[P] + A[Q] > A[R],
 * A[Q] + A[R] > A[P],
 * A[R] + A[P] > A[Q].
 *
 * A[0] = 10
 * A[1] = 2
 * A[2] = 5
 * A[3] = 1
 * A[4] = 8
 * A[5] = 20
 * Triplet (0, 2, 4) is triangular.
 * 0 <= 0 < 2 < 4 < 6 = true
 * 10, 5, 8 =>
 * A[P] + A[Q] > A[R] => 10 + 5 > 8  = true
 * A[Q] + A[R] > A[P] => 5  + 8 > 10 = true
 * A[R] + A[P] > A[Q] => 8 + 10 > 5  = true
 *
 * A[0] = 10
 * A[1] = 50
 * A[2] = 5
 * A[3] = 1
 * is not triangular
 * 0,1,2
 * A[P] + A[Q] > A[R] => 10 + 50 > 5  = true
 * A[Q] + A[R] > A[P] => 50 + 5  > 10 = true
 * A[R] + A[P] > A[Q] => 5  + 10 > 50 = false
 */
object Triangle {
  def solution(a: Array[Int]): Int = {
    // process 3 nested loop to guarantee order of positions
    for (posP <- 0 until (a.size - 2)) { // process until last position of P
      for (posQ <- posP + 1 until (a.size - 1)) { // process until last position of Q
        for (posR <- posQ + 1 until a.size) { // process until last position of R
          if (((a(posP) + a(posQ)) > a(posR))
            && ((a(posQ) + a(posR)) > a(posP))
            && ((a(posR) + a(posP)) > a(posQ))) return 1
        }
      }
    }
    0
  }

  /**
   * A triplet (P, Q, R) is triangular if 0 ≤ P < Q < R < N and:
   * A[P] + A[Q] > A[R],
   * A[Q] + A[R] > A[P],
   * A[R] + A[P] > A[Q].
   *
   * A[0] = 10 , A[1] = 2 , A[2] = 5 , A[3] = 1 , A[4] = 8 , A[5] = 20
   * 1, 2, 5, 8, 10, 20
   * Triplet (0, 2, 4) is triangular => 5, 8, 10
   * 5, 8, 10 =>
   * A[P] + A[Q] > A[R] => 5 + 8  > 10  = true
   * A[Q] + A[R] > A[P] => 8 + 10 > 5  = true
   * A[R] + A[P] > A[Q] => 10 + 5 > 8  = true
   *
   * A[0] = 10 , A[1] = 50 , A[2] = 5 , A[3] = 1
   * is not triangular => 1, 5, 10, 50
   * 5, 10, 50
   * A[P] + A[Q] > A[R] => 5 + 10 > 50  = false
   * A[Q] + A[R] > A[P] => 50 + 5  > 10 = true
   * A[R] + A[P] > A[Q] => 5  + 10 > 50 = false
   *
   * @param a
   * @return
   */
  def solutionSort(a: Array[Int]): Int = {
    scala.util.Sorting.quickSort(a)
    for (pos <- 2 until a.size) {
      if (a(pos - 2) + a(pos - 1) > a(pos)) return 1
    }
    0
  }
}
