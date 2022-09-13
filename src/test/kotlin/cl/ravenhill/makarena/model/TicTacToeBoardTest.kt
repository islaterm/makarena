/*
 * "Makarena" (c) by R8V
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.MakarenaException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.property.Arb
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.list
import io.kotest.property.assume
import io.kotest.property.checkAll


class TicTacToeBoardTest : StringSpec({
    lateinit var emptyBoard: TicTacToeBoard

    beforeEach {
        emptyBoard = buildBoardWith(List(9) { Marker.EMPTY })
    }

    "Builder instances are independent" {
        TicTacToeBoard.builder() shouldNotBeSameInstanceAs TicTacToeBoard.builder()
    }

    "Building a board without 3 rows should throw an exception" {
        checkAll(Arb.list(Arb.enum<Marker>(), 3..90)) { markers ->
            assume(markers.size % 3 == 0)
            assume(markers.size > 9)
            val exception = shouldThrow<MakarenaException> { buildBoardWith(markers) }
            exception.message shouldBe "TicTacToeBoard can only have 3 rows"
        }
    }

    "Building a board with 3 rows should not throw an exception" {
        checkAll(Arb.list(Arb.enum<Marker>())) { markers ->
            assume(markers.size == 9)
            shouldNotThrow<MakarenaException> { buildBoardWith(markers) }
        }
    }

    "An empty board can be created" {
        emptyBoard.checkMovesLeft() shouldBe true
        emptyBoard.flatten().all { it == Marker.EMPTY } shouldBe true
    }

    "A board with random cells can be created" {
        checkAll(Arb.list(Arb.enum<Marker>())) { markers ->
            assume(markers.size == 9)
            val board = buildBoardWith(markers)
            board.flatten() shouldBe markers
        }
    }

    "A board can be cleared" {
        checkAll(Arb.list(Arb.enum<Marker>())) { markers ->
            assume(markers.size == 9)
            val board = buildBoardWith(markers)
            board.empty()
            board.flatten().all { it == Marker.EMPTY } shouldBe true
        }
    }

    "A board is empty if all cells are empty" {
        emptyBoard.isEmpty() shouldBe true
    }
})

/**  Creates a board with the given markers. The markers are assigned to the board row by row. */
fun buildBoardWith(markers: Iterable<Marker>) = with(TicTacToeBoard.builder()) {
    markers.chunked(3).forEach { row ->
        this.row(row[0], row[1], row[2])
    }
    build()
}
