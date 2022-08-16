/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena.model

enum class Marker {
  X, O, EMPTY
}
var player = Marker.X
var opponent = Marker.O

typealias Board = Array<Array<Marker>>
typealias MarkerTriplet = Triple<Marker, Marker, Marker>

class TicTacToeBoard private constructor() {
  companion object {
    /**
     * Builder for a Tic-Tac-Toe board.
     */
    val builder: TicTacToeBoardBuilder
      get() {
        return TicTacToeBoardBuilder()
      }
  }

  class TicTacToeBoardBuilder {
    private val rows = mutableListOf<MarkerTriplet>()

    fun row(first: Marker, second: Marker, third: Marker): TicTacToeBoardBuilder {
      if (rows.size == 3) {
        throw IllegalArgumentException("Only 3 rows allowed")
      }
      rows.add(Triple(first, second, third))
      return this
    }

    fun build(): TicTacToeBoard {
      return TicTacToeBoard(rows)
    }
  }
}