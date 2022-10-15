/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver.signals

/**
 * Collection of exceptions used by the Makarena framework.
 */

/** Generic exception for the Makarena project. */
open class MakarenaException(msg: String) : Exception(msg)

/** Exception thrown when the game can't complete a move.   */
class InvalidMoveException(msg: String) : MakarenaException("An invalid move was attempted. $msg")