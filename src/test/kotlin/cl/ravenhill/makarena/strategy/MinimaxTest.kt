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

import cl.ravenhill.makarena.model.TicTacToeBoard
import cl.ravenhill.makarena.model.TicTacToeMark.EMPTY
import cl.ravenhill.makarena.model.TicTacToeMark.O
import cl.ravenhill.makarena.model.TicTacToeMark.X
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class MinimaxTest : StringSpec({
    "Minimax algorithm can find the best move for a given Tic-Tac-Toe board" {
        val board = TicTacToeBoard.builder()
            .row(X, O, X)
            .row(O, O, X)
            .row(O, X, EMPTY)
            .build()
        val bestMove = findBestMove(board)
        bestMove shouldBe TicTacToeMove(2, 2, 10)
    }
})