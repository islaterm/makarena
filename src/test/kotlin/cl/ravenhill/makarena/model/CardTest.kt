package cl.ravenhill.makarena.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import io.kotest.property.forAll


class CardTest : StringSpec({
  "Cards with the same parameters are equal" {
    checkAll(nameGenerator()) { a ->
      Card(a) shouldNotBeSameInstanceAs Card(a)
      Card(a) shouldBe Card(a)
    }
  }
  "Cards with different parameters are not equal" {
    val names = Pair(nameGenerator(), nameGenerator())
    checkAll(names.first, names.second) { a, b ->
      if (a != b) {
        Card(a) shouldNotBe Card(b)
      }
    }
  }
})