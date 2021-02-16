package org.github.felipegutierrez.explore.recap.codility.challenge

/**
 * +----+-------+------+-------+
 * | id |  name |  age | score |
 * +----+-------+------+-------+
 * |  1 |  Jack | NULL |    12 |
 * +----+-------+------+-------+
 * | 17 | Betty |   28 |    11 |
 * +----+-------+------+-------+
 *
 * removing the defect row
 * +----+-------+-----+-------+
 * | id |  name | age | score |
 * +----+-------+-----+-------+
 * | 17 | Betty |  28 |    11 |
 * +----+-------+-----+-------+
 *
 * +--------+----------+
 * | header |   header |
 * +--------+----------+
 * |  ANNUL | ANNULLED |
 * +--------+----------+
 * |   null |     NILL |
 * +--------+----------+
 * |   NULL |     NULL |
 * +--------+----------+
 * result in:
 * +--------+----------+
 * | header |   header |
 * +--------+----------+
 * |  ANNUL | ANNULLED |
 * +--------+----------+
 * |   null |     NILL |
 * +--------+----------+
 *
 * +---------+------------+-----------+
 * | country | population |      area |
 * +---------+------------+-----------+
 * |      UK |        67m | 242000km2 |
 * +---------+------------+-----------+
 */
object TableWithDefectRow {
  def solution(s: String): String = {
    val table = s.split("\n")
    val header = table(0)
    val tableWithOutHeader = table.drop(1)

    val tableWithOutNull = tableWithOutHeader
      .filterNot(_.contains("NULL,"))
      .filterNot(_.contains(",NULL"))

    header + "\n" + tableWithOutNull.mkString("\n")
  }
}
