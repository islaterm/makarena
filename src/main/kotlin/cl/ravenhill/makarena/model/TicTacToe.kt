/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.MakarenaException
import cl.ravenhill.makarena.strategy.TicTacToeMove

typealias MutableList2D<T> = MutableList<MutableList<T>>

/** A possible move (mark) in a tic-tac-toe board.  */
enum class TicTacToeMark {
    X, O, EMPTY {
        override fun toString(): String = " "
    }
}

/**
 * A Tic-Tac-Toe board.
 *
 * @property rows           The rows of the board with the [TicTacToeMark]s.
 * @property winner         The winner of the game. ``TicTacToeMark.EMPTY`` if the game is not over.
 * @property possibleMoves  The possible moves in the board.
 */
class TicTacToeBoard private constructor(private val rows: MutableList2D<TicTacToeMark>) {
    private val _size = rows.size
    private val columns: MutableList2D<TicTacToeMark>
        get() = MutableList(_size) { i -> MutableList(_size) { j -> rows[j][i] } }
    private val diagonals: MutableList2D<TicTacToeMark>
        get() = mutableListOf(
            MutableList(_size) { i -> rows[i][i] },
            MutableList(_size) { i -> rows[i][_size - 1 - i] }
        )

    init {
        if (rows.any { it.size != rows[0].size }) {
            throw MakarenaException("TickTacToeBoard must be square")
        }
    }

    // region : Properties
    val winner: TicTacToeMark
        get() { // FIXME: This isn't working uwu
            val searchWinnerIn: (MutableList2D<TicTacToeMark>) -> TicTacToeMark =
                { lines: MutableList2D<TicTacToeMark> ->
                    var winner = TicTacToeMark.EMPTY
                    for (line in lines) {
                        val first = line.first()
                        if (first != TicTacToeMark.EMPTY && line.all { it == first }) {
                            winner = first
                            break
                        }
                    }
                    winner
                }
            return searchWinnerIn(this.rows).takeIf { it != TicTacToeMark.EMPTY }
                ?: searchWinnerIn(this.columns).takeIf { it != TicTacToeMark.EMPTY }
                ?: searchWinnerIn(this.diagonals)
        }


    val possibleMoves: List<TicTacToeMove>
        get() = mutableListOf<TicTacToeMove>().apply {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (this@TicTacToeBoard[i][j] == TicTacToeMark.EMPTY) {
                        this.add(TicTacToeMove(i, j, 0))
                    }
                }
            }
        }.toList()
    // endregion

    /**
     * Checks if there are moves left on the board.
     */
    fun checkMovesLeft() = rows.any { row -> row.any { mark -> mark == TicTacToeMark.EMPTY } }

    /**
     * Simulates a move on the board.
     * A simulation is a move that is reverted after the simulation is done.
     *
     * @param move  the move to simulate.
     * @param mark  the mark to simulate.
     * @param block a function to execute after making the move (the move is reverted after the
     *              function is executed).
     */
    fun simulateMove(move: TicTacToeMove, mark: TicTacToeMark, block: (TicTacToeMove) -> Unit) {
        makeMove(move.row, move.column, mark)
        block(move)
        makeMove(move.row, move.column, TicTacToeMark.EMPTY)
    }

    private fun makeMove(row: Int, column: Int, mark: TicTacToeMark) {
        this[row][column] = mark
    }

    /** Empties the board.  */
    fun empty() {
        for (i in 0..2) {
            for (j in 0..2) {
                setMark(i, j, TicTacToeMark.EMPTY)
            }
        }
    }

    // region :     ACCESSORS
    /** Gets the mark at the given position. */
    operator fun get(row: Int): MutableList<TicTacToeMark> = rows[row]

    /**
     * Writes a mark in the board.
     *
     * @param row       The row of the mark.
     * @param column    The column of the mark.
     * @param mark      The mark to write.
     */
    fun setMark(row: Int, column: Int, mark: TicTacToeMark) {
        rows[row][column] = mark
    }
    // endregion

    // region :     UTILITY
    override fun toString() =
        """| ${rows[0][0]} | ${rows[0][1]} | ${rows[0][2]} |
           |---+---+---|
           | ${rows[1][0]} | ${rows[1][1]} | ${rows[1][2]} |
           |---+---+---|
           | ${rows[2][0]} | ${rows[2][1]} | ${rows[2][2]} |""".trimMargin()

    // endregion    UTILITY
    companion object {
        /**
         * Builder for a Tic-Tac-Toe board.
         */
        val builder: () -> TicTacToeBoardBuilder
            get() = { TicTacToeBoardBuilder() }
    }

    class TicTacToeBoardBuilder {
        private val rows = mutableListOf<MutableList<TicTacToeMark>>()

        fun row(
            first: TicTacToeMark,
            second: TicTacToeMark,
            third: TicTacToeMark
        ): TicTacToeBoardBuilder =
            this.also { rows.add(mutableListOf(first, second, third)) }

        fun build() = TicTacToeBoard(rows)
    }
}