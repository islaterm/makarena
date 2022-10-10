package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.model.TicTacToeMark.EMPTY
import io.kotest.assertions.failure
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum
import io.kotest.property.exhaustive.ints

class TicTacToeBoardSpec : StringSpec({
    lateinit var board: TicTacToeBoard

    beforeEach {
        board = TicTacToeBoard.builder()
            .row(EMPTY, EMPTY, EMPTY)
            .row(EMPTY, EMPTY, EMPTY)
            .row(EMPTY, EMPTY, EMPTY)
            .build()
    }

    "An empty board should have no winner" {
        board.winner shouldBe EMPTY
    }

    "A board with a single row of the same mark should have that mark as winner" {
        checkAll(Exhaustive.enum<TicTacToeMark>(), Exhaustive.ints(0..2)) { mark, row ->
            board.setMark(row, 0, mark)
            board.setMark(row, 1, mark)
            board.setMark(row, 2, mark)
            board.winner shouldBe mark
        }
    }

    "!A board with a single column of the same mark should have that mark as winner" {
        failure("Not implemented")
    }

    "!A board with a single diagonal of the same mark should have that mark as winner" {
        failure("Not implemented")
    }
})