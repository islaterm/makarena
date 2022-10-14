package cl.ravenhill.makarena.driver.ttt

import cl.ravenhill.makarena.strategy.Id
import cl.ravenhill.makarena.strategy.TicTacToeMove

/** Identifier for a Tic-Tac-Toe move.   */
sealed interface TicTacToeMark : Id {
    val opponent: TicTacToeMark

    /** Makes a move in the board.  */
    fun move(i: Int, j: Int, score: Int): TicTacToeMove

    /** Empty mark. */
    object Empty : TicTacToeMark {
        override fun toString(): String = " "
        override val opponent = this
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.EmptyMove(i, j, score)
        override fun equals(other: Any?) = other === this || other is Empty
    }

    /** X mark. */
    object X : TicTacToeMark {
        override fun toString(): String = "X"
        override val opponent = O
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.XMove(i, j, score)
        override fun equals(other: Any?) = other === this || other is X
    }

    /** O mark. */
    object O : TicTacToeMark {
        override fun toString(): String = "O"
        override val opponent = X
        override fun move(i: Int, j: Int, score: Int) = TicTacToeMove.OMove(i, j, score)
        override fun equals(other: Any?) = other === this || other is O
    }
}