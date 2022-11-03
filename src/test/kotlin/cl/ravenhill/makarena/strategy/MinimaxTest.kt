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
import cl.ravenhill.makarena.strategy.ttt.TicTacToeMark
import cl.ravenhill.makarena.strategy.ttt.TicTacToeMark.*
import io.kotest.core.spec.style.WordSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

data class TttBoard(
    val c00: TicTacToeMark, val c01: TicTacToeMark, val c02: TicTacToeMark,
    val c10: TicTacToeMark, val c11: TicTacToeMark, val c12: TicTacToeMark,
    val c20: TicTacToeMark, val c21: TicTacToeMark, val c22: TicTacToeMark,
    val best: TicTacToeMove
) {
    fun asStandardBoard(): TicTacToeBoard {
        return TicTacToeBoard.builder()
            .row(c00, c01, c02)
            .row(c10, c11, c12)
            .row(c20, c21, c22)
            .build()
    }
}

class MinimaxTest : WordSpec({
    "Minimax algorithm can find the best move for a given Tic-Tac-Toe board" When {
        withData(
//            TttBoard(Empty, X, X, X, O, O, O, O, X, TicTacToeMove.XMove(0, 0, 10)),
            //TttBoard(X, X, X, O, X, Empty, O, X, Empty, TicTacToeMove.EmptyMove(-1, -1, 10)),     UNDEFINED
//            TttBoard(O, X, X, O, O, X, Empty, O, Empty, TicTacToeMove.XMove(2, 0, 10)),
            TttBoard(X, X, O, X, X, O, O, O, Empty, TicTacToeMove.XMove(2, 2, 10)),
            TttBoard(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, TicTacToeMove.XMove(0, 0, 0)),
        ) {
            findBestMove(it.asStandardBoard()) shouldBe it.best
        }
    }
})