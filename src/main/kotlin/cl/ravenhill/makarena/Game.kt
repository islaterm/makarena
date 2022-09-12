/**
 * This file provides interfaces to represent games.
 */

package cl.ravenhill.makarena

import cl.ravenhill.makarena.model.Marker
import java.util.EnumMap


/** This represents a generic game. */
interface Game {
    /** Scoreboard of the game. */
    val scores: Map<Marker, Int>
}

/** This represents a Tic-Tac-Toe game. */
object TicTacToeGame : Game {
    override val scores = EnumMap<Marker, Int>(Marker::class.java).apply {
        put(Marker.X, 0)
        put(Marker.O, 0)
    }
}
