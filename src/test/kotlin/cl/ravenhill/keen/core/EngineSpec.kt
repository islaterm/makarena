/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.core.chromosomes.BoolChromosome
import cl.ravenhill.keen.signals.KeenException
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.nonPositiveInt
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.checkAll
import org.junit.jupiter.api.assertThrows


class EngineSpec : WordSpec({
    lateinit var engine: Engine<Boolean>

    beforeEach {
        engine = Engine({ 0 }, BoolChromosome(1, 1.0))
    }

    "The population size of the engine" should {
        "be 50 by default" {
            engine.populationSize shouldBe 50
        }

        "be settable for values greater than 0" {
            checkAll(Arb.positiveInt()) { size ->
                engine.populationSize = size
                engine.populationSize shouldBe size
            }
        }

        "throw an exception for values less than 1" {
            checkAll(Arb.nonPositiveInt()) { size ->
                assertThrows<KeenException> {
                    engine.populationSize = size
                }.message shouldBe "Parameter population size must be at least 1"
            }
        }
    }
})