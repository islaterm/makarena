/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */

/**
 *  This file contains the model of the Tic-Tac-Toe game.
 */

package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.MakarenaException

/** Enum that represents all possible "values" that a cell can have. */
enum class Marker {
    X, O, EMPTY
}

var player = Marker.X
var opponent = Marker.O

typealias MutableList2d<T> = MutableList<MutableList<T>>

/**
 * A Tic-Tac-Toe board.
 *
 * @property rows   The rows of the board with the [Marker]s.
 *                  ``Marker.EMPTY`` if the game is not over.
 */
class TicTacToeBoard private constructor(val rows: MutableList2d<Marker>) {
    private val _size = rows.size
    private val columns = MutableList(_size) { i -> MutableList(_size) { j -> rows[j][i] } }
    private val diagonals = mutableListOf(
        MutableList(_size) { i -> rows[i][i] },
        MutableList(_size) { i -> rows[i][_size - 1 - i] }
    )

    private val winner: Marker
        get() {
            val searchWinnerIn = { l: MutableList2d<Marker> ->
                try {
                    l.first { it.allEqual() && it.first() != Marker.EMPTY }.first()
                } catch (e: NoSuchElementException) {
                    Marker.EMPTY
                }
            }
            return searchWinnerIn(rows).takeIf { it != Marker.EMPTY }
                ?: searchWinnerIn(columns).takeIf { it != Marker.EMPTY }
                ?: searchWinnerIn(diagonals)
        }

    init {
        if (rows.any { it.size != rows[0].size }) {
            throw MakarenaException("TickTacToeBoard must be square")
        }
    }

    /**
     * Checks if there are moves left on the board.
     */
    fun checkMovesLeft() = rows.any { row -> row.any { mark -> mark == Marker.EMPTY } }

    /**
     * Returns the possible moves on the board for a cl.ravenhill.makarena.strategy.getPlayer.
     */
    fun getPossibleMoves() = mutableListOf<Pair<Int, Int>>().apply {
        for (i in 0 until _size) {
            for (j in 0 until _size) {
                if (rows[i][j] == Marker.EMPTY) {
                    this.add(Pair(i, j))
                }
            }
        }
    }

    /** Returns a (flat) list of all the cells in the board. */
    fun flatten() = rows.flatten()

    private fun <T> List<T>.allEqual() = this.all { it == this[0] }

    /**
     * Clears all cells of the board.
     *
     * This operation is done __in-place__.
     */
    fun empty() = rows.forEach { it.replaceAll { Marker.EMPTY } }

    /**
     * Checks if the board is empty.
     *
     * @return  ``true`` when all cells on the board are ``Marker.EMPTY``.
     */
    fun isEmpty() = rows.all { row ->
        row.all { it == Marker.EMPTY }
    }

    companion object {
        /**
         * Builder for a Tic-Tac-Toe board.
         */
        val builder: () -> TicTacToeBoardBuilder
            get() = { TicTacToeBoardBuilder() }
    }

    /**
     * Builder for a Tic-Tac-Toe board.
     */
    class TicTacToeBoardBuilder {
        private val rows = mutableListOf<MutableList<Marker>>()

        /** Adds a row to the board and returns the builder. */
        fun row(first: Marker, second: Marker, third: Marker): TicTacToeBoardBuilder =
            this.also {
                if (rows.size == 3) {
                    throw MakarenaException("TicTacToeBoard can only have 3 rows")
                }
                rows.add(mutableListOf(first, second, third))
            }

        /**
         * Builds the board.
         *
         * @throws MakarenaException if the board does not have **exactly** 3 rows.
         */
        fun build() =
            TicTacToeBoard(rows).also {
                if (it.rows.size != 3) {
                    throw MakarenaException("TicTacToeBoard can only have 3 rows")
                }
            }
    }
}
