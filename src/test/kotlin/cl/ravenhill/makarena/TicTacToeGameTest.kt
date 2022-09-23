/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

/**
 * Test suite for a game of Tic Tac Toe.
 *
 * @author <a href="https://github.com/r8vnhill">R8V</a>
 * @see TicTacToeGame
 */
class TicTacToeGameTest : StringSpec({
    beforeAny {
        TicTacToeGame.reset()
    }

    "All players should have a score of 0 at the beginning of the game" {
        TicTacToeGame.scores.values.forEach {
            it shouldBe 0
        }
    }
})