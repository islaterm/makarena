/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.driver.GameWinnerObserver
import cl.ravenhill.makarena.driver.signals.InvalidMoveException
import cl.ravenhill.makarena.driver.signals.MakarenaException
import cl.ravenhill.makarena.driver.ttt.TicTacToeMark
import cl.ravenhill.makarena.strategy.TicTacToeMove
import cl.ravenhill.makarena.strategy.player
import kotlin.properties.Delegates.observable

/** A 2D Mutable list, or matrix if you prefer.*/
typealias MutableList2D<T> = MutableList<MutableList<T>>

/**
 * A Tic-Tac-Toe board.
 *
 * @property rows           The rows of the board with the [TicTacToeMove]s.
 * @property winner         The winner of the game. ``TicTacToeMove.EMPTY`` if the game is not over.
 * @property possibleMoves  The possible moves in the board.
 * @property currentPlayer  The player that is currently playing.
 */
class TicTacToeBoard private constructor(private val rows: MutableList2D<TicTacToeMark>) {
    init {
        if (rows.any { it.size != rows[0].size }) {
            throw MakarenaException("TickTacToeBoard must be square")
        }
    }

    // region : Properties
    private val gameWinnerObservers = mutableListOf<GameWinnerObserver>()

    private val _size = rows.size

    private val columns: MutableList2D<TicTacToeMark>
        get() = MutableList(_size) { i -> MutableList(_size) { j -> rows[j][i] } }

    private val diagonals: MutableList2D<TicTacToeMark>
        get() = mutableListOf(
            MutableList(_size) { i -> rows[i][i] },
            MutableList(_size) { i -> rows[i][_size - 1 - i] }
        )
    var currentPlayer: TicTacToeMark = TicTacToeMark.X

    var winner: TicTacToeMark by observable(TicTacToeMark.Empty) { _, _, new ->
        gameWinnerObservers.parallelStream().forEach { it.onValueChange(new) }
    }

    val possibleMoves: List<TicTacToeMove>
        get() = mutableListOf<TicTacToeMove>().apply {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (this@TicTacToeBoard[i][j] == TicTacToeMark.Empty) {
                        this.add(currentPlayer.move(i, j, 0))
                    }
                }
            }
        }.toList()
    // endregion

    /**
     * Checks if there are moves left on the board.
     */
    fun checkMovesLeft() = rows.any { row -> row.any { mark -> mark == TicTacToeMark.Empty } }

    /**
     * Simulates a move on the board.
     * A simulation is a move that is reverted after the simulation is done.
     *
     * @param move  the move to simulate.
     * @param mark  the mark to simulate.
     * @param block a function to execute after making the move (the move is reverted after the
     *              function is executed).
     */
    fun simulateMove(move: TicTacToeMove, block: (TicTacToeMove) -> Unit) {
        makeMove(move.row, move.column)
        block(move)
        makeMove(move.row, move.column)
    }

    fun makeMove(row: Int, column: Int) =
        if (this[row][column] != TicTacToeMark.Empty) {
            throw InvalidMoveException("Position already taken.")
        } else {
            this[row][column] = currentPlayer
            player.move(row, column, 0)
        }.also {
            checkWinner().takeIf { it != TicTacToeMark.Empty }?.let { winner = it }
        }

    private fun checkWinner(): TicTacToeMark {
        val searchWinnerIn: (MutableList2D<TicTacToeMark>) -> TicTacToeMark =
            { lines: MutableList2D<TicTacToeMark> ->
                var winner: TicTacToeMark = TicTacToeMark.Empty
                for (line in lines) {
                    val first = line.first()
                    if (first != TicTacToeMark.Empty && line.all { it == first }) {
                        winner = player
                        break
                    }
                }
                winner
            }
        return searchWinnerIn(this.rows).takeIf { it != TicTacToeMark.Empty }
            ?: searchWinnerIn(this.columns).takeIf { it != TicTacToeMark.Empty }
            ?: searchWinnerIn(this.diagonals)
    }

    /** Empties the board.  */
    fun empty() {
        for (i in 0..2) {
            for (j in 0..2) {
                setMark(i, j, TicTacToeMark.Empty)
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
    fun addWinnerObserver(observer: GameWinnerObserver) {
        gameWinnerObservers.add(observer)
    }

    override fun toString() =
        """| ${rows[0][0]} | ${rows[0][1]} | ${rows[0][2]} |
           |---+---+---|
           | ${rows[1][0]} | ${rows[1][1]} | ${rows[1][2]} |
           |---+---+---|
           | ${rows[2][0]} | ${rows[2][1]} | ${rows[2][2]} |""".trimMargin()

    fun changePlayer() {
        currentPlayer = currentPlayer.opponent
    }

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

        fun emptyRow(): TicTacToeBoardBuilder =
            this.also {
                rows.add(
                    mutableListOf(
                        TicTacToeMark.Empty,
                        TicTacToeMark.Empty,
                        TicTacToeMark.Empty
                    )
                )
            }

        fun build() = TicTacToeBoard(rows)
    }
}