/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.chromosomes

import cl.ravenhill.keen.KeenException
import io.jenetics.DoubleChromosome
import io.jenetics.DoubleGene as JDoubleGene

class DoubleChromosome(min: Double, max: Double, length: Int = 1) : Chromosome<JDoubleGene> {
    override val jenetics: DoubleChromosome = DoubleChromosome.of(min, max, length)
    override fun toString() = jenetics.toString()
    override fun toBitChromosome() =
        throw KeenException("Cannot convert DoubleChromosome to BitChromosome")
}