/*
 * "makarena" (c) by R8V.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy

import cl.ravenhill.makarena.model.Marker
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class MinimaxTest : StringSpec({
    "Minimax algorithm can find the best move for a given Tic-Tac-Toe board" {
        val board = mutableListOf(
            mutableListOf(Marker.X, Marker.O, Marker.X),
            mutableListOf(Marker.O, Marker.O, Marker.X),
            mutableListOf(Marker.EMPTY, Marker.EMPTY, Marker.EMPTY)
        )
        val bestMove = findBestMove(board)
        bestMove shouldBe TicTacToeMove(2, 2, 10)
    }
})