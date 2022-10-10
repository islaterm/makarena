package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.model.TicTacToeMark.EMPTY
import cl.ravenhill.makarena.strategy.TicTacToeMove
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.list
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum
import io.kotest.property.exhaustive.ints

fun checkWinner(board: TicTacToeBoard, mark: TicTacToeMark, block: () -> Unit) {
    board.empty()
    block()
    board.winner shouldBe mark
}

class TicTacToeBoardSpec : WordSpec({
    lateinit var board: TicTacToeBoard

    beforeAny {
        board = TicTacToeBoard.builder()
            .row(EMPTY, EMPTY, EMPTY)
            .row(EMPTY, EMPTY, EMPTY)
            .row(EMPTY, EMPTY, EMPTY)
            .build()
    }

    "An empty board" should {
        "have no winner" {
            board.winner shouldBe EMPTY
        }

        "give all cells as possible moves" {
            board.possibleMoves.size shouldBe 9
            board.possibleMoves shouldBe listOf(
                TicTacToeMove(0, 0, 0),
                TicTacToeMove(0, 1, 0),
                TicTacToeMove(0, 2, 0),
                TicTacToeMove(1, 0, 0),
                TicTacToeMove(1, 1, 0),
                TicTacToeMove(1, 2, 0),
                TicTacToeMove(2, 0, 0),
                TicTacToeMove(2, 1, 0),
                TicTacToeMove(2, 2, 0)
            )
        }

        "have moves left" {
            board.checkMovesLeft() shouldBe true
        }
    }

    "The winner" should {
        "be obtainable for a board with a single row of the same mark" {
            checkAll(Exhaustive.enum<TicTacToeMark>(), Exhaustive.ints(0..2)) { mark, row ->
                checkWinner(board, mark) {
                    board.setMark(row, 0, mark)
                    board.setMark(row, 1, mark)
                    board.setMark(row, 2, mark)
                }
            }
        }

        "be obtainable for a board with a single column of the same mark" {
            checkAll(Exhaustive.enum<TicTacToeMark>(), Exhaustive.ints(0..2)) { mark, column ->
                checkWinner(board, mark) {
                    board.setMark(0, column, mark)
                    board.setMark(1, column, mark)
                    board.setMark(2, column, mark)
                }
            }
        }

        "be obtainable for a board with a single diagonal of the same mark" {
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
    }

    "The possible moves of a board" should {
        "be obtainable for an arbitrary board" {
            checkAll(
                Arb.list(Arb.enum<TicTacToeMark>(), 9..9)
            ) { marks ->
                board.empty()
                marks.forEachIndexed { index, mark ->
                    board.setMark(index / 3, index % 3, mark)
                }
                board.possibleMoves.size shouldBe marks.count { it == EMPTY }
                board.possibleMoves shouldBe marks.mapIndexedNotNull { index, mark ->
                    if (mark == EMPTY) TicTacToeMove(index / 3, index % 3, 0) else null
                }
            }
        }
    }
})
