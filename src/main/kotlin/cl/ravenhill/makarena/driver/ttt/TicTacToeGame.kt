/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver.ttt

import cl.ravenhill.makarena.driver.GameWinnerObserver
import cl.ravenhill.makarena.driver.ttt.TicTacToeGame.player
import cl.ravenhill.makarena.model.TicTacToeBoard

/**
 * Driver for a Tic-Tac-Toe game.
 *
 * @property player The player that is playing the game.
 */
object TicTacToeGame : GameWinnerObserver {
    private val board = TicTacToeBoard.builder()
        .emptyRow()
        .emptyRow()
        .emptyRow()
        .build()

    val player: TicTacToeMark
        get() = board.currentPlayer

    /** Makes a move on the board and changes the current player to the other one.  */
    fun move(i: Int, j: Int) {
        board.makeMove(i, j)
        board.changePlayer()
    }

    override fun onValueChange(new: TicTacToeMark) {
        throw Throwable("A player winned")
    }
}
