/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
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
data class TicTacToeMove(val row: Int, val column: Int, override var score: Int) : Move