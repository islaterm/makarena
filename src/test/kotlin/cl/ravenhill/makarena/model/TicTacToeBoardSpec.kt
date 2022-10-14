package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.strategy.TicTacToeMark
import cl.ravenhill.makarena.strategy.TicTacToeMark.*
import cl.ravenhill.makarena.strategy.TicTacToeMove
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.Exhaustive
import io.kotest.property.arbitrary.element
import io.kotest.property.arbitrary.list
import io.kotest.property.assume
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.ints

fun checkWinner(board: TicTacToeBoard, mark: TicTacToeMark, block: () -> Unit) {
    board.empty()
    board.currentPlayer = mark
    block()
    board.winner shouldBe mark
}

fun fillBoard(board: TicTacToeBoard, marks: List<TicTacToeMark>) {
    board.empty()
    marks.forEachIndexed { index, mark ->
        board.setMark(index / 3, index % 3, mark)
    }
}

class TicTacToeBoardSpec : WordSpec({
    lateinit var board: TicTacToeBoard

    beforeAny {
        board = TicTacToeBoard.builder()
            .row(Empty, Empty, Empty)
            .row(Empty, Empty, Empty)
            .row(Empty, Empty, Empty)
            .build()
    }

    "An empty board" should {
        "have no winner" {
            board.winner shouldBe Empty
        }

        "give all cells as possible moves" {
            board.possibleMoves.size shouldBe 9
            board.possibleMoves shouldBe listOf(
                TicTacToeMove.XMove(0, 0, 0),
                TicTacToeMove.XMove(0, 1, 0),
                TicTacToeMove.XMove(0, 2, 0),
                TicTacToeMove.XMove(1, 0, 0),
                TicTacToeMove.XMove(1, 1, 0),
                TicTacToeMove.XMove(1, 2, 0),
                TicTacToeMove.XMove(2, 0, 0),
                TicTacToeMove.XMove(2, 1, 0),
                TicTacToeMove.XMove(2, 2, 0)
            )
        }

        "have moves left" {
            board.checkMovesLeft() shouldBe true
        }
    }

    "A full board" should {
        "have no possible moves" {
            checkAll(Arb.list(Arb.element(Empty, X, O), 9..9)) { marks ->
                assume(marks.all { it != Empty })
                fillBoard(board, marks)
                board.possibleMoves.size shouldBe 0
            }
        }

        "have no moves left" {
            checkAll(Arb.list(Arb.element(Empty, X, O), 9..9)) { marks ->
                assume(marks.all { it != Empty })
                fillBoard(board, marks)
                board.checkMovesLeft() shouldBe false
            }
        }
    }

    "The winner" should {
        "be obtainable for a board with a single row of the same mark" {
            checkAll(Arb.element(Empty, X, O), Exhaustive.ints(0..2)) { mark, row ->
                checkWinner(board, mark) {
                    board.setMark(row, 0, mark)
                    board.setMark(row, 1, mark)
                    board.setMark(row, 2, mark)
                }
            }
        }

        "be obtainable for a board with a single column of the same mark" {
            checkAll(Arb.element(Empty, X, O), Exhaustive.ints(0..2)) { mark, column ->
                checkWinner(board, mark) {
                    board.setMark(0, column, mark)
                    board.setMark(1, column, mark)
                    board.setMark(2, column, mark)
                }
            }
        }

        "be obtainable for a board with a single diagonal of the same mark" {
            checkAll(Arb.element(Empty, X, O)) { mark ->
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
                Arb.list(Arb.element(Empty, X, O), 9..9)
            ) { marks ->
                fillBoard(board, marks)
                board.currentPlayer = X
                board.possibleMoves.size shouldBe marks.count { it == Empty }
                board.possibleMoves shouldBe marks.mapIndexedNotNull { index, mark ->
                    if (mark == Empty) board.makeMove(index / 3, index % 3) else null
                }
            }
        }
    }
})
