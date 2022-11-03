/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.strategy.ttt

import cl.ravenhill.makarena.strategy.Id
import cl.ravenhill.makarena.strategy.TicTacToeMove

/** Identifier for a Tic-Tac-Toe move.   */
sealed interface TicTacToeMark : Id {

    /**
     * This is the move that this mark represents.
     */
    fun move(i: Int, j: Int, score: Int): TicTacToeMove

    /**
     * An empty cell.
     */
    object Empty : TicTacToeMark {
        override fun toString(): String = " "
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.EmptyMove(i, j, score)

        override fun equals(other: Any?) = other === this || other is Empty
    }

    /**
     * A cell with a cross.
     */
    object X : TicTacToeMark {
        override fun toString(): String = "X"
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.XMove(i, j, score)

        override fun equals(other: Any?) = other === this || other is X
    }

    /**
     * A cell with a circle.
     */
    object O : TicTacToeMark {
        override fun toString(): String = "O"
        override fun equals(other: Any?) = other === this || other is O
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.OMove(i, j, score)
    }
}