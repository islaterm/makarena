/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.limits

import cl.ravenhill.keen.signals.LimitConfigurationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.string.shouldContain
import io.kotest.property.Arb
import io.kotest.property.arbitrary.negativeInt
import io.kotest.property.checkAll


class GenerationCountSpec : WordSpec({
    "GenerationCount" When {
        "created" should {
            "throw an exception if the given value is negative" {
                checkAll(Arb.negativeInt()) { i ->
                    shouldThrow<LimitConfigurationException> {
                        GenerationCount(i)
                    }.message shouldContain "Generation count must not be negative, but was $i"
                }
            }
        }
    }
})