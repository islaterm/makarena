package cl.ravenhill.makarena.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.spec.style.scopes.StringSpecScope
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll


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

/**
 * Basic test suite for all creatures
 */
class CreatureTest : StringSpec({
  "Cards with the same parameters are equal" {
    checkAll(nameGenerator(), attackGenerator()) { name, attack ->
      Creature(name, attack) shouldNotBeSameInstanceAs Creature(name, attack)
      Creature(name, attack) shouldBe Creature(name, attack)
      TODO("Check hash code")
    }
  }
  "Creatures with the same name and different attack should be equal" {
    checkAll(nameGenerator(), attackGenerator()) { name, attack ->
      Creature(name, attack) shouldBe Creature(name, attack + 1)
    }
  }
  "Creature attack is initialized correctly" {

  }
})

/**
 * Arbitrary value generator to represent a creature attack (a random number between 0 an 10)
 */
private fun attackGenerator() = Arb.int(0..10)

