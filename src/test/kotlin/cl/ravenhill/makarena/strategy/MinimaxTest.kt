/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.strategy

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe


class MinimaxTest : StringSpec({
  "Minimax algorithm can find the best move for a given Tic-Tac-Toe board" {
    val board =
      arrayOf(charArrayOf('x', 'o', 'x'), charArrayOf('o', 'o', 'x'), charArrayOf('_', '_', '_'))
    val bestMove = findBestMove(board)
    bestMove shouldBe TicTacToeMove(2, 2, 10)
  }
})