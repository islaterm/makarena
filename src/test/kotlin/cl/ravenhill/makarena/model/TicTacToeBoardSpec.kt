package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.model.TicTacToeMark.EMPTY
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum
import io.kotest.property.exhaustive.ints

fun checkWinner(board: TicTacToeBoard, mark: TicTacToeMark, block: () -> Unit) {
    board.empty()
    block()
    board.winner shouldBe mark
}

class TicTacToeBoardSpec : StringSpec({
    lateinit var board: TicTacToeBoard

    beforeAny {
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
            checkWinner(board, mark) {
                board.setMark(row, 0, mark)
                board.setMark(row, 1, mark)
                board.setMark(row, 2, mark)
            }
        }
    }

    "A board with a single column of the same mark should have that mark as winner" {
        checkAll(Exhaustive.enum<TicTacToeMark>(), Exhaustive.ints(0..2)) { mark, column ->
            checkWinner(board, mark) {
                board.setMark(0, column, mark)
                board.setMark(1, column, mark)
                board.setMark(2, column, mark)
            }
        }
    }

    "A board with a single diagonal of the same mark should have that mark as winner" {
        checkAll(Exhaustive.enum<TicTacToeMark>()) { mark ->
            checkWinner(board, mark) {
                board.setMark(0, 0, mark)
                board.setMark(1, 1, mark)
                board.setMark(2, 2, mark)
            }
            checkWinner(board, mark) {
                board.setMark(0, 2, mark)
                board.setMark(1, 1, mark)
                board.setMark(2, 0, mark)
            }
        }
    }
})
