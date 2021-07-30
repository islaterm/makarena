package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.model.player.Player
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.property.Arb
import io.kotest.property.arbitrary.bind
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import io.kotest.property.forAll

class PlayerTest : StringSpec({
  val cardGen = Arb.bind(nameGenerator(), attackGenerator()) { name, attack ->
    CardGen(name, attack)
  }
  "Players with same parameters are equal" {
    forAll(nameGenerator()) { name -> Player(name) == Player(name) }
  }
  "Deck is created correctly" {
    checkAll(Arb.list(cardGen, 45..60), nameGenerator()) {generated, playerName ->
      val player = Player(playerName)
      val cards = mutableListOf<Card>()
      player.deck shouldHaveSize 0
      for (c in generated) {
        cards.add(Card(c.name, c.attack))
      }
      for ((i, card) in cards.withIndex()) {
        player.addToDeck(card)
        player.deck shouldHaveSize i + 1
        player.deck shouldContain card
      }
    }
  }
//  "The player can draw a card successfully" {
//    val generators = Array(2) { cardGen }
//    forAll(Arb.list(cardGen, 1..20), nameGenerator()) { cards, playerName ->
//      val player = Player(playerName)
//      player.hand.size shouldBe 0
//      for (i in cards.indices) {
//        player.draw()
//        player.hand.size shouldBe i
//      }
//      true
//    }
//  }
})

data class CardGen(val name: String, val attack: Int)

