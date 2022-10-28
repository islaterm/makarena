/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.chromosomes

import cl.ravenhill.keen.KeenException
import io.jenetics.BitChromosome
import kotlin.ranges.IntRange
import io.jenetics.IntegerChromosome as JIntegerChromosome
import io.jenetics.IntegerGene as JIntegerGene
import io.jenetics.util.IntRange as JIntRange


class IntChromosome(min: Int, max: Int, lengthRange: IntRange) : Chromosome<JIntegerGene> {
    override val jenetics: JIntegerChromosome =
        JIntegerChromosome.of(0, 1000, JIntRange.of(lengthRange.first, lengthRange.last))

    override fun toBitChromosome(): BitChromosome =
        throw KeenException("Can't convert IntChromosome to BitChromosome")

    override fun toString() = jenetics.toString()
}