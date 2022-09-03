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

//var player = kotlin.Char.X
//var opponent = kotlin.Char.O

typealias MutableList2D<T> = MutableList<MutableList<T>>

/**
 * A Tic-Tac-Toe board.
 *
 * @property rows MutableList<MutableList<_root_ide_package_.kotlin.Char>>
 *    The rows of the board with the [_root_ide_package_.kotlin.Char]s.
 * @property winner _root_ide_package_.kotlin.Char
 *    The winner of the game.
 *    `_root_ide_package_.'_'`` if the game is not over.
 */
class TicTacToeBoard private constructor(private val rows: MutableList2D<Char>) {
    private val _size = rows.size
    private val columns = MutableList(_size) { i -> MutableList(_size) { j -> rows[j][i] } }
    private val diagonals = mutableListOf(
        MutableList(_size) { i -> rows[i][i] },
        MutableList(_size) { i -> rows[i][_size - 1 - i] }
    )

    val winner: Char
        get() { // FIXME: This isn't working uwu
            val searchWinnerIn: (MutableList2D<Char>) -> Char = { lines: MutableList2D<Char> ->
                var winner = '_'
                for (line in lines) {
                    val first = line.first()
                    if (first != '_' && line.all { it == first }) {
                        winner = first
                    }
                }
                winner
            }
            return searchWinnerIn(this.rows).takeIf { it != '_' }
                ?: searchWinnerIn(this.columns).takeIf { it != '_' }
                ?: searchWinnerIn(this.diagonals)
        }

    init {
        if (rows.any { it.size != rows[0].size }) {
            throw MakarenaException("TickTacToeBoard must be square")
        }
    }

    /**
     * Checks if there are moves left on the board.
     */
    fun checkMovesLeft() = rows.any { row -> row.any { mark -> mark == '_' } }

    /**
     * Returns the possible moves on the board for a player.
     */
//    fun getPossibleMoves() = mutableListOf<Pair<Int, Int>>().apply {
//        for (i in 0 until _size) {
//            for (j in 0 until _size) {
//                if (rows[i][j] == '_') {
//                    this.add(Pair(i, j))
//                }
//            }
//        }
//    }

    fun simulateMove(move: TicTacToeMove, mark: Char, block: (TicTacToeMove) -> Unit) {
        makeMove(move.row, move.column, mark)
        block(move)
        makeMove(move.row, move.column, '_')
    }

    private fun makeMove(row: Int, column: Int, mark: Char) {
        this[row][column] = mark
    }

    val possibleMoves: List<TicTacToeMove>
        get() = mutableListOf<TicTacToeMove>().apply {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (this@TicTacToeBoard[i][j] == '_') {
                        this.add(TicTacToeMove(i, j, 0))
                    }
                }
            }
        }.toList()

    private fun <T> List<T>.allEqual() = this.all { it == this[0] }
    operator fun get(row: Int): MutableList<Char> = rows[row]

    companion object {
        /**
         * Builder for a Tic-Tac-Toe board.
         */
        val builder: () -> TicTacToeBoardBuilder
            get() = { TicTacToeBoardBuilder() }
    }

    class TicTacToeBoardBuilder {
        private val rows = mutableListOf<MutableList<Char>>()

        fun row(first: Char, second: Char, third: Char): TicTacToeBoardBuilder =
            this.also { rows.add(mutableListOf(first, second, third)) }

        fun build() = TicTacToeBoard(rows)
    }
}