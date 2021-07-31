package cl.ravenhill.makarena.model.player

import cl.ravenhill.makarena.model.Card
import java.util.*

open class Player(val name: String) {
  private val rawDeck = mutableListOf<Card>()
  val deck
    get() = rawDeck.toList()

  private val rawHand = mutableListOf<Card>()
  val hand
    get() = rawHand.toList()

  val graveyard = mutableListOf<Card>()

  override fun equals(other: Any?) = if (other is Player) {
    this.name == other.name
  } else false

  fun draw() {
    rawHand += rawDeck.removeFirst()
  }

  fun addToDeck(card: Card) {
    rawDeck += card
  }

  override fun hashCode() = Objects.hash(Player::class, name)
}