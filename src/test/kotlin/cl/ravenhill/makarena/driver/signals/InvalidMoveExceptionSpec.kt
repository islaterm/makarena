/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena.driver.signals

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.checkAll

/**
 * Test suite for [InvalidMoveException].
 */
class InvalidMoveExceptionSpec : StringSpec({
    val baseMsg = "An invalid move was attempted."
    "An InvalidMoveException can be thrown with a message" {
        checkAll<String> { msg ->
            val exception = InvalidMoveException(msg)
            exception.message shouldBe "$baseMsg $msg"
        }
    }
})