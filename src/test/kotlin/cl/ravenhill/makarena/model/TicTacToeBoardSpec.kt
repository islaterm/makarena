package cl.ravenhill.makarena.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TicTacToeBoardSpec : StringSpec({
    lateinit var board: TicTacToeBoard

    beforeEach {
        board = TicTacToeBoard.builder()
            .row('_', '_', '_')
            .row('_', '_', '_')
            .row('_', '_', '_')
            .build()
    }

    "An empty board should have no winner" {
        board.winner shouldBe '_'
    }
})