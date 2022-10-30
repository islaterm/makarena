/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.Builders.genotype
import cl.ravenhill.keen.core.chromosomes.BoolChromosome
import cl.ravenhill.keen.signals.GenotypeConfigurationException
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.assertThrows

class GenotypeSpec : WordSpec({
    "Building a genotype" should {
        "throw an exception if no chromosomes are added" {
            assertThrows<GenotypeConfigurationException> {
                genotype<BoolChromosome> { }.build()
            }.message shouldContain "Chromosomes must be initialized."
        }
        "throw an exception if chromosomes are empty" {
            assertThrows<GenotypeConfigurationException> {
                genotype<BoolChromosome> {
                    chromosomes = emptyList()
                }.build()
            }.message shouldContain "Chromosomes must not be empty."
        }
    }
})