/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.makarena

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class MakarenaExceptionTest : StringSpec({
    "The exception can be created with a message" {
        shouldThrow<MakarenaException> {
            throw MakarenaException("This is a test")
        }.message shouldBe "This is a test"
    }
})
