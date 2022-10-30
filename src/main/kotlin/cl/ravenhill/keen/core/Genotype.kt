/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.core

import cl.ravenhill.keen.Verifiable
import cl.ravenhill.keen.core.chromosomes.Chromosome
import cl.ravenhill.keen.signals.GenotypeConfigurationException


class Genotype<DNA> private constructor(private val chromosomes: List<Chromosome<DNA>>) :
        Verifiable {

    override fun verify() = chromosomes.isNotEmpty() && chromosomes.all { it.verify() }

    class GenotypeBuilder<DNA> {

        lateinit var chromosomes: List<Chromosome.ChromosomeBuilder<DNA>>

        fun build() = if (!this::chromosomes.isInitialized) {
            throw GenotypeConfigurationException("Chromosomes must be initialized.")
        } else if (chromosomes.isEmpty()) {
            throw GenotypeConfigurationException("Chromosomes must not be empty.")
        } else {
            Genotype(chromosomes.map { it.build() })
        }
    }
}
