/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver.ttt

import cl.ravenhill.makarena.model.TicTacToeBoard

object TicTacToeGame {
    private val board = TicTacToeBoard.builder()
        .emptyRow()
        .emptyRow()
        .emptyRow()
        .build()

    val player: TicTacToeMark
        get() = board.currentPlayer

    fun move(i: Int, j: Int) {
        board.makeMove(i, j)
        board.changePlayer()
    }
}
