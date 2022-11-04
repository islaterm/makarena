/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.Builders.engine
import cl.ravenhill.keen.Builders.genotype
import cl.ravenhill.keen.core.chromosomes.BoolChromosome
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe


class EngineSpec : WordSpec({
    lateinit var engine: Engine<Boolean>

    beforeEach {
        engine = engine<Boolean>({ 0.0 }) {
            genotype = genotype {
                chromosomes = listOf(BoolChromosome.Builder(20, 0.15))
            }
        }
    }

    "Engine" When {
        "created" should {
            "start with generation 0" {
                engine.generation shouldBe 0
            }

            "start with an empty population" {
                engine.population.size shouldBe 0
            }

            "start with 0 steady generations" {
                engine.steadyGenerations shouldBe 0
            }
        }
    }
})