/*
 * "Makarena" (c) by R8V.
 * "Makarena" is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 *  work. If not, see <https://creativecommons.org/licenses/by/4.0/>.
 */

package cl.ravenhill.keen.chromosomes

import io.jenetics.BitChromosome
import io.jenetics.BitGene
import io.jenetics.BitChromosome as JBitChromosome

class BoolChromosome(override val length: Int, val trueProbability: Double) : Chromosome<BitGene> {
    override val jenetics: JBitChromosome = JBitChromosome.of(10, 0.5)

    override fun toBitChromosome(): BitChromosome = jenetics.`as`(BitChromosome::class.java)

    override fun toString() = jenetics.toString()
}