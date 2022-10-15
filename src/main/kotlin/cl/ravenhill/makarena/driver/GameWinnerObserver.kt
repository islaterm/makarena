/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver

import cl.ravenhill.makarena.driver.ttt.TicTacToeMark

/** Observer for the game winner.  */
interface GameWinnerObserver {
    /** Called when the game winner changes.  */
    fun onValueChange(new: TicTacToeMark)
}
