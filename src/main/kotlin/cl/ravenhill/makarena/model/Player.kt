/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.model

import cl.ravenhill.makarena.strategy.Board
import cl.ravenhill.makarena.strategy.Id

sealed interface Player {
    class HumanPlayer(val name: String, val id: Id, val board: Board) : Player
    class ComputerPlayer(val name: String = "CPU", val id: Id, val board: Board) : Player
}
