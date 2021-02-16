package org.github.felipegutierrez.evolve.advanced

import org.github.felipegutierrez.explore.recap.AdvancedHighOrderFunction
import org.scalatest.flatspec.AnyFlatSpec

class AdvancedHighOrderFunctionSpec extends AnyFlatSpec {

  "a higher order function that contains a partial function" should
    "crash if any value is not defined inside the partial function" in {
    val listOne = List(1, 2, 5)
    val listOneResult = AdvancedHighOrderFunction.higherOrderFunctionWithPartialFunction(listOne)
    assertResult(List(11, 22, 55))(listOneResult)

    try {
      val listTwo = List(1, 2, 3)
      AdvancedHighOrderFunction.higherOrderFunctionWithPartialFunction(listTwo)
    } catch {
      case exception => assert(exception.toString.contains("MatchError"))
    }
  }
}
