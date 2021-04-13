package org.github.felipegutierrez.evolve.codility.challenge

import org.scalatest.flatspec.AnyFlatSpec

class TableWithDefectRowSpec extends AnyFlatSpec {
  "a table with defect rows" should
    "return " in {
    assertResult("id,name,age,score\n17,Betty,28,11")(TableWithDefectRow.solution("id,name,age,score\n1,Jack,NULL,12\n17,Betty,28,11"))
    assertResult("id,name,age,NULL\n17,Betty,28,11")(TableWithDefectRow.solution("id,name,age,NULL\n1,Jack,NULL,12\n17,Betty,28,11"))
    assertResult("header,header\nANNUL,ANNULLED\nnull,NILL")(TableWithDefectRow.solution("header,header\nANNUL,ANNULLED\nnull,NILL\nNULL,NULL"))
    assertResult("header,header\nANNUL,ANNULLED\nnull,NILL")(TableWithDefectRow.solution("header,header\nANNUL,ANNULLED\nnull,NILL\naaa,NULL"))
    assertResult("header,header\nANNUL,ANNULLED\nnull,NILL")(TableWithDefectRow.solution("header,header\nANNUL,ANNULLED\nnull,NILL\nNULL,aaa"))
    assertResult("header,NULL\nANNUL,ANNULLED\nnull,NILL")(TableWithDefectRow.solution("header,NULL\nANNUL,ANNULLED\nnull,NILL\nNULL,NULL"))
    assertResult("NULL,header\nANNUL,ANNULLED\nnull,NILL")(TableWithDefectRow.solution("NULL,header\nANNUL,ANNULLED\nnull,NILL\nNULL,NULL"))
    assertResult("country,population,area\nUK,67m,242000km2")(TableWithDefectRow.solution("country,population,area\nUK,67m,242000km2"))
    assertResult("country,NULL,area\nUK,67m,242000km2")(TableWithDefectRow.solution("country,NULL,area\nUK,67m,242000km2"))
    assertResult("country,NULL,area\nUK,67m,242000km2")(TableWithDefectRow.solution("country,NULL,area\nUK,67m,242000km2\nUK,NULL,242000km2"))
  }
}
