/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy

/**
 * This interface represents a generic move for a game.
 *
 * @property score Double:
 *    a numerical value indicating how "good" the move is.
 */
interface Move {
    var score: Int
    val id: Id
}

/**
 * Data class representing a move on the board of a Tic-Tac-Toe game.
 *
 * @property row Int:
 *    the row of the move.
 * @property column Int:
 *    the column of the move.
 * @property score Int:
 *    a numerical value indicating how "good" the move is.
 * @constructor Creates a new move.
 */
sealed interface TicTacToeMove : Move {
    val row: Int
    val column: Int
    override var score: Int

    /** Indicates if this move is an empty move.    */
    fun isEmpty(): Boolean = false

    /**
     * This class represents a move of the player X on a Tic-Tac-Toe game.
     *
     * @property row    the row of the move.
     * @property column the column of the move.
     * @property score  a numerical value indicating how "good" the move is.
     * @constructor Creates a new move.
     */
    data class XMove(override val row: Int, override val column: Int, override var score: Int) :
        TicTacToeMove {
        override val id = TicTacToeMark.X
    }

    /**
     * This class represents a move of the player X on a Tic-Tac-Toe game.
     *
     * @property row    the row of the move.
     * @property column the column of the move.
     * @property score  a numerical value indicating how "good" the move is.
     * @constructor Creates a new move.
     */
    data class OMove(override val row: Int, override val column: Int, override var score: Int) :
        TicTacToeMove {
        override val id = TicTacToeMark.O
    }

    /**
     * This class represents a move of the player X on a Tic-Tac-Toe game.
     *
     * @property row    the row of the move.
     * @property column the column of the move.
     * @property score  a numerical value indicating how "good" the move is.
     * @constructor Creates a new move.
     */
    data class EmptyMove(
        override val row: Int = Int.MIN_VALUE,
        override val column: Int = Int.MIN_VALUE,
        override var score: Int = Int.MIN_VALUE
    ) : TicTacToeMove {
        override val id = TicTacToeMark.Empty
    }
}

