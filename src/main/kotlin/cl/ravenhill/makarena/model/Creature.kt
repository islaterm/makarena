package cl.ravenhill.makarena.model

/**
 * This class represents a Creature card.
 * @constructor Creates a new creature with its original values.
 * @property originalName The original name of this card
 */
class Creature(originalName: String, val originalAttack: Int) : Card(originalName) {
  /**
   * Two Creatures are equal if they have the same name.
   */
  override fun equals(other: Any?) = if (other is Creature) {
    originalName == other.originalName
  } else false

  override fun hashCode() = originalName.hashCode()
}
