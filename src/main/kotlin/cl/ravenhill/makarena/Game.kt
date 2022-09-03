/*
 * "makarena" (c) by Ignacio Slater M.
 * "makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 */
package cl.ravenhill.makarena

import cl.ravenhill.makarena.model.Marker
import java.util.EnumMap


interface Game {
  val scores: Map<Marker, Int>
}

object TicTacToeGame : Game {
  override val scores = EnumMap<Marker, Int>(Marker::class.java).apply {
    put(Marker.X, 0)
    put(Marker.O, 0)
  }
}
