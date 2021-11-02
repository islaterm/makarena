package cl.ravenhill.makarena.model

/**
 * A card is the main entity of the game.
 *
 * @property originalName
 *    The name of the card. This also serves as a unique identifier.
 * @constructor
 *    Creates a new card with the given name; note that this name should be unique, as two cards
 *    with the same name will be considered the same.
 */
open class Card(val originalName: String)
