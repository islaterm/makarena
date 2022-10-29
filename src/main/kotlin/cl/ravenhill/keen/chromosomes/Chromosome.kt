/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.chromosomes

import cl.ravenhill.keen.Verifiable
import cl.ravenhill.keen.genes.Gene

/**
 * Sequence of genes.
 *
 * @param DNA   The type of the genes' values.
 *
 * @property genes      The genes of the chromosome.
 * @property isValid    Whether the chromosome is valid or not.
 * @property size       The size of the chromosome.
 *
 * @author <a href="https://github.com/r8vnhill">R8V</a>
 */
interface Chromosome<DNA> : Verifiable {

    val genes: List<Gene<DNA>>

    override fun verify() = genes.all { it.verify() }

    val size: Int
        get() = genes.size
}