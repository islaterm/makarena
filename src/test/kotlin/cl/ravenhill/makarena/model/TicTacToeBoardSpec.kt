package cl.ravenhill.makarena.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class TicTacToeBoardSpec : StringSpec({
    "An empty board should have no winner" {
        val board = TicTacToeBoard.builder()
            .row('_', '_', '_')
            .row('_', '_', '_')
            .row('_', '_', '_')
            .build()
        board.winner shouldBe '_'
    }

    "A board with a single row of the same mark should have that mark as winner" {
    }

    "A board with a single column of the same mark should have that mark as winner" {
    }

    "A board with a single diagonal of the same mark should have that mark as winner" {
    }
})