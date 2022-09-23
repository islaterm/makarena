/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.MakarenaException

enum class Marker {
  X, O, EMPTY
}

var player = Marker.X
var opponent = Marker.O

typealias MutableList2D<T> = MutableList<MutableList<T>>

/**
 * A Tic-Tac-Toe board.
 *
 * @property rows MutableList<MutableList<Marker>>
 *    The rows of the board with the [Marker]s.
 * @property winner Marker
 *    The winner of the game.
 *    ``Marker.EMPTY`` if the game is not over.
 */
class TicTacToeBoard private constructor(private val rows: MutableList2D<Marker>) {
  private val _size = rows.size
  private val columns = MutableList(_size) { i -> MutableList(_size) { j -> rows[j][i] } }
  private val diagonals = mutableListOf(
    MutableList(_size) { i -> rows[i][i] },
    MutableList(_size) { i -> rows[i][_size - 1 - i] }
  )

  val winner: Marker
    get() {
      val searchWinnerIn = { l: MutableList2D<Marker> ->
        try {
          l.first { it.allEqual() && it.first() != Marker.EMPTY }.first()
        } catch (e: NoSuchElementException) {
          Marker.EMPTY
        }
      }
      return searchWinnerIn(rows).takeIf { it != Marker.EMPTY }
        ?: searchWinnerIn(columns).takeIf { it != Marker.EMPTY } ?: searchWinnerIn(diagonals)
    }

  init {
    if (rows.any { it.size != rows[0].size }) {
      throw MakarenaException("TickTacToeBoard must be square")
    }
  }

  /**
   * Checks if there are moves left on the board.
   */
  fun checkMovesLeft() = rows.any { row -> row.any { mark -> mark == Marker.EMPTY } }

  /**
   * Returns the possible moves on the board for a player.
   */
  fun getPossibleMoves() = mutableListOf<Pair<Int, Int>>().apply {
    for (i in 0 until _size) {
      for (j in 0 until _size) {
        if (rows[i][j] == Marker.EMPTY) {
          this.add(Pair(i, j))
        }
      }
    }
  }

  private fun <T> List<T>.allEqual() = this.all { it == this[0] }

  companion object {
    /**
     * Builder for a Tic-Tac-Toe board.
     */
    val builder: () -> TicTacToeBoardBuilder
      get() = { TicTacToeBoardBuilder() }
  }

  class TicTacToeBoardBuilder {
    private val rows = mutableListOf<MutableList<Marker>>()

    fun row(first: Marker, second: Marker, third: Marker): TicTacToeBoardBuilder =
      this.also { rows.add(mutableListOf(first, second, third)) }

    fun build() = TicTacToeBoard(rows)
  }
}