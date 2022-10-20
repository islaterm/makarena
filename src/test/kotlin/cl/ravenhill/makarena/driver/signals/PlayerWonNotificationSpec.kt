/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver.signals

import cl.ravenhill.makarena.strategy.Id
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll


class PlayerWonNotificationSpec : StringSpec({
    "A notification signaling a player won can be thrown" {
        checkAll<String> { id ->
            shouldThrow<PlayerWonNotification> {
                throw PlayerWonNotification(object : Id {
                    override fun toString() = id
                })
            }.message shouldBe "$id won the game"
        }
    }
})