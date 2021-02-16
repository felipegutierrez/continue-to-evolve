package org.github.felipegutierrez.evolve.codility

/**
 * https://app.codility.com/programmers/lessons/6-sorting/number_of_disc_intersections/
 *
 * A[0] = 1
 * A[1] = 5
 * A[2] = 2
 * A[3] = 1
 * A[4] = 4
 * A[5] = 0
 *
 * intersections found:
 * 0th and 1st
 * 0th and 2nd
 * 0th and 4th
 * 1st and 2nd
 * 1st and 3rd
 * 1st and 4th
 * 1st and 5th
 * 2nd and 3rd
 * 2nd and 4th
 * 3rd and 4th
 * 4th and 5th
 *
 */
object NumberOfDiscIntersections {
  /**
   * extract the limits to the left and right of each position on the array.
   * A[0] = 1 --> (0-1, 0+1) = A0(-1, 1)
   * A[1] = 5 --> (1-5, 1+5) = A1(-4, 6)
   * A[2] = 2 --> (2-2, 2+2) = A2(0, 4)
   * A[3] = 1 --> (3-1, 3+1) = A3(2, 4)
   * A[4] = 4 --> (4-4, 4+4) = A4(0, 8)
   * A[5] = 0 --> (5-0, 5+0) = A5(5, 5)
   *
   * check if there is intersections between any two positions
   * (A0_0 >= A1_0 AND A0_0 <= A1_1) OR // intersection
   * (A0_1 >= A1_0 AND A0_1 <= A1_1) OR // intersection
   * (A0_0 <= A1_0 AND A0_1 >= A1_1)    // one circle contain inside the other
   * if any of these two checks is true count one intersection.
   *
   * @param a
   * @return
   */
  def solution(a: Array[Int]): Int = {
    var count: Long = 0
    for (posI: Long <- 0L until a.size) {
      for (posJ <- (posI + 1) until a.size) {
        val tupleI = (posI - a(posI.toInt), posI + a(posI.toInt))
        val tupleJ = (posJ - a(posJ.toInt), posJ + a(posJ.toInt))
        if ((tupleI._1 >= tupleJ._1 && tupleI._1 <= tupleJ._2) ||
          (tupleI._2 >= tupleJ._1 && tupleI._2 <= tupleJ._2) ||
          (tupleI._1 <= tupleJ._1 && tupleI._2 >= tupleJ._2)) {
          count += 1
          if (count > 10000000) return -1
        }
      }
    }
    count.toInt
  }
}
