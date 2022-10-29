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


class Genotype<DNA>(
    chromosome: Chromosome<DNA>,
    vararg chromosomes: Chromosome<DNA>
) :
    Verifiable {
    private val chromosomes: List<Chromosome<DNA>> =
        listOf(chromosome, *chromosomes)

    override fun verify() = chromosomes.all { it.verify() }

    override fun toString() = chromosomes.joinToString(", ", prefix = "[", postfix = "]")
}