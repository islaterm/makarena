package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.model.player.Player
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeIn
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
    checkAll(Arb.list(cardGen, 45..60), nameGenerator()) { generated, playerName ->
      val player = Player(playerName)
      val cards = createCardPool(generated)
      player.deck shouldHaveSize 0
      `move and check`(cards, player::addToDeck, player::deck)
    }
  }
  "The player can draw a card successfully" {
    checkAll(Arb.list(cardGen, 45..60), nameGenerator()) { generated, playerName ->
      val player = Player(playerName)
      val cards = createCardPool(generated)
      for (card in cards) {
        player.addToDeck(card)
      }
      player.hand shouldHaveSize 0
      `check draw`(player)
    }
  }
})

fun `check draw`(player: Player) {
  var handSize = 0
  var deckSize = player.deck.size
  while (player.deck.isNotEmpty()) {
    val movedCard = player.deck.first()
    player.draw()
    player.hand shouldHaveSize ++handSize
    player.deck shouldHaveSize --deckSize
    assert(movedCard !in player.deck)
    movedCard shouldBeIn player.hand
  }
}

/**
 * Adds a card to a list and check the result.
 *
 * @param player Player
 *    test player
 * @param cards List<Card>
 *    the list of cards to add
 */
fun `move and check`(
  cards: Array<Card>,
  adder: (Card) -> Unit,
  getter: () -> List<Card>
) {
  for ((i, card) in cards.withIndex()) {
    adder(card)
    getter() shouldHaveSize i + 1
    getter() shouldContain card
  }
}

fun createCardPool(source: List<CardGen>) =
  Array(source.size) { Card(source[it].name, source[it].attack) }


data class CardGen(val name: String, val attack: Int)

