package cl.ravenhill.makarena.model.player

import cl.ravenhill.makarena.model.Card

open class Player(val name: String) {
  private val rawDeck = mutableListOf<Card>()
  val deck: List<Card>
    get() = rawDeck.toList()
  val hand = mutableListOf<Card>()
  val graveyard = mutableListOf<Card>()

  override fun equals(other: Any?): Boolean {
    return if (other is Player) {
      this.name == other.name
    } else false
  }

  fun draw() {
    TODO("Not yet implemented")
  }

  fun addToDeck(card: Card) {
    rawDeck.add(card)
  }
}