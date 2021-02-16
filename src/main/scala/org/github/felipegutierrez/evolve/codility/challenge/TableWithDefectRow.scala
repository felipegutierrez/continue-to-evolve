package org.github.felipegutierrez.evolve.codility.challenge

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

/*
  create table tasks (
      id integer not null,
      name varchar(40) not null,
      unique(id)
  );

  create table reports (
      id integer not null,
      task_id integer not null,
      candidate varchar(40) not null,
      score integer not null,
      unique(id)
  );

insert into tasks values (101, 'MinDist');
insert into tasks values (123, 'Equi');
insert into tasks values (142, 'Median');
insert into tasks values (300, 'Tricoloring');
insert into reports values (13, 101, 'John Smith', 100);
insert into reports values (24, 123, 'Delaney Lloyd', 34);
insert into reports values (37, 300, 'Monroe Jimenez', 50);
insert into reports values (49, 101, 'Stanley Price', 45);
insert into reports values (51, 142, 'Tanner Sears', 37);
insert into reports values (68, 142, 'Lara Fraser', 3);
insert into reports values (83, 300, 'Tanner Sears', 0);


SELECT R.task_id, T.name AS task_name,
CASE
	WHEN (AVG(R.score) <= 20) THEN 'Hard'
	WHEN (AVG(R.score) > 20 AND AVG(R.score) <= 60) THEN 'Medium'
	WHEN (AVG(R.score) > 60) THEN 'Easy'
END AS difficulty
FROM tasks AS T INNER JOIN reports AS R ON (T.id = R.task_id)
GROUP BY R.task_id, T.name
ORDER BY R.task_id;


SELECT R.task_id, T.name AS task_name,
CASE
        WHEN (AVG(R.score) <= 20) THEN 'Hard'
        WHEN (AVG(R.score) > 20 AND AVG(R.score) <= 60) THEN 'Medium'
        WHEN (AVG(R.score) > 60) THEN 'Easy'
END AS difficulty
FROM tasks AS T FULL OUTER JOIN reports AS R ON (T.id = R.task_id)
GROUP BY R.task_id, T.name
ORDER BY R.task_id;
 */